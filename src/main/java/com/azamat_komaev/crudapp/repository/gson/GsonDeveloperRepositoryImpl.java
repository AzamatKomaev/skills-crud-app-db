package com.azamat_komaev.crudapp.repository.gson;

import com.azamat_komaev.crudapp.model.Developer;
import com.azamat_komaev.crudapp.model.Status;
import com.azamat_komaev.crudapp.repository.DeveloperRepository;
import com.azamat_komaev.crudapp.service.RepositoryService;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class GsonDeveloperRepositoryImpl implements DeveloperRepository {
    private final RepositoryService<Developer> repositoryService;

    public GsonDeveloperRepositoryImpl() {
        this.repositoryService = new RepositoryService<>("src/main/resources/developers.json");
    }

    private Integer generateNewId(List<Developer> developers) {
        Developer maxIdDeveloper = developers.stream().max(Comparator.comparing(Developer::getId)).orElse(null);
        return Objects.nonNull(maxIdDeveloper) ? maxIdDeveloper.getId() + 1 : 1;
    }

    @Override
    public Developer getById(Integer id) {
        return this.repositoryService.getItemsFromFile(Developer.class).stream()
            .filter(s -> s.getId().equals(id))
            .findFirst()
            .orElse(null);
    }

    @Override
    public List<Developer> getAll() {
        return this.repositoryService.getItemsFromFile(Developer.class);
    }

    @Override
    public Developer save(Developer developerToSave) {
        List<Developer> currentDevelopers = this.repositoryService.getItemsFromFile(Developer.class);

        Integer id = generateNewId(currentDevelopers);
        developerToSave.setId(id);
        currentDevelopers.add(developerToSave);

        this.repositoryService.addItemsToFile(currentDevelopers);
        return developerToSave;
    }

    @Override
    public Developer update(Developer developer) {
        List<Developer> currentDevelopers = this.repositoryService.getItemsFromFile(Developer.class);

        AtomicBoolean wasUpdated = new AtomicBoolean(false);
        currentDevelopers = currentDevelopers.stream()
            .map(d -> {
                if (Objects.equals(d.getId(), developer.getId())){
                    wasUpdated.set(true);
                    return developer;
                }
                return d;
            })
            .collect(Collectors.toList());

        this.repositoryService.addItemsToFile(currentDevelopers);
        return wasUpdated.get() ? developer : null;
    }

    @Override
    public void deleteById(Integer id) {
        List<Developer> currentDevelopers = this.repositoryService.getItemsFromFile(Developer.class);

        currentDevelopers = currentDevelopers.stream()
            .peek(d -> {
                if (Objects.equals(d.getId(), id)) {
                    d.setStatus(Status.DELETED);
                }
            })
            .collect(Collectors.toList());

        this.repositoryService.addItemsToFile(currentDevelopers);
    }
}
