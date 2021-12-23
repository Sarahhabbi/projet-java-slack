package caches;

import models.HasId;
import repositories.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemoryCache<T extends HasId> implements Repository<T> {

    private final Map<String, T> cache = new HashMap<>();

    @Override
    public T save(T obj) {
        cache.put(obj.getName(), obj);
        return obj;
    }

    public Map<String, T> getCache() {
        return cache;
    }

    @Override
    public void delete(T obj) {
        cache.remove(obj.getName());
    }

    @Override
    public List<T> findAll() {
        return new ArrayList<>(cache.values());
    }

    @Override
    public T find(String id) {
        return cache.get(id);
    }

    @Override
    public boolean exists(T obj) {
        return cache.containsKey(obj.getName());
    }

    public void displayCache(){
        for (Map.Entry<String, T> entry : cache.entrySet()) {
            System.out.println(entry.getKey()+" : "+entry.getValue());
        }
    }
}
