package com.azamat_komaev.crudapp.repository.jdbc;

import com.azamat_komaev.crudapp.config.Database;
import com.azamat_komaev.crudapp.model.Specialty;
import com.azamat_komaev.crudapp.model.Status;
import com.azamat_komaev.crudapp.repository.SpecialtyRepository;
import com.azamat_komaev.crudapp.service.RepositoryService;

import java.sql.Connection;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class JdbcSpecialtyRepositoryImpl implements SpecialtyRepository {
    private final Connection connection;

    public JdbcSpecialtyRepositoryImpl() {
        connection = Database.getInstance().getConnection();
    }

    @Override
    public Specialty getById(Integer id) {

    }

    @Override
    public List<Specialty> getAll() {
        return null;
    }

    @Override
    public Specialty save(Specialty specialtyToSave) {
        return null;
    }

    @Override
    public Specialty update(Specialty specialty) {
        return null;
    }

    @Override
    public void deleteById(Integer id) {
    }
}
