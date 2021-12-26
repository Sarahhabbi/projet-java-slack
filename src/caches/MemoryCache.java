package caches;

import models.HasId;
import repositories.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemoryCache<T extends HasId> implements Repository<T> {

    public final Map<String, T> cache = new HashMap<>();

    @Override
    public T save(T obj) {
        /*List<T> a=new ArrayList<>(cache.values());
        System.out.println("before");
        for(T b:a){
            System.out.println(b.toString());
        }*/
        cache.put(obj.getName(), obj);
        /*System.out.println("after");
        List<T> c=new ArrayList<>(cache.values());
        for(T b:c){
            System.out.println(b.toString());
        }*/
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
