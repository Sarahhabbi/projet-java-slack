package repositories;

import caches.MemoryCache;
import models.HasId;
import models.HasId_Channel;

import java.io.FileNotFoundException;
import java.util.*;

public class CompositeChannelRepository<T extends HasId_Channel> implements Repository_channel<T>{
    public final Map<String, MemoryCache> cache = new HashMap<>();
    private final Repository_channel<T> file;
    private Timer timer = new Timer();


    public CompositeChannelRepository(Repository_channel<T> file) {
        this.file = file;
        /*try{
            List<T> oldcontent=file.findAll();
            for(T element:oldcontent){
                memory.save(element);
            }
        }
        catch(FileNotFoundException e){
            System.out.println("The database is empty");
        }*/
        /*timer.schedule(new TimerTask() {
            @Override
            public void run() {
                for (T obj : memory.findAll()) {
                    file.delete(obj);
                    file.save(obj);
                }
            }
        }, 10000, 10000);*/
    }
    public void addChannel(String name){
        cache.put(name,new MemoryCache());
    }

    @Override
    public void delete(T  obj) throws Exception {
        String channel=obj.getChannelName();
        MemoryCache mc= cache.get(channel);
        mc.delete(obj);
        file.delete(obj);
    }

    @Override
    public List<T> findAll() throws FileNotFoundException {
        ArrayList<T> res=new ArrayList<>();
        for(String name: cache.keySet()){
            MemoryCache mc=cache.get(name);
            res.addAll(mc.findAll());
        }
        return res;
    }

    @Override
    public T  save(T  obj) {
        String channel=obj.getChannelName();
        MemoryCache mc= cache.get(channel);
        mc.save(obj);
        return file.save(obj);
    }

    @Override
    public T  find(String id) throws FileNotFoundException {
        T obj=null;
        for (String name: cache.keySet()){
            MemoryCache mc=cache.get(name);
            obj=(T)mc.find(id);
            if(obj!=null){
                return obj;
            }
        }
        return null;
    }

    @Override
    public boolean exists(T  obj) {
        String channel=obj.getChannelName();
        MemoryCache mc= cache.get(channel);
        return mc.exists(obj);
    }

    @Override
    public List<T> findAllByChannel(String name) throws FileNotFoundException{
        MemoryCache mc= cache.get(name);
        return mc.findAll();
    }
}
