package com.azamat_komaev.crudapp.repository;

import com.azamat_komaev.crudapp.config.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public interface GenericRepository<T, ID> {
    T getById(ID id);

    List<T> getAll();

    T save(T t);

    T update(T t);

    void deleteById(ID id);
}

