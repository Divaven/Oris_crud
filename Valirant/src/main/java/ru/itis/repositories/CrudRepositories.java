package ru.itis.repositories;

import java.util.List;

public interface CrudRepositories<T> {
    void save(T t);
    void delete(T t);
    void update(T t);
    List<T> getAll();
}
