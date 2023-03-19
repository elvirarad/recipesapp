package me.elvira.recipesapp.model;

import java.util.Objects;

public final class Recipe {
    private final int id;
    private final String name;
    private final int cookingTime;
    private final String[] steps;

    public Recipe(int id,
                  String name,
                  int cookingTime,
                  String[] steps) {
        //if (id > 0) {
        this.id = id;
        this.name = name;
        this.cookingTime = cookingTime;
        this.steps = steps;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCookingTime() {
        return cookingTime;
    }

    public String[] getSteps() {
        return steps;
    }

    @Override
    public String toString() {
        return "Recipe: " +
                "№ " + id +
                ", name: " + name +
                ", cookingTime = " + cookingTime +
                ", steps: " + steps;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return id == recipe.id && Objects.equals(name, recipe.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


}
