package me.elvira.recipesapp.services;

import java.io.File;

public interface FilesServicesRecipe {

    boolean saveToFile(String json);

    String readFromFile();

    File getDataFile();

    boolean cleanDataFile();
}
