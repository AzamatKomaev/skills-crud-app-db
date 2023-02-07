package com.azamat_komaev.crudapp.controller;

import com.azamat_komaev.crudapp.model.Developer;
import com.azamat_komaev.crudapp.model.Skill;
import com.azamat_komaev.crudapp.model.Specialty;
import com.azamat_komaev.crudapp.repository.DeveloperRepository;
import com.azamat_komaev.crudapp.repository.gson.GsonDeveloperRepositoryImpl;

import java.util.List;

public class DeveloperController {
    private final DeveloperRepository developerRepository;

    public DeveloperController() {
        this.developerRepository = new GsonDeveloperRepositoryImpl();
    }

    public List<Developer> getAll() {
        return this.developerRepository.getAll();
    }

    public Developer getOne(Integer id) {
        return this.developerRepository.getById(id);
    }

    public Developer save(String firstName, String lastName,
                          List<Skill> skillList, Specialty specialty) {
        Developer developerToSave = new Developer(null, firstName, lastName, skillList, specialty);
        return this.developerRepository.save(developerToSave);
    }

    public Developer update(Integer id, String firstName, String lastName,
                            List<Skill> skillList, Specialty specialty) {
        Developer developerToUpdate = this.developerRepository.getById(id);

        if (developerToUpdate == null) {
            return null;
        }

        developerToUpdate = new Developer(id, firstName, lastName, skillList, specialty);
        return this.developerRepository.update(developerToUpdate);
    }

    public void destroy(Integer id) {
        this.developerRepository.deleteById(id);
    }
}

