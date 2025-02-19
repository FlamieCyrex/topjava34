package ru.javawebinar.topjava.repository;

import java.util.List;

public interface ObjectRepository <T, ID>{
    void save(T object);
    T findById(ID id);
    List<T> findAll();
    void delete(T object);
    void update(T object);
}
