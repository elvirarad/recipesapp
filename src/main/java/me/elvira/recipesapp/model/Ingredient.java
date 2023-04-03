package me.elvira.recipesapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;
@Data
@AllArgsConstructor
@NoArgsConstructor

public final class Ingredient {
    private String name;
    private int quantity;
    private String unit;

}
