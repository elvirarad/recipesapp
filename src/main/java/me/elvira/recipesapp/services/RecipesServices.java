package me.elvira.recipesapp.services;

import me.elvira.recipesapp.dto.RecipeDTO;
import me.elvira.recipesapp.model.Recipe;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
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

    Path createTextDataFile() throws IOException;

    ResponseEntity<Object> downloadTextDataFile();


//    Recipe editRecipe(int id, Recipe recipe);
}
