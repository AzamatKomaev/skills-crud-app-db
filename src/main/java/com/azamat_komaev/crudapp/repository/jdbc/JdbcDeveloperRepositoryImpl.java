package com.azamat_komaev.crudapp.repository.jdbc;

import com.azamat_komaev.crudapp.config.Database;
import com.azamat_komaev.crudapp.model.Developer;
import com.azamat_komaev.crudapp.model.Skill;
import com.azamat_komaev.crudapp.model.Specialty;
import com.azamat_komaev.crudapp.model.Status;
import com.azamat_komaev.crudapp.repository.DeveloperRepository;
import com.azamat_komaev.crudapp.repository.SkillRepository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class JdbcDeveloperRepositoryImpl implements DeveloperRepository {

    public JdbcDeveloperRepositoryImpl() {
    }

    private Developer getDeveloperResultSet(ResultSet rs, SkillRepository skillRepository) throws SQLException {
        int developerId = rs.getInt("d.id");
        String firstName = rs.getString("first_name");
        String lastName = rs.getString("last_name");
        Status isDeveloperActive = rs.getBoolean("d.active") ? Status.ACTIVE : Status.DELETED;

        int specialtyId = rs.getInt("specialty_id");
        String specialtyName = rs.getString("sp.name");
        Status isSpecialtyActive = rs.getBoolean("sp.active") ? Status.ACTIVE : Status.DELETED;

        String skillsId = rs.getString("skills_id");

        Specialty specialty = new Specialty(specialtyId, specialtyName, isSpecialtyActive);
        List<Skill> skillList = new ArrayList<>();

        if (skillsId != null) {
            for (String skillId: skillsId.split(" ")) {
                skillList.add(skillRepository.getById(Integer.valueOf(skillId)));
            }
        }

        return new Developer(developerId, firstName, lastName,
                             isDeveloperActive, skillList, specialty);
    }

    @Override
    public Developer getById(Integer id) {
        return null;
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
            Map<Integer, Developer> developerMap = new HashMap<>();

            while (rs.next()) {
                int developerId = rs.getInt("d.id");

                if (developerMap.containsKey(developerId)) {
                    continue;
                }

                Developer developer = getDeveloperResultSet(rs, skillRepository);
                developerMap.put(developerId, developer);
            }

            return new ArrayList<>(developerMap.values());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return developerList;
    }

    @Override
    public Developer save(Developer developerToSave) {
        return null;
    }

    @Override
    public Developer update(Developer developer) {
        return null;
    }

    @Override
    public void deleteById(Integer id) {

    }
}
