package me.elvira.recipesapp.services;

import me.elvira.recipesapp.dto.IngredientDTO;
import me.elvira.recipesapp.model.Ingredient;

import java.util.List;

public interface IngredientServices {
    IngredientDTO addIngredient(Ingredient ingredient);
    IngredientDTO getIngredient(int id);

    List<Ingredient> getAllIngredients();

    IngredientDTO updateIngredient(int id, Ingredient ingredient);

    IngredientDTO deleteById(int id);
}
