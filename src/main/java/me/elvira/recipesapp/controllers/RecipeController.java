package me.elvira.recipesapp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.elvira.recipesapp.dto.RecipeDTO;
import me.elvira.recipesapp.model.Recipe;
import me.elvira.recipesapp.services.RecipesServices;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.awt.*;

@RestController
@RequestMapping("/recipe")
@Tag(name = "Рецепты", description = "CRUD-операции и другие эндпоинты для работы с рецептами")

public class RecipeController {

    private final RecipesServices recipesServices;

    public RecipeController(RecipesServices recipesServices) {
        this.recipesServices = recipesServices;
    }

    @GetMapping
    @Operation(summary = "вывод всех рецептов")
    public List<Recipe> getRecipes(){
        return recipesServices.getAllRecipes();
    }

    @GetMapping("/{id}")
    @Operation(summary = "вывод рецепта по id")
    public RecipeDTO getRecipe(@PathVariable("id") int id) {
        return recipesServices.getRecipe(id);
    }

    @PostMapping()
    @Operation(summary = "добавление рецепта")
    public RecipeDTO addRecipe(@RequestBody Recipe recipe){
        return recipesServices.addRecipe(recipe);
    }

    @PutMapping("/{id}")
    @Operation(summary = "редактирование рецепта")
    public RecipeDTO editRecipe(@PathVariable("id") int id, @RequestBody Recipe recipe)
    {
        return recipesServices.updateRecipe(id, recipe);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "удаление рецепта по id")
    public RecipeDTO deleteRecipe(@PathVariable("id") int id){
        return recipesServices.deleteById(id);
    }

}
