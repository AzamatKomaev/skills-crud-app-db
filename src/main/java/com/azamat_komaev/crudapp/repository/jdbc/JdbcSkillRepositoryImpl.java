package com.azamat_komaev.crudapp.repository.jdbc;

import com.azamat_komaev.crudapp.config.Database;
import com.azamat_komaev.crudapp.model.Skill;
import com.azamat_komaev.crudapp.model.Status;
import com.azamat_komaev.crudapp.repository.SkillRepository;

import java.sql.*;
import java.util.*;

public class JdbcSkillRepositoryImpl implements SkillRepository {

    public JdbcSkillRepositoryImpl() {
    }

    @Override
    public Skill getById(Integer id) {
        Connection conn = Database.getInstance().getConnection();
        String sqlQuery = "select * from skills where id = ?";
        Skill skill;

        try (
            PreparedStatement statement = conn.prepareStatement(sqlQuery)
        ) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            if (!rs.next()) {
                throw new RuntimeException("Cannot find skill entry with id=" + id);
            }

            skill = new Skill(rs.getInt("id"), rs.getString("name"),
                              rs.getBoolean("active") ? Status.ACTIVE : Status.DELETED);
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

        return skill;
    }

    @Override
    public List<Skill> getAll() {
        Connection conn = Database.getInstance().getConnection();
        String sqlQuery = "select * from skills";
        List<Skill> skillList = new ArrayList<>();

        try (
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sqlQuery)
        ) {
            while (rs.next()) {
                Skill skill = new Skill(rs.getInt("id"), rs.getString("name"),
                                        rs.getBoolean("active") ? Status.ACTIVE : Status.DELETED);
                skillList.add(skill);
            }

            return skillList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Skill save(Skill skillToSave) {
        Connection conn = Database.getInstance().getConnection();
        String sqlQuery = "insert into skills (name) values (?)";

        try (
            PreparedStatement statement = conn.prepareStatement(sqlQuery);
        ) {
            statement.setString(1, skillToSave.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

        return skillToSave;
    }

    @Override
    public Skill update(Skill skill) {
        Connection conn = Database.getInstance().getConnection();
        String sqlQuery = "update skills set name = ? where id = ?";

        try (
            PreparedStatement statement = conn.prepareStatement(sqlQuery)
        ) {
            statement.setString(1, skill.getName());
            statement.setInt(2, skill.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

        return skill;
    }

    @Override
    public void deleteById(Integer id) {
        Connection conn = Database.getInstance().getConnection();
        String sqlQuery = "update skills set active = true where id = ?";

        try (
            PreparedStatement statement = conn.prepareStatement(sqlQuery)
        ) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}

