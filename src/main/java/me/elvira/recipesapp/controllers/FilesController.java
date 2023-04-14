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
            return filesServicesRecipe.downloadDataFile();
    }


    @PostMapping(value = "/importRecipes", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "скачать из файла рецепты")
    public ResponseEntity<Void> uploadDataRecipeFile(@RequestParam MultipartFile file) {
        return filesServicesRecipe.uploadDataFile(file);
    }
        //MultipartFile - данные о том, что б.загружено. Данные отправл-ся кусками (один из них - ф-л)

    @PostMapping(value = "/importIngredients", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "скачать из файла ингредиенты")
    public ResponseEntity<Void> uploadDataIngredientsFile(@RequestParam MultipartFile file) {
        return filesServicesIngredient.uploadDataFile(file);}
}
