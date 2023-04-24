package me.elvira.recipesapp.services;

import me.elvira.recipesapp.dto.RecipeDTO;
import me.elvira.recipesapp.model.Recipe;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.List;

public interface RecipesServices {

    RecipeDTO addRecipe(Recipe recipe);

    RecipeDTO getRecipe(int recipeNumber);

    List<Recipe> getAllRecipes();

    RecipeDTO updateRecipe(int id, Recipe recipe);

    RecipeDTO deleteById(int id);

    void saveToFile();

    void readFromFile();

    void expertFileTxt(PrintWriter writer) throws IOException;
}
