package me.elvira.recipesapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;
@Data
@AllArgsConstructor

public final class Ingredient {
    private String name;
    private int quantity;
    private String unit;

}
