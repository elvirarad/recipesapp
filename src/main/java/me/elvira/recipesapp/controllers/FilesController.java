package me.elvira.recipesapp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.elvira.recipesapp.model.Recipe;
import me.elvira.recipesapp.services.FilesServicesRecipe;
import me.elvira.recipesapp.services.FilesServicesIngredient;
import me.elvira.recipesapp.services.RecipesServices;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("/files")
@Tag(name = "Файл с рецептами", description = "CRUD-операции и другие эндпоинты для работы с файлом рецептов")

public class FilesController {

    private final FilesServicesRecipe filesServicesRecipe; //инжектим Service
    private final FilesServicesIngredient filesServicesIngredient;

    private final RecipesServices recipesServices;

    public FilesController(FilesServicesRecipe filesServicesRecipe, FilesServicesIngredient filesServicesIngredient, RecipesServices recipesServices) {
        this.filesServicesRecipe = filesServicesRecipe;
        this.filesServicesIngredient = filesServicesIngredient;
        this.recipesServices = recipesServices;
    }

    @GetMapping(value = "/exportRecipes")
    @Operation(summary = "Загрузка файла рецептов")
    public ResponseEntity<InputStreamResource> downloadDataRecipeFile() throws FileNotFoundException {
            return filesServicesRecipe.downloadDataFile();
    }


    @PostMapping(value = "/importRecipes", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "скачать из файла рецепты")
    public ResponseEntity<Void> uploadDataRecipeFile(@RequestParam MultipartFile file) {
        if (filesServicesRecipe.uploadDataFile(file)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
        //MultipartFile - данные о том, что б.загружено. Данные отправл-ся кусками (один из них - ф-л)

    @PostMapping(value = "/importIngredients", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "скачать из файла ингредиенты")
    public ResponseEntity<Void> uploadDataIngredientsFile(@RequestParam MultipartFile file) {
        return filesServicesIngredient.uploadDataFile(file);}

    @GetMapping("/export/recipesTxt")
    @Operation(
            summary = "Загрузка в файл рецептов из памяти",
            description = "Скачать в файл рецепты в формате .txt"
    )

    public void downloadRecipes(HttpServletResponse response) throws IOException {
        ContentDisposition disposition = ContentDisposition.attachment()
                .name("recipes.txt")
                .build();
        response.addHeader(HttpHeaders.CONTENT_DISPOSITION, disposition.toString());
        response.setContentType("text/plain");
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        recipesServices.exportFileTxt(response.getWriter());
    }

}
