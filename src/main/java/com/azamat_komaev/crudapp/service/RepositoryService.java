package com.azamat_komaev.crudapp.service;

import com.azamat_komaev.crudapp.repository.GenericRepository;

import java.util.List;

public class RepositoryService<T, ID> {
    GenericRepository<T, ID> repository;

    public RepositoryService(GenericRepository<T, ID> repository) {
        this.repository = repository;
    }

    public List<T> getAll() {
        return repository.getAll();
    }

    public T getById(ID id) {
        return repository.getById(id);
    }

    public T save(T t) {
        return repository.save(t);
    }

    public T update(T t) {
        return repository.update(t);
    }

    public void delete(ID id) {
        repository.deleteById(id);
    }
}
