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
        String sqlQuery = "select d.*, sp.*, group_concat(sk.id separator ' ') as skills_id " +
                          "from developers d left join developers_skills ds on d.id = ds.developer_id " +
                          "left join skills sk on ds.skill_id = sk.id " +
                          "left join specialties sp on d.specialty_id = sp.id " +
                          "where d.id = ? group by d.id";
        Developer developer = null;
        SkillRepository skillRepository = new JdbcSkillRepositoryImpl();

        try (
            Connection conn = Database.getInstance().getConnection();
            PreparedStatement statement = conn.prepareStatement(sqlQuery);
        ) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            if (!rs.next()) {
                return null;
            }

            developer = DeveloperRepositoryUtil.getDeveloperResultSet(rs, skillRepository);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return developer;
    }

    @Override
    public List<Developer> getAll() {
        String sqlQuery = "select d.*, sp.*, group_concat(sk.id separator ' ') as skills_id " +
                          "from developers d left join developers_skills ds on d.id = ds.developer_id " +
                          "left join skills sk on ds.skill_id = sk.id " +
                          "left join specialties sp on d.specialty_id = sp.id group by d.id";
        List<Developer> developerList = new ArrayList<>();
        SkillRepository skillRepository = new JdbcSkillRepositoryImpl();

        try (
            Connection conn = Database.getInstance().getConnection();
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sqlQuery)
        ) {
            while (rs.next()) {
                Developer developer = DeveloperRepositoryUtil.getDeveloperResultSet(rs, skillRepository);
                developerList.add(developer);
            }

            return developerList;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return developerList;
    }

    @Override
    public Developer save(Developer developerToSave) {
        String insertDeveloperSqlQuery = "insert into developers (first_name, last_name, specialty_id) " +
                                         "values (?, ?, ?)";
        try (
            Connection conn = Database.getInstance().getConnection();
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
                return null;
            }

            int developerId = rs.getInt(1);
            DeveloperRepositoryUtil.insertSkillsIdsIntoDevelopersSkillsTable(conn, developerToSave, developerId);

            rs.close();

            conn.commit();
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return developerToSave;
    }

    @Override
    public Developer update(Developer developerToUpdate) {
        String updateDeveloperSqlQuery = "update developers set first_name = ?, last_name = ?, specialty_id = ? " +
                                         "where id = ?";
        String deleteOldDeveloperSkillsSqlQuery = "delete from developers_skills where developer_id = ?";

        try (
            Connection conn = Database.getInstance().getConnection();
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
            return null;
        }

        return developerToUpdate;
    }

    @Override
    public void deleteById(Integer id) {

    }
}
