package com.azamat_komaev.crudapp.repository.jdbc;

import com.azamat_komaev.crudapp.model.Skill;
import com.azamat_komaev.crudapp.model.Status;
import com.azamat_komaev.crudapp.repository.SkillRepository;
import com.azamat_komaev.crudapp.service.RepositoryService;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class JdbcSkillRepositoryImpl implements SkillRepository {

    public JdbcSkillRepositoryImpl() {
    }

    @Override
    public Skill getById(Integer id) {
        return null;
    }

    @Override
    public List<Skill> getAll() {
        return null;
    }

    @Override
    public Skill save(Skill skillToSave) {
        return null;
    }

    @Override
    public Skill update(Skill skill) {
        return null;
    }

    @Override
    public void deleteById(Integer id) {
    }
}

