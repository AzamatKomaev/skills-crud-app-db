package com.azamat_komaev.crudapp.service;

import static org.junit.Assert.*;

import com.azamat_komaev.crudapp.model.Skill;
import com.azamat_komaev.crudapp.model.Status;
import com.azamat_komaev.crudapp.repository.SkillRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class SkillServiceTest {
    RepositoryService<Skill, Integer> service;
    @Mock
    SkillRepository repository;

    @Before
    public void setUp() {
        service = new RepositoryService<>(repository);
    }

    @Test
    public void testGetAllSkillsEmptyCollection() {
        Mockito.when(repository.getAll()).thenReturn(new ArrayList<>());

        assertEquals(new ArrayList<>(), service.getAll());
        Mockito.verify(repository).getAll();
    }

    @Test
    public void testGetAllSeveralSkills() {
        List<Skill> skillList = new ArrayList<>(List.of(
            new Skill(1, "Programming", Status.ACTIVE),
            new Skill(2, "Teaching", Status.ACTIVE)
        ));

        Mockito.when(repository.getAll()).thenReturn(skillList);

        assertEquals(skillList, service.getAll());
        Mockito.verify(repository).getAll();
    }

    @Test(expected = RuntimeException.class)
    public void testThrowRuntimeExceptionWhenGetAllSkills() {
        Mockito.when(repository.getAll()).thenThrow(RuntimeException.class);

        repository.getAll();
    }

    @Test
    public void testGetSkillById() {
        Skill skill = new Skill(1, "Programming", Status.ACTIVE);

        Mockito.when(repository.getById(1)).thenReturn(skill);

        assertEquals(skill, service.getById(1));
        Mockito.verify(repository).getById(1);
    }

    @Test(expected = RuntimeException.class)
    public void testThrowRuntimeExceptionWhenGetWrongSkillById() {
        Mockito.when(repository.getById(100)).thenThrow(RuntimeException.class);

        repository.getById(100);
    }

    @Test
    public void testSaveSkill() {
        Skill skillToSave = new Skill(2, "Mentoring", Status.ACTIVE);

        Mockito.when(repository.save(skillToSave)).thenReturn(skillToSave);

        assertEquals(skillToSave, service.save(skillToSave));
        Mockito.verify(repository).save(skillToSave);
    }

    @Test(expected = RuntimeException.class)
    public void testThrowRuntimeExceptionWhenSaveSkill() {
        Skill skillToSave = new Skill(3, "Testing", Status.ACTIVE);

        Mockito.when(repository.save(skillToSave)).thenThrow(RuntimeException.class);

        service.save(skillToSave);
    }

    @Test
    public void testUpdateSkill() {
        Skill skillToUpdate = new Skill(1, "Programming", Status.ACTIVE);

        Mockito.when(repository.update(skillToUpdate)).thenReturn(skillToUpdate);

        assertEquals(skillToUpdate, service.update(skillToUpdate));
        Mockito.verify(repository).update(skillToUpdate);
    }

    @Test(expected = RuntimeException.class)
    public void testThrowRuntimeExceptionWhenUpdateSkill() {
        Skill skillToUpdate = new Skill(3, "Learning", Status.ACTIVE);

        Mockito.when(repository.update(skillToUpdate)).thenThrow(RuntimeException.class);

        service.update(skillToUpdate);
    }

    @Test
    public void testDeleteSkill() {
        Mockito.doNothing().when(repository).deleteById(1);

        service.delete(1);

        Mockito.verify(repository).deleteById(1);
    }

    @Test(expected = RuntimeException.class)
    public void testDeleteWrongSkill() {
        Mockito.doThrow(RuntimeException.class).when(repository).deleteById(1);

        service.delete(1);
    }
}
