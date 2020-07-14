package com.company.models;

import java.util.UUID;

public class Ingredient {

    private static final int DEFAULT_LOW_QUANTITY = 100;

    private String id;

    private String name;

    private int quantity;

    private int lowQuantityLevel;

    private boolean isPresent;

    public Ingredient() {
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getLowQuantityLevel() {
        return lowQuantityLevel;
    }

    public void setLowQuantityLevel(int lowQuantityLevel) {
        this.lowQuantityLevel = lowQuantityLevel;
    }

    public boolean isLowOnQuantity() {
        return this.getQuantity() <= this.getLowQuantityLevel();
    }

    public boolean isPresent() {
        return isPresent;
    }

    public void setPresent(boolean present) {
        isPresent = present;
    }

    public static int getDefaultLowQuantity() {
        return DEFAULT_LOW_QUANTITY;
    }

    public boolean decreaseQuantity(int decrementQuantity) {
        this.setQuantity(this.getQuantity()-decrementQuantity);
        return true;
    }

    public boolean addQuantity(int addQuantity) {
        this.setQuantity(this.getQuantity()+addQuantity);
        return true;
    }
}
