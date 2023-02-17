package com.azamat_komaev.crudapp.service;

import static org.junit.Assert.*;

import com.azamat_komaev.crudapp.model.Developer;
import com.azamat_komaev.crudapp.model.Skill;
import com.azamat_komaev.crudapp.model.Specialty;
import com.azamat_komaev.crudapp.model.Status;
import com.azamat_komaev.crudapp.repository.DeveloperRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class DeveloperServiceTest {
    RepositoryService<Developer, Integer> service;
    @Mock
    DeveloperRepository repository;
    List<Skill> skillList = new ArrayList<>();
    Specialty specialty;


    public DeveloperServiceTest() {
        skillList.add(new Skill(1, "Programming", Status.ACTIVE));
        skillList.add(new Skill(2, "Mentoring", Status.ACTIVE));

        specialty = new Specialty(1, "Programmer", Status.ACTIVE);
    }

    @Before
    public void setUp() {
        service = new RepositoryService<>(repository);
    }

    @Test
    public void testGetAllDevelopersEmptyCollection() {
        Mockito.when(repository.getAll()).thenReturn(new ArrayList<>());

        assertEquals(new ArrayList<>(), service.getAll());
        Mockito.verify(repository).getAll();
    }

    @Test
    public void testGetAllSeveralDevelopers() {
        List<Developer> developerList = new ArrayList<>(List.of(
            new Developer(1, "Azamat", "Komaev", Status.ACTIVE, skillList, specialty),
            new Developer(2, "Vlad", "Vladovich", Status.ACTIVE, skillList, specialty)
        ));

        Mockito.when(repository.getAll()).thenReturn(developerList);

        assertEquals(developerList, service.getAll());
        Mockito.verify(repository).getAll();
    }

    @Test(expected = RuntimeException.class)
    public void testThrowRuntimeExceptionWhenGetAllDevelopers() {
        Mockito.when(repository.getAll()).thenThrow(RuntimeException.class);

        repository.getAll();
    }

    @Test
    public void testGetDeveloperById() {
        Developer developer = new Developer(1, "Azamat", "Komaev", Status.ACTIVE, skillList, specialty);

        Mockito.when(repository.getById(1)).thenReturn(developer);

        assertEquals(developer, service.getById(1));
        Mockito.verify(repository).getById(1);
    }

    @Test(expected = RuntimeException.class)
    public void testThrowRuntimeExceptionWhenGetWrongDeveloperById() {
        Mockito.when(repository.getById(100)).thenThrow(RuntimeException.class);

        repository.getById(100);
    }

    @Test
    public void testSaveDeveloper() {
        Developer developerToSave = new Developer(1, "Azamat", "Komaev", Status.ACTIVE, skillList, specialty);

        Mockito.when(repository.save(developerToSave)).thenReturn(developerToSave);

        assertEquals(developerToSave, service.save(developerToSave));
        Mockito.verify(repository).save(developerToSave);
    }

    @Test(expected = RuntimeException.class)
    public void testThrowRuntimeExceptionWhenSaveDeveloper() {
        Developer developerToSave = new Developer(1, "Azamat", "Komaev", Status.ACTIVE, skillList, specialty);

        Mockito.when(repository.save(developerToSave)).thenThrow(RuntimeException.class);

        service.save(developerToSave);
    }

    @Test
    public void testUpdateDeveloper() {
        Developer developerToUpdate = new Developer(1, "Azamat", "Komaev", Status.ACTIVE, skillList, specialty);

        Mockito.when(repository.update(developerToUpdate)).thenReturn(developerToUpdate);

        assertEquals(developerToUpdate, service.update(developerToUpdate));
        Mockito.verify(repository).update(developerToUpdate);
    }

    @Test(expected = RuntimeException.class)
    public void testThrowRuntimeExceptionWhenUpdateDeveloper() {
        Developer developerToUpdate = new Developer(1, "Azamat", "Komaev", Status.ACTIVE, skillList, specialty);

        Mockito.when(repository.update(developerToUpdate)).thenThrow(RuntimeException.class);

        service.update(developerToUpdate);
    }

    @Test
    public void testDeleteDeveloper() {
        Mockito.doNothing().when(repository).deleteById(1);

        service.delete(1);

        Mockito.verify(repository).deleteById(1);
    }

    @Test(expected = RuntimeException.class)
    public void testDeleteWrongDeveloper() {
        Mockito.doThrow(RuntimeException.class).when(repository).deleteById(1);

        service.delete(1);
    }
}
