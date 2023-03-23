package me.elvira.recipesapp.services.impl;

import me.elvira.recipesapp.dto.IngredientDTO;
import me.elvira.recipesapp.dto.RecipeDTO;
import me.elvira.recipesapp.model.Ingredient;
import me.elvira.recipesapp.model.Recipe;
import me.elvira.recipesapp.services.IngredientServices;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class IngredientServicesImpl implements IngredientServices {

    private final Map<Integer, Ingredient> ingredients = new HashMap<>();
    private static int Number = 0;

    @Override
    public IngredientDTO addIngredient(Ingredient ingredient) {
        int id = Number++;
        ingredients.put(id, ingredient);
        return IngredientDTO.from(id, ingredient);
    }

    @Override
    public IngredientDTO getIngredient(int id) {
        Ingredient ingredient = ingredients.get(id);
        if (ingredient != null) {
            return IngredientDTO.from(id, ingredient);
        }
        return null;
    }
}
