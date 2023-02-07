package com.azamat_komaev.crudapp.controller;

import java.util.List;

public interface GenericController<T> {
    List<T> getAll();

    T getOne(Integer id);

    T save(Object request);

    T update(Object request);

    void delete(Integer id);
}
