package me.elvira.recipesapp.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.elvira.recipesapp.dto.RecipeDTO;
import me.elvira.recipesapp.exception.RecipeNotFoundException;
import me.elvira.recipesapp.exception.RecipeValidationException;
import me.elvira.recipesapp.model.Recipe;
import me.elvira.recipesapp.services.FilesServicesRecipe;
import me.elvira.recipesapp.services.RecipesServices;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;

@Service
public class RecipesServicesImpl implements RecipesServices {

    private final FilesServicesRecipe filesServicesRecipe;
    private static TreeMap<Integer, Recipe> recipes = new TreeMap<>();
    private static int recipeNumber = 0;

    public RecipesServicesImpl(FilesServicesRecipe filesServicesRecipe) {
        this.filesServicesRecipe = filesServicesRecipe;
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
    public Path createTextDataFile() throws IOException {
        Path path = filesServicesRecipe.createTempFile("recipesDataFile");
        for (Recipe recipe : recipes.values()) {
            try (Writer writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
                writer.append(recipe.getName()).append("\n \n").append("Время приготовления: ").append(String.valueOf(recipe.getCookingTime())).append(" minutes.").append("\n");
                writer.append("\n");
                writer.append("Ингредиенты: \n \n");
                recipe.getIngredients().forEach(ingredient -> {
                    try {
                        writer.append(" • ").append(ingredient.getName()).append(" • ").append(String.valueOf(ingredient.getQuantity())).append(" ").append(ingredient.getUnit()).append("\n \n");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
                writer.append("\n");
                writer.append("Инструкция приготовления: \n \n");
                recipe.getSteps().forEach(step -> {
                    try {
                        writer.append(" > ").append(step).append("\n \n");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
                writer.append("\n \n");
            }
        }
        return path;
    }

    @Override
    public ResponseEntity<Object> downloadTextDataFile() {
        try {
            Path path = createTextDataFile();
            if (Files.size(path) == 0) {
                return ResponseEntity.noContent().build();
            }
            InputStreamResource resource = new InputStreamResource(new FileInputStream(path.toFile()));
            return ResponseEntity.ok()
                    .contentType(MediaType.TEXT_PLAIN)
                    .contentLength(Files.size(path))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"recipesDataFile.txt\"")
                    .body(resource);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.toString());
        }
    }

}