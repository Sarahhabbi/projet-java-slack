package repositories;

import caches.MemoryCache;
import models.HasId;

import java.util.List;

public interface Repository<T extends HasId> {

    T save(T obj);

    void delete(T obj);

    List<T> findAll();

    T find(String id);

    boolean exists(T obj);
}
