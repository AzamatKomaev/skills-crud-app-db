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
        Specialty specialtyToSave = new Specialty(null, name, status);
        return specialtyService.save(specialtyToSave);
    }

    public Specialty update(Integer id, String name, Status status) {
        Specialty specialtyToUpdate = this.specialtyService.getById(id);

        if (specialtyToUpdate == null) {
            throw new IllegalArgumentException("There is not any specialty with id=" + id);
        }

        specialtyToUpdate.setName(name);
        specialtyToUpdate.setStatus(status);

        return specialtyService.update(specialtyToUpdate);
    }

    public void destroy(Integer id) {
        specialtyService.delete(id);
    }
}

