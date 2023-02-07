package com.azamat_komaev.crudapp.controller;

import com.azamat_komaev.crudapp.model.Skill;
import com.azamat_komaev.crudapp.repository.gson.GsonSkillRepositoryImpl;
import com.azamat_komaev.crudapp.repository.SkillRepository;

import java.util.List;

public class SkillController {
    private final SkillRepository skillRepository;

    public SkillController() {
        this.skillRepository = new GsonSkillRepositoryImpl();
    }

    public List<Skill> getAll() {
        return this.skillRepository.getAll();
    }

    public Skill getOne(Integer id) {
        return this.skillRepository.getById(id);
    }

    public Skill save(String name) {
        Skill skillToSave = new Skill(null, name);
        return this.skillRepository.save(skillToSave);
    }

    public Skill update(Integer id, String name) {
        Skill skillToUpdate = this.skillRepository.getById(id);

        if (skillToUpdate == null) {
            return null;
        }

        skillToUpdate = new Skill(id, name);
        return this.skillRepository.update(skillToUpdate);
    }

    public void destroy(Integer id) {
        this.skillRepository.deleteById(id);
    }
}

