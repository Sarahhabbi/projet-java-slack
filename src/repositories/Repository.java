package repositories;

import caches.MemoryCache;
import models.HasId;

import java.io.FileNotFoundException;
import java.util.List;

public interface Repository<T extends HasId> {

    T save(T obj);

    void delete(T obj) throws FileNotFoundException, Exception;

    List<T> findAll() throws FileNotFoundException;

    T find(String id) throws FileNotFoundException;

    boolean exists(T obj) throws FileNotFoundException;
}
