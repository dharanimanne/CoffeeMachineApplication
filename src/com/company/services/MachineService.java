package com.company.services;

import com.company.models.Machine;
import com.company.models.Order;
import com.company.requests.MachineCreateRequest;

import java.util.Optional;

public interface MachineService {

    Machine createMachine(MachineCreateRequest machineCreateRequest);

    void startMachine();

    void shutdownMachine();

    Optional<Order> orderBeverage(String beverageId);
}
