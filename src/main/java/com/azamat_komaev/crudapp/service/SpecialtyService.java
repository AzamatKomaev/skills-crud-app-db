package com.azamat_komaev.crudapp.service;

import com.azamat_komaev.crudapp.model.Specialty;
import com.azamat_komaev.crudapp.model.Status;
import com.azamat_komaev.crudapp.repository.SpecialtyRepository;
import com.azamat_komaev.crudapp.repository.jdbc.JdbcSpecialtyRepositoryImpl;

import java.util.List;

public class SpecialtyService {
    private final SpecialtyRepository specialtyRepository;

    public SpecialtyService() {
        this.specialtyRepository = new JdbcSpecialtyRepositoryImpl();
    }

    public static boolean validateSpecialty(Specialty specialty) {
        if (specialty == null) {
            return true;
        }

        return false;
    }

    public List<Specialty> getAll() {
        return specialtyRepository.getAll();
    }

    public Specialty getById(Integer id) {
        return specialtyRepository.getById(id);
    }

    public Specialty save(String name, Status status) {
        Specialty specialtyToSave = new Specialty(null, name, status);
        return specialtyRepository.save(specialtyToSave);
    }

    public Specialty update(Specialty specialtyToUpdate, String name, Status status) {
        specialtyToUpdate.setName(name);
        specialtyToUpdate.setStatus(status);
        return specialtyToUpdate;
    }

    public void delete(Integer id) {
        specialtyRepository.deleteById(id);
    }
}
