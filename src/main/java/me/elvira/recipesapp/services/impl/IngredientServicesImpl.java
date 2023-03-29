package me.elvira.recipesapp.services.impl;

import me.elvira.recipesapp.dto.IngredientDTO;
import me.elvira.recipesapp.model.Ingredient;
import me.elvira.recipesapp.services.IngredientServices;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IngredientServicesImpl implements IngredientServices {

    private final Map<Integer, Ingredient> ingredients = new HashMap<>();
    private static int Number = 0;

    @Override
    public IngredientDTO addIngredient(Ingredient ingredient) {
        int id = Number++;
        ingredients.put(id, ingredient);
        return IngredientDTO.from(id, ingredient);
    }

    @Override
    public IngredientDTO getIngredient(int id) {
        Ingredient ingredient = ingredients.get(id);
        if (ingredient != null) {
            return IngredientDTO.from(id, ingredient);
        }
        return null;
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
        return IngredientDTO.from(id, ingredient);
    }

    @Override
    public IngredientDTO deleteById(int id){
        Ingredient existingIngredient = ingredients.remove(id);
        if (existingIngredient == null){
            throw new IngredientNotFoundException();
        }
        return IngredientDTO.from(id, existingIngredient);
    }

}
