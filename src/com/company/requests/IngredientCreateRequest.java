package com.company.requests;

import com.company.models.Ingredient;

import java.util.UUID;

public class IngredientCreateRequest {

    private String name;

    private int quantity;

    private boolean isPresent;

    private Integer lowQuantityLevel;

    public IngredientCreateRequest(String name, int quantity, boolean isPresent, Integer lowQuantityLevel) {
        this.name = name;
        this.quantity = quantity;
        this.isPresent = isPresent;
        this.lowQuantityLevel = lowQuantityLevel;
    }

    public IngredientCreateRequest(String name, int quantity) {
        this(name, quantity, true);
    }

    public IngredientCreateRequest(String name, int quantity, int lowQuantityLevel) {
        this(name, quantity, true, lowQuantityLevel);
    }

    public IngredientCreateRequest(String name, int quantity, boolean isPresent) {
        this(name, quantity, isPresent, null);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isPresent() {
        return isPresent;
    }

    public void setPresent(boolean present) {
        isPresent = present;
    }

    public Integer getLowQuantityLevel() {
        return lowQuantityLevel;
    }

    public void setLowQuantityLevel(Integer lowQuantityLevel) {
        this.lowQuantityLevel = lowQuantityLevel;
    }
}
