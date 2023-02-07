package com.azamat_komaev.crudapp.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class RepositoryService<T> {
    private final String FILE_PATH;
    private final Gson GSON = new Gson();

    public RepositoryService(String filePath) {
        this.FILE_PATH = filePath;
    }

    public List<T> getItemsFromFile(Class<T> modelType) {
        String fileContent;
        List<T> modelList;
        List<T> emptyList = new ArrayList<>(Collections.emptyList());

        try {
            fileContent = new String(Files.readAllBytes(Paths.get(this.FILE_PATH)));
            Type type = TypeToken.getParameterized(List.class, modelType).getType();
            modelList = GSON.fromJson(fileContent, type);
        } catch (IOException e) {
            e.printStackTrace();
            return emptyList;
        }

        return modelList != null ? modelList : emptyList;
    }

    public void addItemsToFile(List<T> elements) {

    }
}
