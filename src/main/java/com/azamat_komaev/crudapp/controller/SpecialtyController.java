package com.azamat_komaev.crudapp.controller;

import com.azamat_komaev.crudapp.model.Specialty;
import com.azamat_komaev.crudapp.model.Status;
import com.azamat_komaev.crudapp.repository.jdbc.JdbcSpecialtyRepositoryImpl;
import com.azamat_komaev.crudapp.service.RepositoryService;

import java.util.List;

public class SpecialtyController {
    private final RepositoryService<Specialty, Integer> specialtyService;

    public SpecialtyController() {
        this.specialtyService = new RepositoryService<>(new JdbcSpecialtyRepositoryImpl());
    }

    public List<Specialty> getAll() {
        return specialtyService.getAll();
    }

    public Specialty getOne(Integer id) {
        return specialtyService.getById(id);
    }

    public Specialty save(String name, Status status) {
        Specialty specialtyToSave = new Specialty(null, name, status);
        return specialtyService.save(specialtyToSave);
    }

    public Specialty update(Integer id, String name, Status status) {
        Specialty specialtyToUpdate = this.specialtyService.getById(id);

        specialtyToUpdate.setName(name);
        specialtyToUpdate.setStatus(status);

        return specialtyService.update(specialtyToUpdate);
    }

    public void destroy(Integer id) {
        specialtyService.delete(id);
    }
}
