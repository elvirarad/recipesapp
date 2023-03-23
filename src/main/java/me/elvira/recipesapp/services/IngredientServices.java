package me.elvira.recipesapp.services;

import me.elvira.recipesapp.dto.IngredientDTO;
import me.elvira.recipesapp.model.Ingredient;

public interface IngredientServices {
    IngredientDTO addIngredient(Ingredient ingredient);
    IngredientDTO getIngredient(int id);
}
