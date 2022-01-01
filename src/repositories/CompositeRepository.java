package repositories;

import caches.MemoryCache;
import models.HasId;

import java.io.FileNotFoundException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;

public class CompositeRepository<T extends HasId> implements Repository<T> {

    private final MemoryCache memory;
    private final Repository<T> file;

    public CompositeRepository(Repository<T> file) {
        this.memory= new MemoryCache<>();
        try {
            List<T> r = file.findAll();
            for (T element : r) {
                this.memory.save(element);
                System.out.println("Element ajoute "+ element.getName());
            }
        }catch(FileNotFoundException fne){
            fne.printStackTrace();
        }
        this.file = file;
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
