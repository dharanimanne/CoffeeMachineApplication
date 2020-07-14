package com.company.adapters;

import com.company.models.Beverage;
import com.company.models.BeverageIngredientComposition;
import com.company.requests.BeverageCreateRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BeverageAdapter {

    public static Beverage toBeverage(BeverageCreateRequest beverageCreateRequest, Map<String, Integer> ingredientCompositionMap) {
        Beverage beverage = new Beverage();
        beverage.setName(beverageCreateRequest.getName());

        List<BeverageIngredientComposition> beverageIngredientCompositionList = new ArrayList<>();
        for(String ingredientId : ingredientCompositionMap.keySet()) {
            int quantity = ingredientCompositionMap.get(ingredientId);

            BeverageIngredientComposition beverageIngredientComposition = new BeverageIngredientComposition();
            beverageIngredientComposition.setBeverageId(beverage.getId());
            beverageIngredientComposition.setIngredientId(ingredientId);
            beverageIngredientComposition.setQuantity(quantity);

            beverageIngredientCompositionList.add(beverageIngredientComposition);
        }

        beverage.setBeverageIngredientCompositionList(beverageIngredientCompositionList);

        return beverage;
    }

}
