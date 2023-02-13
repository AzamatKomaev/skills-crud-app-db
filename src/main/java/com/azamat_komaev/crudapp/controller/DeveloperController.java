package com.azamat_komaev.crudapp.controller;

import com.azamat_komaev.crudapp.model.Developer;
import com.azamat_komaev.crudapp.model.Skill;
import com.azamat_komaev.crudapp.model.Specialty;
import com.azamat_komaev.crudapp.model.Status;
import com.azamat_komaev.crudapp.service.DeveloperService;
import com.azamat_komaev.crudapp.service.SpecialtyService;

import java.util.List;

public class DeveloperController {
    private final DeveloperService developerService;

    public DeveloperController() {
        this.developerService = new DeveloperService();
    }

    public List<Developer> getAll() {
        return developerService.getAll();
    }

    public Developer getOne(Integer id) {
        return developerService.getById(id);
    }

    public Developer save(String firstName, String lastName, Status status,
                          List<Skill> skillList, Specialty specialty) {
        if (SpecialtyService.validateSpecialty(specialty)) {
            throw new IllegalArgumentException("Specialty object is not valid!");
        }

        return developerService.save(firstName, lastName, status, skillList, specialty);
    }

    public Developer update(Integer id, String firstName, String lastName, Status status,
                            List<Skill> skillList, Specialty specialty) {
        Developer developerToUpdate = developerService.getById(id);

        if (!DeveloperService.validateDeveloper(developerToUpdate)) {
            throw new IllegalArgumentException(
                "Developer object is not valid. Maybe there is not any developer with such id: " + id
            );
        }

        return developerService.update(developerToUpdate, firstName, lastName, status, skillList, specialty);
    }

    public void destroy(Integer id) {
        developerService.delete(id);
    }
}

