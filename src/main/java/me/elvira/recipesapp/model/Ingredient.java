package me.elvira.recipesapp.model;

import java.util.Objects;

public final class Ingredient {
    private final String name;
    private final int quantity;
    private final String unit;

    public Ingredient(String name, int quantity, String unit) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getUnit() {
        return unit;
    }

    @Override
    public String toString() {
        return "Ingredient: " + name +
                ", quantity: " + quantity +
                ", unit: " + unit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient that = (Ingredient) o;
        return quantity == that.quantity && Objects.equals(name, that.name) && Objects.equals(unit, that.unit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, quantity, unit);
    }
}
