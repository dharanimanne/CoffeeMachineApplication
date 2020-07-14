package com.company.services;

import com.company.exceptions.InsufficientIngredientException;
import com.company.models.Beverage;
import com.company.models.Order;
import com.company.requests.BeverageCreateRequest;

public interface BeverageService {

    Beverage saveBeverageConfiguration(BeverageCreateRequest beverageCreateRequest);

    Beverage getBeverageById(String beverageId);

    Order orderBeverage(String beverageId) throws InsufficientIngredientException;

}
