package com.company.services;

import com.company.models.Ingredient;
import com.company.requests.IngredientCreateRequest;

import java.util.List;

public interface IngredientService {

    Ingredient createIngredient(IngredientCreateRequest ingredientCreateRequest);

    Ingredient getIngredientById(String ingredientId);

    Ingredient getOrCreateDefault(String ingredientName);

    boolean addQuantityToIngredient(String ingredientId, int quantity);

    boolean decreaseIngredientQuantity(String ingredientId, int decrementQuantity);

    boolean checkIfIngredientHasRequiredQuantity(String ingredientId, int requiredQuantity);

    List<Ingredient> getAllIngredientsWithLowQuantities();

    // Unused Method: Used in Approach 2 of ordering beverage
    boolean checkAndUpdateIngredientQuantity(String ingredientId, int changeInQuantity);

}
