package me.elvira.recipesapp.services.impl;

import me.elvira.recipesapp.dto.RecipeDTO;
import me.elvira.recipesapp.exception.RecipeNotFoundException;
import me.elvira.recipesapp.model.Recipe;
import me.elvira.recipesapp.services.RecipesServices;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RecipesServicesImpl implements RecipesServices {
    private final Map<Integer, Recipe> recipes = new HashMap<>();
    private static int recipeNumber = 0;

    @Override
    public RecipeDTO addRecipe(Recipe recipe) {
        int id = recipeNumber++;
        recipes.put(id, recipe);
        return RecipeDTO.from(id, recipe);
    }

    @Override
    public RecipeDTO getRecipe(int id) {
        Recipe recipe = recipes.get(id);
        if (recipe != null) {
            return RecipeDTO.from(id, recipe);
        }
        return null;
    }

    @Override
    public List<Recipe> getAllRecipes(){

//        List<RecipeDTO> allRecipes = new ArrayList<>();
//          for (Map.Entry<Integer, Recipe> entry : recipes.entrySet()){
//              allRecipes.add(RecipeDTO.from(entry.getKey(), entry.getValue()));
//          }
//        return allRecipes;
        return new ArrayList<>(recipes.values());
    }

    @Override
    public RecipeDTO updateRecipe(int id, Recipe recipe){
        Recipe existingRecipe = recipes.get(id);
        if (existingRecipe == null){
            throw new RecipeNotFoundException();
        }
        recipes.put(id, recipe);
        return RecipeDTO.from(id, recipe);
        }

//@Override
//    public RecipeDTO editRecipe(int id, Recipe recipe){
////        for (Recipe value : recipes.values()) {
////        if (recipes.containsKey(id)){
//            recipes.put(id, recipe);
//            return RecipeDTO.from(id, recipe);
//        }
////    }return null;

    @Override
    public RecipeDTO deleteById(int id){
        Recipe existingRecipe = recipes.remove(id);
        if (existingRecipe == null){
            throw new RecipeNotFoundException();
        }
        return RecipeDTO.from(id, existingRecipe);
    }
}

