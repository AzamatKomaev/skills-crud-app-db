package com.azamat_komaev.crudapp.repository.jdbc;

import com.azamat_komaev.crudapp.config.Database;
import com.azamat_komaev.crudapp.model.Specialty;
import com.azamat_komaev.crudapp.model.Status;
import com.azamat_komaev.crudapp.repository.SpecialtyRepository;

import java.sql.*;
import java.util.*;

public class JdbcSpecialtyRepositoryImpl implements SpecialtyRepository {
    public JdbcSpecialtyRepositoryImpl() {
    }

    @Override
    public Specialty getById(Integer id) {
        String sqlQuery = "select * from specialties where id = ?";
        Specialty specialty;

        try (
            Connection conn = Database.getInstance().getConnection();
            PreparedStatement statement = conn.prepareStatement(sqlQuery);
        ) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            if (!rs.next()) {
                throw new RuntimeException("Cannot find skill entry with id=" + id);
            }

            specialty = new Specialty(rs.getInt("id"), rs.getString("name"),
                                      rs.getBoolean("active") ? Status.ACTIVE : Status.DELETED);
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

        return specialty;
    }

    @Override
    public List<Specialty> getAll() {
        String sqlQuery = "select * from specialties";
        List<Specialty> specialtyList = new ArrayList<>();

        try (
            Connection conn = Database.getInstance().getConnection();
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sqlQuery)
        ) {
            while (rs.next()) {
                Specialty specialty = new Specialty(rs.getInt("id"), rs.getString("name"),
                                                    rs.getBoolean("active") ? Status.ACTIVE : Status.DELETED);
                specialtyList.add(specialty);
            }

            return specialtyList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Specialty save(Specialty specialtyToSave) {
        String sqlQuery = "insert into specialties (name) values (?)";

        try (
            Connection conn = Database.getInstance().getConnection();
            PreparedStatement statement = conn.prepareStatement(sqlQuery);
        ) {
            statement.setString(1, specialtyToSave.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

        return specialtyToSave;
    }

    @Override
    public Specialty update(Specialty specialty) {
        String sqlQuery = "update specialties set name = ? where id = ?";

        try (
            Connection conn = Database.getInstance().getConnection();
            PreparedStatement statement = conn.prepareStatement(sqlQuery)
        ) {
            statement.setString(1, specialty.getName());
            statement.setInt(2, specialty.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

        return specialty;
    }

    @Override
    public void deleteById(Integer id) {
        String sqlQuery = "update specialties set active = false where id = ?";
        try (
            Connection conn = Database.getInstance().getConnection();
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
