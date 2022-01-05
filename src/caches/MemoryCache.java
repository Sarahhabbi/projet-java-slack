package caches;

import models.HasId;
import repositories.Repository;

import java.util.*;

public class MemoryCache<T extends HasId> implements Repository<T> {

    public final LinkedHashMap<String, T> cache = new LinkedHashMap<>();

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


}
