package me.elvira.recipesapp.services;

import me.elvira.recipesapp.dto.RecipeDTO;
import me.elvira.recipesapp.model.Recipe;

import java.util.List;

public interface RecipesServices {

    RecipeDTO addRecipe(Recipe recipe);

    RecipeDTO getRecipe(int recipeNumber);

    List<Recipe> getAllRecipes();

    RecipeDTO updateRecipe(int id, Recipe recipe);

    RecipeDTO deleteById(int id);

//    Recipe editRecipe(int id, Recipe recipe);
}
