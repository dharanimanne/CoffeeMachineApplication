package com.company.services;

import com.company.adapters.IngredientAdapter;
import com.company.models.Ingredient;
import com.company.requests.IngredientCreateRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IngredientServiceImpl implements IngredientService {

    private Map<String, Ingredient> ingredientMap;
    private Map<String, Ingredient> ingredientNameMap;

    public IngredientServiceImpl() {
        ingredientMap = new HashMap<>();
        ingredientNameMap = new HashMap<>();
    }

    @Override
    public Ingredient createIngredient(IngredientCreateRequest ingredientCreateRequest) {
        Ingredient ingredient = IngredientAdapter.toIngredient(ingredientCreateRequest);

        ingredientMap.put(ingredient.getId(), ingredient);
        ingredientNameMap.put(ingredient.getName(), ingredient);

        return ingredient;
    }

    @Override
    public Ingredient getIngredientById(String ingredientId) {
        return ingredientMap.get(ingredientId);
    }

    @Override
    public Ingredient getOrCreateDefault(String ingredientName) {
        if(ingredientNameMap.containsKey(ingredientName)) {
            return getIngredientByName(ingredientName);
        } else {
            IngredientCreateRequest ingredientCreateRequest = new IngredientCreateRequest(ingredientName, 0, false);
            return createIngredient(ingredientCreateRequest);
        }
    }

    // Assuming that we can add Ingredients which aren't initially present in the system
    @Override
    public boolean addQuantityToIngredient(String ingredientId, int quantity) {
        Ingredient ingredient = ingredientMap.get(ingredientId);
        ingredient.addQuantity(quantity);
        if(!ingredient.isPresent()) ingredient.setPresent(true);
        return true;
    }

    @Override
    public boolean decreaseIngredientQuantity(String ingredientId, int decrementQuantity) {
        Ingredient ingredient = ingredientMap.get(ingredientId);
        return ingredient.decreaseQuantity(decrementQuantity);
    }

    @Override
    public boolean checkIfIngredientHasRequiredQuantity(String ingredientId, int requiredQuantity) {
        Ingredient ingredient = ingredientMap.get(ingredientId);

        return ingredient.getQuantity() - requiredQuantity >= 0;
    }

    @Override
    public List<Ingredient> getAllIngredientsWithLowQuantities() {

        List<Ingredient> lowQuantityIngredients = new ArrayList<Ingredient>();

        for(Ingredient ingredient : ingredientMap.values()) {
            if(checkIfIngredientQuantityIsLow(ingredient.getId()) && ingredient.isPresent()) {
                lowQuantityIngredients.add(ingredient);
            }
        }

        return lowQuantityIngredients;
    }

    private Ingredient getIngredientByName(String ingredientName) {
        return ingredientNameMap.get(ingredientName);
    }

    @Override
    public synchronized boolean checkAndUpdateIngredientQuantity(String ingredientId, int delta) {
        Ingredient ingredient = ingredientMap.get(ingredientId);

        if(ingredient.getQuantity() + delta < 0) {
            return false;
        } else {
            ingredient.setPresent(true);
            ingredient.setQuantity(ingredient.getQuantity() + delta);
            return true;
        }
    }

    private boolean checkIfIngredientQuantityIsLow(String ingredientId) {
        Ingredient ingredient = ingredientMap.get(ingredientId);

        return ingredient.isLowOnQuantity();
    }


}
