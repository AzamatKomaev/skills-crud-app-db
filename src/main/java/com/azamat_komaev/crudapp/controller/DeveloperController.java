package com.azamat_komaev.crudapp.controller;

import com.azamat_komaev.crudapp.model.Developer;
import com.azamat_komaev.crudapp.model.Skill;
import com.azamat_komaev.crudapp.model.Specialty;
import com.azamat_komaev.crudapp.model.Status;
import com.azamat_komaev.crudapp.repository.jdbc.JdbcDeveloperRepositoryImpl;
import com.azamat_komaev.crudapp.service.RepositoryService;

import java.util.List;

public class DeveloperController {
    private final RepositoryService<Developer, Integer> developerService;

    public DeveloperController() {
        this.developerService = new RepositoryService<>(new JdbcDeveloperRepositoryImpl());
    }

    public List<Developer> getAll() {
        return developerService.getAll();
    }

    public Developer getOne(Integer id) {
        return developerService.getById(id);
    }

    public Developer save(String firstName, String lastName, Status status,
                          List<Skill> skillList, Specialty specialty) {
        Developer developerToSave = new Developer(null, firstName, lastName, status,
                                                  skillList, specialty);
        return developerService.save(developerToSave);
    }

    public Developer update(Integer id, String firstName, String lastName, Status status,
                            List<Skill> skillList, Specialty specialty) {
        Developer developerToUpdate = developerService.getById(id);

        developerToUpdate.setFirstName(firstName);
        developerToUpdate.setLastName(lastName);
        developerToUpdate.setStatus(status);
        developerToUpdate.setSkills(skillList);
        developerToUpdate.setSpecialty(specialty);

        return developerService.update(developerToUpdate);
    }

    public void destroy(Integer id) {
        developerService.delete(id);
    }
}

