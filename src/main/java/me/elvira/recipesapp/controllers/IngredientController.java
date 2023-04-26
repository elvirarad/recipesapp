package me.elvira.recipesapp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Ингредиенты", description = "CRUD-операции и другие эндпоинты для работы с инредиентами")

public class IngredientController {
    private final IngredientServices ingredientServices;

    public IngredientController(IngredientServices ingredientServices) {
        this.ingredientServices = ingredientServices;
    }

    @GetMapping
    @Operation(summary = "вывод всех инредиентов")
    public List<Ingredient> getIngredients (){
        return ingredientServices.getAllIngredients();
    }

    @GetMapping("/{id}")
    @Operation(summary = "вывод инредиента по id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "ингредиент найден",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Ingredient.class))
                            )
                    }
            )
    })
    public IngredientDTO getIngredient (@PathVariable("id") int id) {
        return ingredientServices.getIngredient(id);
    }

    @PostMapping()
    @Operation(summary = "добавление инредиента")
    public IngredientDTO addIngredient (@RequestBody Ingredient ingredient){
        return ingredientServices.addIngredient(ingredient);
    }

    @PutMapping("/{id}")
    @Operation(summary = "редактирование инредиента")
    public IngredientDTO editIngredient(@PathVariable("id") int id, @RequestBody Ingredient ingredient){
        return ingredientServices.updateIngredient(id, ingredient);
    }

    @DeleteMapping("/id")
    @Operation(summary = "удаление инредиента по id")
    public IngredientDTO deleteIngredient(@PathVariable("id") int id){
        return ingredientServices.deleteById(id);
    }

}
