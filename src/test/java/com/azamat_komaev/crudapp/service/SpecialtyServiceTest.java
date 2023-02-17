package com.azamat_komaev.crudapp.service;

import static org.junit.Assert.*;

import com.azamat_komaev.crudapp.model.Specialty;
import com.azamat_komaev.crudapp.model.Status;
import com.azamat_komaev.crudapp.repository.SpecialtyRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class SpecialtyServiceTest {
    RepositoryService<Specialty, Integer> service;
    @Mock
    SpecialtyRepository repository;

    @Before
    public void setUp() {
        service = new RepositoryService<>(repository);
    }

    @Test
    public void testGetAllSpecialtyEmptyCollection() {
        Mockito.when(repository.getAll()).thenReturn(new ArrayList<>());

        assertEquals(new ArrayList<>(), service.getAll());
        Mockito.verify(repository).getAll();
    }

    @Test
    public void testGetAllSeveralSpecialties() {
        List<Specialty> specialtyList = new ArrayList<>(List.of(
            new Specialty(1, "Programmer", Status.ACTIVE),
            new Specialty(2, "HR", Status.ACTIVE)
        ));

        Mockito.when(repository.getAll()).thenReturn(specialtyList);

        assertEquals(specialtyList, service.getAll());
        Mockito.verify(repository).getAll();
    }

    @Test(expected = RuntimeException.class)
    public void testThrowRuntimeExceptionWhenGetAllSpecialties() {
        Mockito.when(repository.getAll()).thenThrow(RuntimeException.class);

        repository.getAll();
    }

    @Test
    public void testGetSpecialtyById() {
        Specialty specialty = new Specialty(1, "Programming", Status.ACTIVE);

        Mockito.when(repository.getById(1)).thenReturn(specialty);

        assertEquals(specialty, service.getById(1));
        Mockito.verify(repository).getById(1);
    }

    @Test(expected = RuntimeException.class)
    public void testThrowRuntimeExceptionWhenGetWrongSpecialtyById() {
        Mockito.when(repository.getById(100)).thenThrow(RuntimeException.class);

        repository.getById(100);
    }

    @Test
    public void testSaveSpecialty() {
        Specialty specialtyToSave = new Specialty(4, "UI/UX", Status.ACTIVE);

        Mockito.when(repository.save(specialtyToSave)).thenReturn(specialtyToSave);

        assertEquals(specialtyToSave, service.save(specialtyToSave));
        Mockito.verify(repository).save(specialtyToSave);
    }

    @Test(expected = RuntimeException.class)
    public void testThrowRuntimeExceptionWhenSaveSpecialty() {
        Specialty specialtyToSave = new Specialty(5, "Designer", Status.ACTIVE);

        Mockito.when(repository.save(specialtyToSave)).thenThrow(RuntimeException.class);

        service.save(specialtyToSave);
    }

    @Test
    public void testUpdateSpecialty() {
        Specialty specialtyToUpdate = new Specialty(1, "Programming", Status.ACTIVE);

        Mockito.when(repository.update(specialtyToUpdate)).thenReturn(specialtyToUpdate);

        assertEquals(specialtyToUpdate, service.update(specialtyToUpdate));
        Mockito.verify(repository).update(specialtyToUpdate);
    }

    @Test(expected = RuntimeException.class)
    public void testThrowRuntimeExceptionWhenUpdateSpecialty() {
        Specialty specialtyToUpdate = new Specialty(3, "HR", Status.ACTIVE);

        Mockito.when(repository.update(specialtyToUpdate)).thenThrow(RuntimeException.class);

        service.update(specialtyToUpdate);
    }

    @Test
    public void testDeleteSpecialty() {
        Mockito.doNothing().when(repository).deleteById(1);

        service.delete(1);

        Mockito.verify(repository).deleteById(1);
    }

    @Test(expected = RuntimeException.class)
    public void testDeleteWrongSpecialty() {
        Mockito.doThrow(RuntimeException.class).when(repository).deleteById(1);

        service.delete(1);
    }
}
