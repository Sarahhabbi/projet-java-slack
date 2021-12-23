package repositories;

import caches.MemoryCache;
import models.HasId;
import repositories.Repository;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class CompositeRepository<T extends HasId> implements Repository<T> {

    private final Repository<T> memory = new MemoryCache<>();
    private final Repository<T> file;
    private Timer timer = new Timer();

    public CompositeRepository(Repository<T> file) {
        this.file = file;
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
    public void delete(T obj) {
        memory.delete(obj);
        file.delete(obj);
    }

    @Override
    public List<T> findAll() {
        return memory.findAll();
    }

    @Override
    public T find(String id) {
        return memory.find(id);
    }

    @Override
    public boolean exists(T obj) {
        return false;
    }


}
