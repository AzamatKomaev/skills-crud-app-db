package com.azamat_komaev.crudapp.controller;

import com.azamat_komaev.crudapp.model.Specialty;
import com.azamat_komaev.crudapp.model.Status;
import com.azamat_komaev.crudapp.repository.SpecialtyRepository;
import com.azamat_komaev.crudapp.repository.jdbc.JdbcSpecialtyRepositoryImpl;

import java.util.List;

public class SpecialtyController {
    private final SpecialtyRepository specialtyRepository;

    public SpecialtyController() {
        this.specialtyRepository = new JdbcSpecialtyRepositoryImpl();
    }

    public List<Specialty> getAll() {
        return this.specialtyRepository.getAll();
    }

    public Specialty getOne(Integer id) {
        return this.specialtyRepository.getById(id);
    }

    public Specialty save(String name, Status status) {
        Specialty specialtyToSave = new Specialty(null, name, status);
        return this.specialtyRepository.save(specialtyToSave);
    }

    public Specialty update(Integer id, String name, Status status) {
        Specialty specialtyToUpdate = this.specialtyRepository.getById(id);

        if (specialtyToUpdate == null) {
            return null;
        }

        specialtyToUpdate = new Specialty(id, name, status);
        return this.specialtyRepository.update(specialtyToUpdate);
    }

    public void destroy(Integer id) {
        this.specialtyRepository.deleteById(id);
    }
}

