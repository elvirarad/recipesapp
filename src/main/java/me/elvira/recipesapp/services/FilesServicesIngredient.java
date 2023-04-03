package me.elvira.recipesapp.services;

public interface FilesServicesIngredient {

    boolean saveToFile(String json);

    String readFromFile();

    boolean cleanDataFile();
}
