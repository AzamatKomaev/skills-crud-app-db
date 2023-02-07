package com.azamat_komaev.crudapp.repository.gson;

import com.azamat_komaev.crudapp.model.Specialty;
import com.azamat_komaev.crudapp.model.Status;
import com.azamat_komaev.crudapp.repository.SpecialtyRepository;
import com.azamat_komaev.crudapp.service.RepositoryService;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class GsonSpecialtyRepositoryImpl implements SpecialtyRepository {
    private final RepositoryService<Specialty> repositoryService;

    public GsonSpecialtyRepositoryImpl() {
        this.repositoryService = new RepositoryService<>("src/main/resources/specialties.json");
    }

    private Integer generateNewId(List<Specialty> specialties) {
        Specialty maxIdSpecialty = specialties.stream().max(Comparator.comparing(Specialty::getId)).orElse(null);
        return Objects.nonNull(maxIdSpecialty) ? maxIdSpecialty.getId() + 1 : 1;
    }

    @Override
    public Specialty getById(Integer id) {
        return this.repositoryService.getItemsFromFile(Specialty.class).stream()
            .filter(s -> s.getId().equals(id))
            .findFirst()
            .orElse(null);
    }

    @Override
    public List<Specialty> getAll() {
        return this.repositoryService.getItemsFromFile(Specialty.class);
    }

    @Override
    public Specialty save(Specialty specialtyToSave) {
        List<Specialty> currentSpecialties = this.repositoryService.getItemsFromFile(Specialty.class);

        Integer id = generateNewId(currentSpecialties);
        specialtyToSave.setId(id);
        currentSpecialties.add(specialtyToSave);

        this.repositoryService.addItemsToFile(currentSpecialties);
        return specialtyToSave;
    }

    @Override
    public Specialty update(Specialty specialty) {
        List<Specialty> currentSpecialties = this.repositoryService.getItemsFromFile(Specialty.class);

        AtomicBoolean wasUpdated = new AtomicBoolean(false);
        currentSpecialties = currentSpecialties.stream()
            .map(s -> {
                if (Objects.equals(s.getId(), specialty.getId())) {
                    wasUpdated.set(true);
                    return specialty;
                }
                return s;
            })
            .collect(Collectors.toList());

        this.repositoryService.addItemsToFile(currentSpecialties);
        return wasUpdated.get() ? specialty : null;
    }

    @Override
    public void deleteById(Integer id) {
        List<Specialty> currentSpecialties = this.repositoryService.getItemsFromFile(Specialty.class);

        currentSpecialties = currentSpecialties.stream()
            .peek(s -> {
                if (Objects.equals(s.getId(), id)) {
                    s.setStatus(Status.DELETED);
                }
            })
            .collect(Collectors.toList());

        this.repositoryService.addItemsToFile(currentSpecialties);
    }
}
