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
        Skill skill = new Skill(null, name, status);
        return skillService.save(skill);
    }

    public Skill update(Integer id, String name, Status status) {
        Skill skillToUpdate = this.skillService.getById(id);

        if (skillToUpdate == null) {
            throw new IllegalArgumentException("There is no any skill id= " + id);
        }

        skillToUpdate.setName(name);
        skillToUpdate.setStatus(status);
        return this.skillService.update(skillToUpdate);
    }

    public void destroy(Integer id) {
        skillService.delete(id);
    }
}

