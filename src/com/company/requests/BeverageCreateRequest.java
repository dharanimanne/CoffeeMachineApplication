package com.company.requests;

import com.company.models.Beverage;
import com.company.models.BeverageIngredientComposition;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class BeverageCreateRequest {

    private String name;

    private Map<String, Integer> beverageIngredientCompositionMap;

    public BeverageCreateRequest(String name, Map<String, Integer> beverageIngredientCompositionMap) {
        this.name = name;
        this.beverageIngredientCompositionMap = beverageIngredientCompositionMap;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Integer> getBeverageIngredientCompositionMap() {
        return beverageIngredientCompositionMap;
    }

    public void setBeverageIngredientCompositionMap(Map<String, Integer> beverageIngredientCompositionMap) {
        this.beverageIngredientCompositionMap = beverageIngredientCompositionMap;
    }
}
