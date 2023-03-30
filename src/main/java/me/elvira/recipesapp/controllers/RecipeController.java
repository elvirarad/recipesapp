package me.elvira.recipesapp.controllers;

import me.elvira.recipesapp.dto.RecipeDTO;
import me.elvira.recipesapp.model.Recipe;
import me.elvira.recipesapp.services.RecipesServices;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.awt.*;

@RestController
@RequestMapping("/recipe")

public class RecipeController {
    private final RecipesServices recipesServices;

    public RecipeController(RecipesServices recipesServices) {
        this.recipesServices = recipesServices;
    }

    @GetMapping
    public List<Recipe> getRecipes(){
        return recipesServices.getAllRecipes();
    }

    @GetMapping("/{id}")
    public RecipeDTO getRecipe(@PathVariable("id") int id) {
        return recipesServices.getRecipe(id);
    }

    @PostMapping()
    public RecipeDTO addRecipe(@RequestBody Recipe recipe){
        return recipesServices.addRecipe(recipe);
    }

    @PutMapping("/{id}")
    public RecipeDTO editRecipe(@PathVariable("id") int id, @RequestBody Recipe recipe)
    {
        return recipesServices.updateRecipe(id, recipe);
    }

    @DeleteMapping("/id")
    public RecipeDTO deleteRecipe(@PathVariable("id") int id){
        return recipesServices.deleteById(id);
    }
}
