package me.elvira.recipesapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor

public final class Recipe {
    private String name;
    private int cookingTime;
    private List<Ingredient> ingredients;
    private List<String> steps;

}
