package com.company;

import com.company.models.Beverage;
import com.company.models.Ingredient;
import com.company.models.Order;
import com.company.requests.BeverageCreateRequest;
import com.company.requests.IngredientCreateRequest;
import com.company.services.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
	// write your code here

        int numberOfSlots = 10;

        IngredientService ingredientService = new IngredientServiceImpl();
        BeverageService beverageService = new BeverageServiceImpl(ingredientService);
        MachineService machineService = new MachineServiceImpl(ingredientService, beverageService, numberOfSlots);
        machineService.startMachine();

        // Create Ingredients
        Map<String, String> ingredientsNameIndex = fillIngredients(ingredientService);

        // Create Beverages
        Beverage hotTea = createHotTeaBeverage(beverageService);
        Beverage hotCoffee = createHotCoffeeBeverage(beverageService);
        Beverage blackTea = createBlackTeaBeverage(beverageService);
        Beverage greenTea = createGreenTeaBeverage(beverageService);

        // Order Beverages
        orderBeverageTestCase(hotTea.getId(), machineService);
        orderBeverageTestCase(hotCoffee.getId(), machineService);
        orderBeverageTestCase(blackTea.getId(), machineService);
        orderBeverageTestCase(greenTea.getId(), machineService);

        // Check Ingredient with Low Quantities
        checkIngredientsWithLowQuantities(ingredientService);

        //fill hot water and order black tea beverage
        ingredientService.addQuantityToIngredient(ingredientsNameIndex.get("hot_water"), 100);

        orderBeverageTestCase(blackTea.getId(), machineService);

        // Check Ingredients with Low Quantities
        checkIngredientsWithLowQuantities(ingredientService);

        machineService.shutdownMachine();
    }

    public static Map<String, String> fillIngredients(IngredientService ingredientService) {

        Map<String, String> result = new HashMap<>();

        IngredientCreateRequest ingredientCreateRequest1 = new IngredientCreateRequest("hot_water", 500);
        IngredientCreateRequest ingredientCreateRequest2 = new IngredientCreateRequest("hot_milk", 500);
        IngredientCreateRequest ingredientCreateRequest3 = new IngredientCreateRequest("ginger_syrup", 500);
        IngredientCreateRequest ingredientCreateRequest4 = new IngredientCreateRequest("sugar_syrup", 500);
        IngredientCreateRequest ingredientCreateRequest5 = new IngredientCreateRequest("tea_leaves_syrup", 500);

        Ingredient ingredient1 = ingredientService.createIngredient(ingredientCreateRequest1);
        Ingredient ingredient2 = ingredientService.createIngredient(ingredientCreateRequest2);
        Ingredient ingredient3 = ingredientService.createIngredient(ingredientCreateRequest3);
        Ingredient ingredient4 = ingredientService.createIngredient(ingredientCreateRequest4);
        Ingredient ingredient5 = ingredientService.createIngredient(ingredientCreateRequest5);

        result.put(ingredient1.getName(), ingredient1.getId());
        result.put(ingredient2.getName(), ingredient2.getId());
        result.put(ingredient3.getName(), ingredient3.getId());
        result.put(ingredient4.getName(), ingredient4.getId());
        result.put(ingredient5.getName(), ingredient5.getId());

        return result;
    }

    public static Beverage createHotTeaBeverage(BeverageService beverageService) {
        Map<String, Integer> beverageIngredientsMap = new HashMap<>();
        beverageIngredientsMap.put("hot_water", 200);
        beverageIngredientsMap.put("hot_milk", 100);
        beverageIngredientsMap.put("ginger_syrup", 10);
        beverageIngredientsMap.put("sugar_syrup", 10);
        beverageIngredientsMap.put("tea_leaves_syrup", 30);
        BeverageCreateRequest beverageCreateRequest = new BeverageCreateRequest("hot_tea", beverageIngredientsMap);
        Beverage beverage = beverageService.saveBeverageConfiguration(beverageCreateRequest);

        return beverage;
    }

    public static void orderBeverageTestCase(String beverageId, MachineService machineService) {
        String responseMessage = machineService.orderBeverage(beverageId).map(Order::getResponseMessage).orElse("UNSUCCESSFUL");
        System.out.println(responseMessage);
    }

    public static Beverage createHotCoffeeBeverage(BeverageService beverageService) {

        Map<String, Integer> beverageIngredientsMap = new HashMap<>();
        beverageIngredientsMap.put("hot_water", 100);
        beverageIngredientsMap.put("ginger_syrup", 30);
        beverageIngredientsMap.put("hot_milk", 400);
        beverageIngredientsMap.put("sugar_syrup", 50);
        beverageIngredientsMap.put("tea_leaves_syrup", 30);
        BeverageCreateRequest beverageCreateRequest = new BeverageCreateRequest("hot_coffee", beverageIngredientsMap);
        Beverage beverage = beverageService.saveBeverageConfiguration(beverageCreateRequest);

        return beverage;
    }

    public static Beverage createBlackTeaBeverage(BeverageService beverageService) {

        Map<String, Integer> beverageIngredientsMap = new HashMap<>();
        beverageIngredientsMap.put("hot_water", 300);
        beverageIngredientsMap.put("ginger_syrup", 30);
        beverageIngredientsMap.put("sugar_syrup", 50);
        beverageIngredientsMap.put("tea_leaves_syrup", 30);
        BeverageCreateRequest beverageCreateRequest = new BeverageCreateRequest("black_tea", beverageIngredientsMap);
        Beverage beverage = beverageService.saveBeverageConfiguration(beverageCreateRequest);

        return beverage;
    }

    public static Beverage createGreenTeaBeverage(BeverageService beverageService) {
        Map<String, Integer> beverageIngredientsMap = new HashMap<>();
        beverageIngredientsMap.put("hot_water", 100);
        beverageIngredientsMap.put("ginger_syrup", 30);
        beverageIngredientsMap.put("sugar_syrup", 50);
        beverageIngredientsMap.put("green_mixture", 30);
        BeverageCreateRequest beverageCreateRequest = new BeverageCreateRequest("green_tea", beverageIngredientsMap);
        Beverage beverage = beverageService.saveBeverageConfiguration(beverageCreateRequest);

        return beverage;
    }

    public static void checkIngredientsWithLowQuantities(IngredientService ingredientService) {
        List<Ingredient> lowQuantityIngredients = ingredientService.getAllIngredientsWithLowQuantities();

        for(Ingredient ingredient : lowQuantityIngredients) {
            System.out.println(ingredient.getName() + " is low on quantity. Available Quantity: " + ingredient.getQuantity());
        }
    }
}
