package me.elvira.recipesapp.controllers;

import me.elvira.recipesapp.dto.RecipeDTO;
import me.elvira.recipesapp.model.Recipe;
import me.elvira.recipesapp.services.RecipesServices;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recipe")

public class RecipeController {
    private final RecipesServices recipesServices;

    public RecipeController(RecipesServices recipesServices) {
        this.recipesServices = recipesServices;
    }

    @GetMapping("/{id}")
    public RecipeDTO getRecipe(@PathVariable("id") int id) {
        return recipesServices.getRecipe(id);
    }

    @PostMapping()
    public RecipeDTO addRecipe(@RequestBody Recipe recipe){
        return recipesServices.addRecipe(recipe);
    }
}
