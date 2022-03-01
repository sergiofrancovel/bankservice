package org.example.dao;

import java.util.List;

public interface Repository<ID, T> {
    ID save(T obj);
    List<T> getAll();
    T getById(ID id);
    void updateById(ID id, T obj);
    void delete(T obj);
    void deleteById(ID id);
}
