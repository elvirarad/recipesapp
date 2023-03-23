package me.elvira.recipesapp.services.impl;

import me.elvira.recipesapp.dto.RecipeDTO;
import me.elvira.recipesapp.model.Recipe;
import me.elvira.recipesapp.services.RecipesServices;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RecipesServicesImpl implements RecipesServices {
    private final Map<Integer, Recipe> recipes = new HashMap<>();
    private static int recipeNumber = 0;

    @Override
    public RecipeDTO addRecipe(Recipe recipe) {
        int id = recipeNumber++;
        recipes.put(id, recipe);
        return RecipeDTO.from(id, recipe);
    }

    @Override
    public RecipeDTO getRecipe(int id) {
        Recipe recipe = recipes.get(id);
        if (recipe != null) {
            return RecipeDTO.from(id, recipe);
        }
        return null;
    }
}
