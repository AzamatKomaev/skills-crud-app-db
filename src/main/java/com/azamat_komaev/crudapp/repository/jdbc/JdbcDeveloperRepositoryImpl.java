package com.azamat_komaev.crudapp.repository.jdbc;

import com.azamat_komaev.crudapp.config.Database;
import com.azamat_komaev.crudapp.model.Developer;
import com.azamat_komaev.crudapp.model.Skill;
import com.azamat_komaev.crudapp.model.Specialty;
import com.azamat_komaev.crudapp.model.Status;
import com.azamat_komaev.crudapp.repository.DeveloperRepository;
import com.azamat_komaev.crudapp.repository.SkillRepository;
import com.azamat_komaev.crudapp.util.DeveloperRepositoryUtil;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class JdbcDeveloperRepositoryImpl implements DeveloperRepository {

    public JdbcDeveloperRepositoryImpl() {
    }

    @Override
    public Developer getById(Integer id) {
        Connection conn = Database.getInstance().getConnection();
        String sqlQuery = "select d.*, sp.*, group_concat(sk.id separator ' ') as skills_id " +
                          "from developers d left join developers_skills ds on d.id = ds.developer_id " +
                          "left join skills sk on ds.skill_id = sk.id " +
                          "left join specialties sp on d.specialty_id = sp.id " +
                          "where d.id = ? group by d.id";
        SkillRepository skillRepository = new JdbcSkillRepositoryImpl();
        Developer developer;

        try (
            PreparedStatement statement = conn.prepareStatement(sqlQuery);
        ) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            if (!rs.next()) {
                throw new RuntimeException("Cannot find developer entry with id=" + id);
            }

            developer = DeveloperRepositoryUtil.getDeveloperFromResultSet(rs, skillRepository);
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

        return developer;
    }

    @Override
    public List<Developer> getAll() {
        Connection conn = Database.getInstance().getConnection();
        String sqlQuery = "select d.*, sp.*, group_concat(sk.id separator ' ') as skills_id " +
                          "from developers d left join developers_skills ds on d.id = ds.developer_id " +
                          "left join skills sk on ds.skill_id = sk.id " +
                          "left join specialties sp on d.specialty_id = sp.id group by d.id";
        List<Developer> developerList = new ArrayList<>();
        SkillRepository skillRepository = new JdbcSkillRepositoryImpl();

        try (
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sqlQuery)
        ) {
            while (rs.next()) {
                Developer developer = DeveloperRepositoryUtil.getDeveloperFromResultSet(rs, skillRepository);
                developerList.add(developer);
            }

            return developerList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Developer save(Developer developerToSave) {
        Connection conn = Database.getInstance().getConnection();
        String insertDeveloperSqlQuery = "insert into developers (first_name, last_name, specialty_id) " +
                                         "values (?, ?, ?)";

        try (
            PreparedStatement preparedStatement = conn.prepareStatement(insertDeveloperSqlQuery,
                                                                        Statement.RETURN_GENERATED_KEYS);
        ) {
            conn.setAutoCommit(false);

            preparedStatement.setString(1, developerToSave.getFirstName());
            preparedStatement.setString(2, developerToSave.getLastName());
            preparedStatement.setInt(3, developerToSave.getSpecialty().getId());
            preparedStatement.executeUpdate();

            ResultSet rs = preparedStatement.getGeneratedKeys();

            if (!rs.next()) {
                throw new RuntimeException("Cannot get generated keys from result set!");
            }

            int developerId = rs.getInt(1);
            DeveloperRepositoryUtil.insertSkillsIdsIntoDevelopersSkillsTable(conn, developerToSave, developerId);

            rs.close();
            conn.commit();
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

        return developerToSave;
    }

    @Override
    public Developer update(Developer developerToUpdate) {
        Connection conn = Database.getInstance().getConnection();
        String updateDeveloperSqlQuery = "update developers set first_name = ?, last_name = ?, specialty_id = ? " +
                                         "where id = ?";
        String deleteOldDeveloperSkillsSqlQuery = "delete from developers_skills where developer_id = ?";

        try (
            PreparedStatement preparedUpdateStatement = conn.prepareStatement(updateDeveloperSqlQuery);
            PreparedStatement preparedDeleteStatement = conn.prepareStatement(deleteOldDeveloperSkillsSqlQuery)
        ) {
            conn.setAutoCommit(false);

            preparedUpdateStatement.setString(1, developerToUpdate.getFirstName());
            preparedUpdateStatement.setString(2, developerToUpdate.getLastName());
            preparedUpdateStatement.setInt(3, developerToUpdate.getSpecialty().getId());
            preparedUpdateStatement.setInt(4, developerToUpdate.getId());
            preparedUpdateStatement.executeUpdate();

            preparedDeleteStatement.setInt(1, developerToUpdate.getId());
            preparedDeleteStatement.executeUpdate();

            DeveloperRepositoryUtil.insertSkillsIdsIntoDevelopersSkillsTable(conn, developerToUpdate,
                                                                             developerToUpdate.getId());

            conn.commit();
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

        return developerToUpdate;
    }

    @Override
    public void deleteById(Integer id) {
        Connection conn = Database.getInstance().getConnection();
        String deleteDeveloperSqlQuery = "delete from developers where id = ?";
        String deleteDevelopersSkillsRelationsSqlQuery = "delete from developers_skills where developer_id = ?";

        try (
            PreparedStatement preparedDeleteRelationsStatement = conn.prepareStatement(deleteDevelopersSkillsRelationsSqlQuery);
            PreparedStatement preparedDeleteDeveloperStatement = conn.prepareStatement(deleteDeveloperSqlQuery);
        ) {
            conn.setAutoCommit(false);

            preparedDeleteRelationsStatement.setInt(1, id);
            preparedDeleteDeveloperStatement.setInt(1, id);

            preparedDeleteRelationsStatement.executeUpdate();
            preparedDeleteDeveloperStatement.executeUpdate();

            conn.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}
