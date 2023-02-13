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

    public static boolean validateSkill(Skill skill) {
        if (skill == null) {
            return false;
        }

        return true;
    }

    public List<Skill> getAll() {
        return skillRepository.getAll();
    }

    public Skill getById(Integer id) {
        return skillRepository.getById(id);
    }

    public Skill save(String name, Status status) {
        Skill skillToSave = new Skill(null, name, status);
        return skillRepository.save(skillToSave);
    }

    public Skill update(Skill skillToUpdate, String name, Status status) {
        skillToUpdate.setName(name);
        skillToUpdate.setStatus(status);
        return skillRepository.save(skillToUpdate);
    }

    public void delete(Integer id) {
        skillRepository.deleteById(id);
    }
}
