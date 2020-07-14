package com.company.requests;

import java.util.UUID;

public class MachineCreateRequest {

    private String name;

    private int numberOfSlots;

    public MachineCreateRequest(String name, int numberOfSlots) {
        this.name = name;
        this.numberOfSlots = numberOfSlots;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfSlots() {
        return numberOfSlots;
    }

    public void setNumberOfSlots(int numberOfSlots) {
        this.numberOfSlots = numberOfSlots;
    }
}
