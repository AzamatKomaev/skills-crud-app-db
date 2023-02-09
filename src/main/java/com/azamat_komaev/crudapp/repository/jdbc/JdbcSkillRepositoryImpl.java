package com.azamat_komaev.crudapp.repository.jdbc;

import com.azamat_komaev.crudapp.config.Database;
import com.azamat_komaev.crudapp.model.Skill;
import com.azamat_komaev.crudapp.model.Status;
import com.azamat_komaev.crudapp.repository.SkillRepository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class JdbcSkillRepositoryImpl implements SkillRepository {

    public JdbcSkillRepositoryImpl() {
    }

    @Override
    public Skill getById(Integer id) {
        return null;
    }

    @Override
    public List<Skill> getAll() {
        List<Skill> skillList = new ArrayList<>();

        try (
            Connection conn = Database.getInstance().getConnection();
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select * from skills")
        ) {
            while (rs.next()) {
                Skill skill = new Skill(rs.getInt("id"), rs.getString("name"),
                                        rs.getBoolean("active") ? Status.ACTIVE : Status.DELETED);
                skillList.add(skill);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return skillList;
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

