package me.elvira.recipesapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.elvira.recipesapp.model.Ingredient;
import me.elvira.recipesapp.model.Recipe;

import java.util.List;
@Data
@AllArgsConstructor

public class RecipeDTO {
    private final int id;
    private final String name;
    private final int cookingTime;
    private final List<Ingredient> ingredients;
    private final List<String> steps;

    public static RecipeDTO from(int id, Recipe recipe){
        return new RecipeDTO(id, recipe.getName(), recipe.getCookingTime(),
                recipe.getIngredients(), recipe.getSteps());
    } // статическая фабрика

}
