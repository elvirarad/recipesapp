package me.elvira.recipesapp.services.impl;

import me.elvira.recipesapp.services.FilesServicesRecipe;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
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
    public ResponseEntity<InputStreamResource> downloadDataFile() throws FileNotFoundException {
        File file = getDataFile();       //получаем инф.о самом файле
        if (file.exists()){                                  //сужествует это ф-л?
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            //из file открываем поток FileInputStream, читать не б., исп-ем как рессурс InputStreamResource
            //отправляем этот рессурс клиенту в http-ответе. А браузер,Swagger,postman начнёт автом-ки ф-л скчивать
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)  // тип - поток байт, не json
                    .contentLength(file.length())                     //иначе браузер не знает сколько скачивать
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"RecipeLog.json\"")
                    //добавляем заголовок, attachment - надо скачить с названием ф-ла RecipeLog.json
                    .body(resource);
        } else {
            return ResponseEntity.noContent().build();              //204 статус, но содержимого неть
        }
    }

    @Override
    public boolean uploadDataFile(MultipartFile file) {
        //MultipartFile - данные о том, что б.загружено. Данные отправл-ся кусками (один из них - ф-л)
        cleanDataFile();
        File dataFile = getDataFile();

        try (FileOutputStream fos = new FileOutputStream(dataFile)) {
            IOUtils.copy(file.getInputStream(), fos);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
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
