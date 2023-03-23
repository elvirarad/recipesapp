package me.elvira.recipesapp.services;

import me.elvira.recipesapp.dto.RecipeDTO;
import me.elvira.recipesapp.model.Recipe;

public interface RecipesServices {

    RecipeDTO addRecipe(Recipe recipe);

    RecipeDTO getRecipe(int recipeNumber);

}
