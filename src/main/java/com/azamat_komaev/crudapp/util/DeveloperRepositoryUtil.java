package com.azamat_komaev.crudapp.util;

import com.azamat_komaev.crudapp.model.Developer;
import com.azamat_komaev.crudapp.model.Skill;
import com.azamat_komaev.crudapp.model.Specialty;
import com.azamat_komaev.crudapp.model.Status;
import com.azamat_komaev.crudapp.repository.SkillRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DeveloperRepositoryUtil {
    public static Developer getDeveloperResultSet(ResultSet rs, SkillRepository skillRepository) throws SQLException {
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

    public static void insertSkillsIdsIntoDevelopersSkillsTable(Connection conn,
                                                          Developer developer, int developerId) throws SQLException {
        String insertSkillsIdsIntoM2MTableSqlQuery = "insert into developers_skills (developer_id, skill_id) " +
            "values (?, ?)";

        List<Integer> skillsId = developer.getSkills().stream().map(Skill::getId).toList();
        PreparedStatement preparedStatement = conn.prepareStatement(insertSkillsIdsIntoM2MTableSqlQuery);

        for (int skillId: skillsId) {
            preparedStatement.setInt(1, developerId);
            preparedStatement.setInt(2, skillId);
            preparedStatement.addBatch();
        }

        preparedStatement.executeBatch();
        preparedStatement.close();
    }
}
