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

    public static boolean validateDeveloper(Developer developer) {
        if (developer == null) {
            return false;
        }

        return true;
    }

    public List<Developer> getAll() {
        return developerRepository.getAll();
    }

    public Developer getById(Integer id) {
        return developerRepository.getById(id);
    }

    public Developer save(String firstName, String lastName, Status status,
                          List<Skill> skillList, Specialty specialty) {
        Developer developerToSave = new Developer(null, firstName, lastName, status, skillList, specialty);
        return this.developerRepository.save(developerToSave);
    }

    public Developer update(Developer developerToUpdate, String firstName, String lastName,
                            Status status, List<Skill> skillList, Specialty specialty) {
        developerToUpdate.setFirstName(firstName);
        developerToUpdate.setLastName(lastName);
        developerToUpdate.setStatus(status);
        developerToUpdate.setSkills(skillList);
        developerToUpdate.setSpecialty(specialty);

        return developerRepository.update(developerToUpdate);
    }

    public void delete(Integer id) {
        developerRepository.deleteById(id);
    }
}
