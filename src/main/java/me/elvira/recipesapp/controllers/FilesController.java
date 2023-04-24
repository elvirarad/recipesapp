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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
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

    @GetMapping("/export/text")
    @Operation(
            summary = "Загрузка файла рецептов в текстовом формате",
            description = "Скачать файл с рецептами"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Файл успешно загружен",
                    content = {
                            @Content(
                                    mediaType = "application/text-plain",
                                    array = @ArraySchema(schema =
                                    @Schema(implementation = Recipe.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Ошибка параметров запроса",
                    content = {
                            @Content(
                                    mediaType = "application/text-plain",
                                    array = @ArraySchema(schema =
                                    @Schema(implementation = Recipe.class))
                            )
                    }
            ),

            @ApiResponse(
                    responseCode = "404",
                    description = "Неверный URL или нет такой операции в веб-приложении",
                    content = {
                            @Content(
                                    mediaType = "application/text-plain",
                                    array = @ArraySchema(schema =
                                    @Schema(implementation = Recipe.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Внутренняя ошибка сервера во время запроса",
                    content = {
                            @Content(
                                    mediaType = "application/text-plain",
                                    array = @ArraySchema(schema =
                                    @Schema(implementation = Recipe.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "В файле нет содержимого",
                    content = {
                            @Content(
                                    mediaType = "application/text-plain",
                                    array = @ArraySchema(schema =
                                    @Schema(implementation = Recipe.class))
                            )
                    }
            )
    })

    public ResponseEntity<Object> downloadTextDataFile() {
        try {
            Path path = recipesServices.createTextDataFile();
            if (Files.size(path) == 0) {
                return ResponseEntity.noContent().build();
            }
            InputStreamResource resource = new InputStreamResource(new FileInputStream(path.toFile()));
            return ResponseEntity.ok()
                    .contentType(MediaType.TEXT_PLAIN)
                    .contentLength(Files.size(path))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"recipesDataFile.txt\"")
                    .body(resource);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.toString());
        }
    }

}
