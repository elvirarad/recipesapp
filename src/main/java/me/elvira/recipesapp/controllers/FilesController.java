package me.elvira.recipesapp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.elvira.recipesapp.services.FilesServicesRecipe;
import me.elvira.recipesapp.services.FilesServicesIngredient;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@RestController
@RequestMapping("/files")
@Tag(name = "Файл с рецептами", description = "CRUD-операции и другие эндпоинты для работы с файлом рецептов")

public class FilesController {

    private final FilesServicesRecipe filesServicesRecipe; //инжектим Service
    private final FilesServicesIngredient filesServicesIngredient;

    public FilesController(FilesServicesRecipe filesServicesRecipe, FilesServicesIngredient filesServicesIngredient) {
        this.filesServicesRecipe = filesServicesRecipe;
        this.filesServicesIngredient = filesServicesIngredient;
    }

    @GetMapping(value = "/exportRecipes")
    @Operation(summary = "скачать в файл рецепты")
    public ResponseEntity<InputStreamResource> downloadDataRecipeFile() throws FileNotFoundException {
        File file = filesServicesRecipe.getDataFile();       //получаем инф.о самом файле
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

    @PostMapping(value = "/importRecipes", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "скачать из файла рецепты")
    public ResponseEntity<Void> uploadDataRecipeFile(@RequestParam MultipartFile file) {
        //MultipartFile - данные о том, что б.загружено. Данные отправл-ся кусками (один из них - ф-л)
        filesServicesRecipe.cleanDataFile();
        File dataFile = filesServicesRecipe.getDataFile();

        try (FileOutputStream fos = new FileOutputStream(dataFile)){
            IOUtils.copy(file.getInputStream(), fos);
            return ResponseEntity.ok().build();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @PostMapping(value = "/importIngredients", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "скачать из файла ингредиенты")
    public ResponseEntity<Void> uploadDataIngredientsFile(@RequestParam MultipartFile file) {
        filesServicesIngredient.cleanDataFile();
        File dataFile = filesServicesIngredient.getDataFile();

        try (FileOutputStream fos = new FileOutputStream(dataFile)){
            IOUtils.copy(file.getInputStream(), fos);
            return ResponseEntity.ok().build();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
