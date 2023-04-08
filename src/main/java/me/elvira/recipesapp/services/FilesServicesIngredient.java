package me.elvira.recipesapp.services;

import java.io.File;

public interface FilesServicesIngredient {

    boolean saveToFile(String json);

    String readFromFile();

    boolean cleanDataFile();

    File getDataFile();
}
