package com.azamat_komaev.crudapp.controller;

import com.azamat_komaev.crudapp.model.Skill;
import com.azamat_komaev.crudapp.model.Status;
import com.azamat_komaev.crudapp.service.SkillService;

import java.util.List;

public class SkillController {
    private final SkillService skillService;

    public SkillController() {
        this.skillService = new SkillService();
    }

    public List<Skill> getAll() {
        return skillService.getAll();
    }

    public Skill getOne(Integer id) {
        return skillService.getById(id);
    }

    public Skill save(String name, Status status) {
        return skillService.save(name, status);
    }

    public Skill update(Integer id, String name, Status status) {
        Skill skillToUpdate = this.skillService.getById(id);

        if (!SkillService.validateSkill(skillToUpdate)) {
            throw new IllegalArgumentException("Skill object is not valid. Maybe there is not any skill with such id: " + id);
        }

        return this.skillService.update(skillToUpdate, name, status);
    }

    public void destroy(Integer id) {
        skillService.delete(id);
    }
}

