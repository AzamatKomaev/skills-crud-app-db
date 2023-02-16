package com.azamat_komaev.crudapp.service;

import com.azamat_komaev.crudapp.model.Specialty;
import com.azamat_komaev.crudapp.repository.SpecialtyRepository;
import com.azamat_komaev.crudapp.repository.jdbc.JdbcSpecialtyRepositoryImpl;

import java.util.List;

public class SpecialtyService {
    private final SpecialtyRepository specialtyRepository;

    public SpecialtyService() {
        this.specialtyRepository = new JdbcSpecialtyRepositoryImpl();
    }

    public List<Specialty> getAll() {
        return specialtyRepository.getAll();
    }

    public Specialty getById(Integer id) {
        return specialtyRepository.getById(id);
    }

    public Specialty save(Specialty specialty) {
        return specialtyRepository.save(specialty);
    }

    public Specialty update(Specialty specialtyToUpdate) {
        return specialtyRepository.update(specialtyToUpdate);
    }

    public void delete(Integer id) {
        specialtyRepository.deleteById(id);
    }
}
