package com.company.exceptions;

import com.company.models.Ingredient;

import java.util.Collections;
import java.util.List;

public class InsufficientIngredientException extends Exception {

    private List<Ingredient> insufficientIngredients;

    public InsufficientIngredientException(String message, List<Ingredient> insufficientIngredients) {
        super(message);
        this.setInsufficientIngredients(insufficientIngredients);
    }

    public List<Ingredient> getInsufficientIngredients() {
        return insufficientIngredients;
    }

    public void setInsufficientIngredients(List<Ingredient> insufficientIngredients) {
        this.insufficientIngredients = insufficientIngredients;
    }
}
