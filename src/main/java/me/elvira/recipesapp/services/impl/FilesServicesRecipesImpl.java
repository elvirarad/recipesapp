package me.elvira.recipesapp.services.impl;

import me.elvira.recipesapp.services.FilesServicesRecipe;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
@Service
public class FilesServicesRecipesImpl implements FilesServicesRecipe {


    @Value("${path.to.files.folder}")
    private String recipesFilePath;

    @Value("recipes.json")
    private String recipesFileName;

    @Override
    public boolean saveToFile(String json){
        try {
            cleanDataFile();
            Files.writeString(Path.of(recipesFilePath, recipesFileName), json);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public String readFromFile(){
        try {
            return Files.readString(Path.of(recipesFilePath, recipesFileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public File getDataFile(){
        // класс File несёт служебную информацию: размер, название ...
        return new File(recipesFilePath + "/" + recipesFileName);
    }

    @Override
    public boolean cleanDataFile(){
        try {
            Path path = Path.of(recipesFilePath, recipesFileName);
            Files.deleteIfExists(path);
            Files.createFile(path);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
