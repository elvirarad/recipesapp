package me.elvira.recipesapp.services;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;

public interface FilesServicesRecipe {

    boolean saveToFile(String json);

    String readFromFile();

    File getDataFile();

    ResponseEntity<InputStreamResource> downloadDataFile() throws FileNotFoundException;

    boolean uploadDataFile(MultipartFile file);

    boolean cleanDataFile();
}
