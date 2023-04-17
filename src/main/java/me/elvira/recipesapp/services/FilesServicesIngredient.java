package me.elvira.recipesapp.services;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface FilesServicesIngredient {

    ResponseEntity<Void> uploadDataFile(@RequestParam MultipartFile file);

    boolean saveToFile(String json);

    String readFromFile();

    boolean cleanDataFile();

    File getDataFile();
}
