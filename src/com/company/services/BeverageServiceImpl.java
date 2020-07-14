package com.company.services;

import com.company.adapters.BeverageAdapter;
import com.company.enums.OrderStatus;
import com.company.exceptions.InsufficientIngredientException;
import com.company.models.Beverage;
import com.company.models.BeverageIngredientComposition;
import com.company.models.Ingredient;
import com.company.models.Order;
import com.company.requests.BeverageCreateRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeverageServiceImpl implements BeverageService {

    private Map<String, Beverage> beverageMap;
    private IngredientService ingredientService;

    public BeverageServiceImpl(IngredientService ingredientService) {
        this.beverageMap = new HashMap<>();
        this.ingredientService = ingredientService;
    }

    @Override
    public Beverage saveBeverageConfiguration(BeverageCreateRequest beverageCreateRequest) {
        Map<String, Integer> ingredientCompositionMap = new HashMap<>();
        Map<String, Integer> ingredientsCompositionMapRequest = beverageCreateRequest.getBeverageIngredientCompositionMap();
        for(String ingredientName : ingredientsCompositionMapRequest.keySet()) {
            // Creating Ingredient if not present in the db already
            Ingredient ingredient = ingredientService.getOrCreateDefault(ingredientName);
            int quantity = ingredientsCompositionMapRequest.get(ingredientName);

            ingredientCompositionMap.put(ingredient.getId(), quantity);
        }

        Beverage beverage = BeverageAdapter.toBeverage(beverageCreateRequest, ingredientCompositionMap);
        beverageMap.put(beverage.getId(), beverage);

        return beverage;
    }

    @Override
    public Beverage getBeverageById(String beverageId) {
        return beverageMap.get(beverageId);
    }

    @Override
    public synchronized Order orderBeverage(String beverageId) throws InsufficientIngredientException {

        // Get the Beverage and required Ingredients details
        Beverage beverage = this.getBeverageById(beverageId);
        List<BeverageIngredientComposition> beverageIngredientCompositionList = beverage.getBeverageIngredientCompositionList();

        List<Ingredient> missingIngredients = new ArrayList<>();

        // Check if all the ingredients are in sufficient quantities
        for(BeverageIngredientComposition beverageIngredientComposition : beverageIngredientCompositionList) {
            String ingredientId = beverageIngredientComposition.getIngredientId();
            int requiredQuantity = beverageIngredientComposition.getQuantity();
            boolean isQuantityAvailable = ingredientService.checkIfIngredientHasRequiredQuantity(ingredientId, requiredQuantity);
            if(!isQuantityAvailable) {
                Ingredient ingredient = ingredientService.getIngredientById(ingredientId);
                missingIngredients.add(ingredient);
            }
        }

        // All ingredients available
        if(missingIngredients.isEmpty()) {
            for(BeverageIngredientComposition beverageIngredientComposition : beverageIngredientCompositionList) {
                String ingredientId = beverageIngredientComposition.getIngredientId();
                int requiredQuantity = beverageIngredientComposition.getQuantity();
                ingredientService.decreaseIngredientQuantity(ingredientId, requiredQuantity);
            }
            Order order = new Order();
            order.setOrderStatus(OrderStatus.SUCCESSFUL);
            order.setResponseMessage(beverage.getName() + " is prepared");
            return order;
        } else {
            // Throwing exception as ingredients are missing
            throw new InsufficientIngredientException("Ingredients Insufficient or Unavailable", missingIngredients);
        }
    }

    /* 2 Methods to go about ordering a beverage:

       Method 1: If we want consistent processing, we can queue each order request as it comes, check for constraints
       and then go on to processing next beverage request. We can utilise DB transaction to check and update
       values for each request. Beverages can be dispersed in parallel but different orders will not engage in race conditions
       and the correctness of the system is ensured.

       Method 2: If we relax the consistency constraint, we can let multiple order requests check and update for constraints at once
       and if a constraint isn't satisfied, we can rollback the changes done by this particular order request thread.
       Potential consistency issues arise when system shuts down after the update but before the rollback happens

       Going forward with Method 1 considering Consistency and correctness of the system is more important
     */

    /*
    @Override
    public synchronized Beverage orderBeverage(String beverageId) throws InsufficientResourceException {

        Beverage beverage = this.getBeverageById(beverageId);

        Map<String, Integer> processedIngredientsCompositionMap = new HashMap<>();
        List<BeverageIngredientComposition> beverageIngredientCompositionList = this.getIngredientCompositionMap(beverageId);

        boolean shouldRollback = false;
        Ingredient missingIngredient = null;

        for(BeverageIngredientComposition beverageIngredientComposition : beverageIngredientCompositionList) {
            // TODO: Multiple threads will access ingredient objects at once. Need to ensure parallel execution works here
            String ingredientId = beverageIngredientComposition.getIngredientId();
            Ingredient ingredient = ingredientService.getIngredientById(ingredientId);

            int requiredQuantity = beverageIngredientComposition.getQuantity();

            if(ingredientService.checkAndUpdateIngredientQuantity(ingredientId, -requiredQuantity)) {
                processedIngredientsCompositionMap.put(ingredientId, -requiredQuantity);
            } else {
                shouldRollback = true;
                missingIngredient = ingredient;
                break;
            }
        }

        if(shouldRollback) {
            for(String ingredientId : processedIngredientsCompositionMap.keySet()) {
                int requiredQuantity = processedIngredientsCompositionMap.get(ingredientId);
                ingredientService.checkAndUpdateIngredientQuantity(ingredientId, -requiredQuantity);
            }
            String errorMessage = beverage.getName() + " cannot be prepared because " + missingIngredient.getName() + " is not ";
            if(missingIngredient.isPresent()) throw new InsufficientResourceException(errorMessage + "sufficient");
            throw new InsufficientResourceException(errorMessage + "available");
        }
        return beverage;
    }
     */

}
