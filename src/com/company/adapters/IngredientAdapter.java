package com.company.adapters;

import com.company.models.Ingredient;
import com.company.requests.IngredientCreateRequest;

public class IngredientAdapter {

    public static Ingredient toIngredient(IngredientCreateRequest ingredientCreateRequest) {
        Ingredient ingredient = new Ingredient();

        ingredient.setName(ingredientCreateRequest.getName());
        ingredient.setQuantity(ingredientCreateRequest.getQuantity());
        ingredient.setPresent(ingredientCreateRequest.isPresent());

        if(ingredientCreateRequest.getLowQuantityLevel() == null) {
            ingredient.setLowQuantityLevel(ingredient.getDefaultLowQuantity());
        } else ingredient.setLowQuantityLevel(ingredientCreateRequest.getLowQuantityLevel());

        return ingredient;
    }
}
