package me.elvira.recipesapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.elvira.recipesapp.model.Ingredient;

@Data
@AllArgsConstructor

public class IngredientDTO {
    private final int id;
    private final String name;
    private final int quantity;
    private final String unit;

    public static IngredientDTO from(int id, Ingredient ingredient){
        return new IngredientDTO(id, ingredient.getName(),
                ingredient.getQuantity(), ingredient.getUnit());
    } // статическая фабрика
}
