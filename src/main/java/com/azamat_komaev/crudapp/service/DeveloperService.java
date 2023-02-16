package com.azamat_komaev.crudapp.service;

import com.azamat_komaev.crudapp.model.Developer;
import com.azamat_komaev.crudapp.model.Skill;
import com.azamat_komaev.crudapp.model.Specialty;
import com.azamat_komaev.crudapp.model.Status;
import com.azamat_komaev.crudapp.repository.DeveloperRepository;
import com.azamat_komaev.crudapp.repository.jdbc.JdbcDeveloperRepositoryImpl;

import java.util.List;

public class DeveloperService {
    private final DeveloperRepository developerRepository;

    public DeveloperService() {
        this.developerRepository = new JdbcDeveloperRepositoryImpl();
    }

    public List<Developer> getAll() {
        return developerRepository.getAll();
    }

    public Developer getById(Integer id) {
        return developerRepository.getById(id);
    }

    public Developer save(Developer developer) {
        return this.developerRepository.save(developer);
    }

    public Developer update(Developer developer) {
        return developerRepository.update(developer);
    }

    public void delete(Integer id) {
        developerRepository.deleteById(id);
    }
}
