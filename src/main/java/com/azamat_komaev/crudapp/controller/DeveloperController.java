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
        if (specialty == null) {
            throw new IllegalArgumentException("Specialty is null!");
        }

        Developer developerToSave = new Developer(null, firstName, lastName, status,
                                                  skillList, specialty);
        return developerService.save(developerToSave);
    }

    public Developer update(Integer id, String firstName, String lastName, Status status,
                            List<Skill> skillList, Specialty specialty) {
        Developer developerToUpdate = developerService.getById(id);

        if (developerToUpdate == null) {
            throw new IllegalArgumentException("There is not any developer with id=" + id);
        }

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

