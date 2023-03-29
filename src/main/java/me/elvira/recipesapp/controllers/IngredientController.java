package me.elvira.recipesapp.controllers;

import me.elvira.recipesapp.dto.IngredientDTO;
import me.elvira.recipesapp.dto.RecipeDTO;
import me.elvira.recipesapp.model.Ingredient;
import me.elvira.recipesapp.model.Recipe;
import me.elvira.recipesapp.services.IngredientServices;
import me.elvira.recipesapp.services.RecipesServices;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ingredient")

public class IngredientController {
    private final IngredientServices ingredientServices;

    public IngredientController(IngredientServices ingredientServices) {
        this.ingredientServices = ingredientServices;
    }

    @GetMapping
    public List<Ingredient> getIngredients (){
        return ingredientServices.getAllIngredients();
    }

    @GetMapping("/{id}")
    public IngredientDTO getIngredient (@PathVariable("id") int id) {
        return ingredientServices.getIngredient(id);
    }

    @PostMapping()
    public IngredientDTO addIngredient (@RequestBody Ingredient ingredient){
        return ingredientServices.addIngredient(ingredient);
    }

    @PutMapping("/{id}")
    public IngredientDTO editIngredient(@PathVariable("id") int id, @RequestBody Ingredient ingredient){
        return ingredientServices.updateIngredient(id, ingredient);
    }

    @DeleteMapping("/id")
    public IngredientDTO deleteIngredient(@PathVariable("id") int id){
        return ingredientServices.deleteById(id);
    }

}
