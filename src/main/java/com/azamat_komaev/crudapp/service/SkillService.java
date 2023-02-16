package com.azamat_komaev.crudapp.service;

import com.azamat_komaev.crudapp.model.Skill;
import com.azamat_komaev.crudapp.model.Status;
import com.azamat_komaev.crudapp.repository.SkillRepository;
import com.azamat_komaev.crudapp.repository.jdbc.JdbcSkillRepositoryImpl;

import java.util.List;

public class SkillService {
    private final SkillRepository skillRepository;

    public SkillService() {
        this.skillRepository = new JdbcSkillRepositoryImpl();
    }

    public List<Skill> getAll() {
        return skillRepository.getAll();
    }

    public Skill getById(Integer id) {
        return skillRepository.getById(id);
    }

    public Skill save(Skill skill) {
        return skillRepository.save(skill);
    }

    public Skill update(Skill skill) {
        return skillRepository.save(skill);
    }

    public void delete(Integer id) {
        skillRepository.deleteById(id);
    }
}
