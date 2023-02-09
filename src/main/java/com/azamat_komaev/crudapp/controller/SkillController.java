package com.azamat_komaev.crudapp.controller;

import com.azamat_komaev.crudapp.model.Skill;
import com.azamat_komaev.crudapp.model.Status;
import com.azamat_komaev.crudapp.repository.jdbc.JdbcSkillRepositoryImpl;
import com.azamat_komaev.crudapp.repository.SkillRepository;

import java.util.List;

public class SkillController {
    private final SkillRepository skillRepository;

    public SkillController() {
        this.skillRepository = new JdbcSkillRepositoryImpl();
    }

    public List<Skill> getAll() {
        return this.skillRepository.getAll();
    }

    public Skill getOne(Integer id) {
        return this.skillRepository.getById(id);
    }

    public Skill save(String name, Status status) {
        Skill skillToSave = new Skill(null, name, status);
        return this.skillRepository.save(skillToSave);
    }

    public Skill update(Integer id, String name, Status status) {
        Skill skillToUpdate = this.skillRepository.getById(id);

        if (skillToUpdate == null) {
            return null;
        }

        skillToUpdate = new Skill(id, name, status);
        return this.skillRepository.update(skillToUpdate);
    }

    public void destroy(Integer id) {
        this.skillRepository.deleteById(id);
    }
}

