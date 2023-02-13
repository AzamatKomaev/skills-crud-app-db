package com.azamat_komaev.crudapp.controller;

import com.azamat_komaev.crudapp.model.Specialty;
import com.azamat_komaev.crudapp.model.Status;
import com.azamat_komaev.crudapp.service.SpecialtyService;

import java.util.List;

public class SpecialtyController {
    private final SpecialtyService specialtyService;

    public SpecialtyController() {
        this.specialtyService = new SpecialtyService();
    }

    public List<Specialty> getAll() {
        return specialtyService.getAll();
    }

    public Specialty getOne(Integer id) {
        return specialtyService.getById(id);
    }

    public Specialty save(String name, Status status) {
        return specialtyService.save(name, status);
    }

    public Specialty update(Integer id, String name, Status status) {
        Specialty specialtyToUpdate = this.specialtyService.getById(id);

        if (SpecialtyService.validateSpecialty(specialtyToUpdate)) {
            throw new IllegalArgumentException("Specialty object is not valid. Maybe there is not any skill with such id: " + id);
        }

        return specialtyService.update(specialtyToUpdate, name, status);
    }

    public void destroy(Integer id) {
        specialtyService.delete(id);
    }
}

