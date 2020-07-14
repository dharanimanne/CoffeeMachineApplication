package com.company.models;

import java.util.List;
import java.util.UUID;

public class Beverage {

    private String id;

    private String name;

    private List<BeverageIngredientComposition> beverageIngredientCompositionList;

    public Beverage(){
        this.setId(UUID.randomUUID().toString());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BeverageIngredientComposition> getBeverageIngredientCompositionList() {
        return beverageIngredientCompositionList;
    }

    public void setBeverageIngredientCompositionList(List<BeverageIngredientComposition> beverageIngredientCompositionList) {
        this.beverageIngredientCompositionList = beverageIngredientCompositionList;
    }
}
