package me.elvira.recipesapp.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.elvira.recipesapp.dto.IngredientDTO;
import me.elvira.recipesapp.exception.IngredientNotFoundException;
import me.elvira.recipesapp.exception.IngredientValidationException;
import me.elvira.recipesapp.model.Ingredient;
import me.elvira.recipesapp.model.Recipe;
import me.elvira.recipesapp.services.FilesServicesIngredient;
import me.elvira.recipesapp.services.IngredientServices;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class IngredientServicesImpl implements IngredientServices {

    private final FilesServicesIngredient filesServicesIngredient;

    private static Map<Integer, Ingredient> ingredients = new TreeMap<>();
    private static int number = 0;

    public IngredientServicesImpl(FilesServicesIngredient filesServicesIngredient) {
        this.filesServicesIngredient = filesServicesIngredient;
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
    public IngredientDTO addIngredient(Ingredient ingredient) {
        if (StringUtils.isBlank(ingredient.getName())){
            throw new IngredientValidationException();
        }
        int id = number++;
        ingredients.put(id, ingredient);
        saveToFile();
        return IngredientDTO.from(id, ingredient);
    }


    @Override
    public IngredientDTO getIngredient(int id) {
        Ingredient ingredient = ingredients.get(id);
        if (ingredient == null) {
            throw new IngredientNotFoundException();
        }
        return IngredientDTO.from(id, ingredient);
    }

    @Override
    public List<Ingredient> getAllIngredients(){

//        List<IngredientDTO> allIngredients = new ArrayList<>();
//          for (Map.Entry<Integer, Ingredient> entry : ingredients.entrySet()){
//              allIngredients.add(IngredientDTO.from(entry.getKey(), entry.getValue()));
//          }
//        return allIngredients;
        return new ArrayList<>(ingredients.values());
    }
    @Override
    public IngredientDTO updateIngredient(int id, Ingredient ingredient){
        Ingredient existingIngredient = ingredients.get(id);
        if (existingIngredient == null){
            throw new IngredientNotFoundException();
        }
        ingredients.put(id, ingredient);
        saveToFile();
        return IngredientDTO.from(id, ingredient);
    }

    @Override
    public IngredientDTO deleteById(int id){
        Ingredient existingIngredient = ingredients.remove(id);
        saveToFile();
        if (existingIngredient == null){
            throw new IngredientNotFoundException();
        }
        return IngredientDTO.from(id, existingIngredient);
    }

    @Override
    public void saveToFile(){
        try {
            String json = new ObjectMapper().writeValueAsString(ingredients);
            filesServicesIngredient.saveToFile(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void readFromFile(){
        try {
            String json = filesServicesIngredient.readFromFile();
            ingredients = new ObjectMapper().readValue(json, new TypeReference<TreeMap<Integer, Ingredient>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
