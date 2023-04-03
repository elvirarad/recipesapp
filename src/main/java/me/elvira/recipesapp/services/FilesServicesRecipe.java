package me.elvira.recipesapp.services;

public interface FilesServicesRecipe {

    boolean saveToFile(String json);

    String readFromFile();

    boolean cleanDataFile();
}
