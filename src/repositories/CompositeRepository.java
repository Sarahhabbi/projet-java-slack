package repositories;

import caches.MemoryCache;
import models.HasId;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;

public class CompositeRepository<T extends HasId> implements Repository<T> {

    private final MemoryCache memory;
    private final Repository<T> file;
    private Timer timer = new Timer();

    public CompositeRepository(Repository<T> file) {
        this.memory= new MemoryCache<>();
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

    @Override
    public T save(T obj) {
        memory.save(obj);
        return file.save(obj);
    }

    @Override
    public void delete(T obj) throws Exception {
        memory.delete(obj);
        file.delete(obj);
    }

    @Override
    public List<T> findAll() throws FileNotFoundException {
        return memory.findAll();
    }

    @Override
    public T find(String id) throws FileNotFoundException {
        return (T)memory.find(id);
    }

    @Override
    public boolean exists(T obj) {
        return memory.exists(obj);
    }

}
