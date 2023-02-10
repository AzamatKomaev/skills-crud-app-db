package com.azamat_komaev.crudapp.repository;

import java.sql.Connection;
import java.util.List;

public interface GenericRepository<T, ID> {
    T getById(ID id);

    List<T> getAll();

    T save(T t);

    T update(T t);

    void deleteById(ID id);
}

