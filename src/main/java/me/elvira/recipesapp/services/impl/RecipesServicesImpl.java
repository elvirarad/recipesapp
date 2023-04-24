package me.elvira.recipesapp.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.elvira.recipesapp.dto.RecipeDTO;
import me.elvira.recipesapp.exception.RecipeNotFoundException;
import me.elvira.recipesapp.exception.RecipeValidationException;
import me.elvira.recipesapp.model.Ingredient;
import me.elvira.recipesapp.model.Recipe;
import me.elvira.recipesapp.services.FilesServicesRecipe;
import me.elvira.recipesapp.services.RecipesServices;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@Service
public class RecipesServicesImpl implements RecipesServices {

    private final FilesServicesRecipe filesServicesRecipe;
    private static TreeMap<Integer, Recipe> recipes = new TreeMap<>();
    private final ObjectMapper objectMapper;
    private static int recipeNumber = 0;

    public RecipesServicesImpl(FilesServicesRecipe filesServicesRecipe, ObjectMapper objectMapper) {
        this.filesServicesRecipe = filesServicesRecipe;
        this.objectMapper = objectMapper;
    }


    @PostConstruct
    private void  init(){
        try {
            readFromFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public RecipeDTO addRecipe(Recipe recipe) {
        if (StringUtils.isBlank(recipe.getName())){
            throw new RecipeValidationException();
        }
        int id = recipeNumber++;
        recipes.put(id, recipe);
        saveToFile();
        return RecipeDTO.from(id, recipe);
    }

    @Override
    public RecipeDTO getRecipe(int id) {
        Recipe recipe = recipes.get(id);
        if (recipe == null) {
            throw new RecipeNotFoundException();
        }
        return RecipeDTO.from(id, recipe);
//        StringUtils.isBlank(recipe)
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
        saveToFile();
        return RecipeDTO.from(id, recipe);
        }

    @Override
    public RecipeDTO deleteById(int id){
        Recipe existingRecipe = recipes.remove(id);
        saveToFile();
        if (existingRecipe == null){
            throw new RecipeNotFoundException();
        }
        return RecipeDTO.from(id, existingRecipe);
    }

    @Override
    public void saveToFile(){
        try {
            String json = new ObjectMapper().writeValueAsString(recipes);
            filesServicesRecipe.saveToFile(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void readFromFile(){
        try {
            String json = filesServicesRecipe.readFromFile();
            recipes = new ObjectMapper().readValue(json, new TypeReference<TreeMap<Integer, Recipe>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void expertFileTxt(PrintWriter writer) throws IOException {
//        List<Recipe> recipeList = new ArrayList<>();
//        objectMapper.writeValue(writer, this.recipes.values());
        if (writer == null) {
            throw new RecipeNotFoundException();
        }
        if (StringUtils.isBlank(writer.toString())){
            throw new RecipeValidationException();
        }
        for (Recipe recipe : this.recipes.values()){
            writer.println(recipe.getName());
            writer.println("Время приготовления: %d минут.".formatted(recipe.getCookingTime()));
            writer.println("Ингредиенты:");
            for (Ingredient ingredient : recipe.getIngredients()){
                writer.println("\t%s - %d %s".formatted(ingredient.getName(), ingredient.getQuantity(), ingredient.getUnit()));
            }
            writer.println("Инструкция приготовления:");
            for (int i = 0; i < recipe.getSteps().size(); i++){
                writer.println("%d. %s".formatted(i + 1, recipe.getSteps().get(i)));
            }
            writer.println();
        }
        writer.flush();
    }

}