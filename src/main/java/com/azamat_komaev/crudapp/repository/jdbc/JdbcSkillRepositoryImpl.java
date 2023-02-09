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
        String sqlQuery = "select * from skills where id = ?";
        Skill skill = null;

        try (
            Connection conn = Database.getInstance().getConnection();
            PreparedStatement statement = conn.prepareStatement(sqlQuery);
        ) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            if (!rs.next()) {
                return null;
            }

            skill = new Skill(rs.getInt("id"), rs.getString("name"),
                              rs.getBoolean("active") ? Status.ACTIVE : Status.DELETED);

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return skill;
    }

    @Override
    public List<Skill> getAll() {
        String sqlQuery = "select * from skills";
        List<Skill> skillList = new ArrayList<>();

        try (
            Connection conn = Database.getInstance().getConnection();
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sqlQuery)
        ) {
            while (rs.next()) {
                Skill skill = new Skill(rs.getInt("id"), rs.getString("name"),
                                        rs.getBoolean("active") ? Status.ACTIVE : Status.DELETED);
                skillList.add(skill);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return skillList;
    }

    @Override
    public Skill save(Skill skillToSave) {
        String sqlQuery = "insert into skills (name) values (?)";

        try (
            Connection conn = Database.getInstance().getConnection();
            PreparedStatement statement = conn.prepareStatement(sqlQuery);
        ) {
            statement.setString(1, skillToSave.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return skillToSave;
    }

    @Override
    public Skill update(Skill skill) {
        String sqlQuery = "update skills set name = ? where id = ?";

        try (
            Connection conn = Database.getInstance().getConnection();
            PreparedStatement statement = conn.prepareStatement(sqlQuery)
        ) {
            statement.setString(1, skill.getName());
            statement.setInt(2, skill.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return skill;
    }

    @Override
    public void deleteById(Integer id) {
        String sqlQuery = "delete from skills where id = ?";

        try (
            Connection conn = Database.getInstance().getConnection();
            PreparedStatement statement = conn.prepareStatement(sqlQuery)
        ) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

