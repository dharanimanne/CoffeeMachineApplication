package com.company.services;

import com.company.adapters.MachineAdapter;
import com.company.enums.OrderStatus;
import com.company.exceptions.InsufficientIngredientException;
import com.company.models.*;
import com.company.requests.MachineCreateRequest;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MachineServiceImpl implements MachineService {

    private IngredientService ingredientService;

    private BeverageService beverageService;

    private ExecutorService executorService;

    private int numberOfSlots;

    public MachineServiceImpl(IngredientService ingredientService, BeverageService beverageService, int numberOfSlots) {
        this.ingredientService = ingredientService;
        this.beverageService = beverageService;
        this.numberOfSlots = numberOfSlots;
    }

    @Override
    public Machine createMachine(MachineCreateRequest machineCreateRequest) {
        Machine machine = MachineAdapter.toMachine(machineCreateRequest);
        return machine;
    }

    @Override
    public void startMachine() {
        executorService = Executors.newFixedThreadPool(numberOfSlots);
    }

    @Override
    public void shutdownMachine() {
        executorService.shutdown();
    }


    /**
     * Submit the order request to the queue of tasks
     * Available threads (from the pool of n threads) will take up the order and process the order
     *
     * @param beverageId
     * @return Order with success or failed status
     */
    public Optional<Order> orderBeverage(String beverageId) {
        Future<Order> submit = executorService.submit(new Callable<Order>() {
            @Override
            public Order call() {
                try {
                    Order order = beverageService.orderBeverage(beverageId);
                    return order;
                } catch (InsufficientIngredientException insufficientResourceException) {
                    Order failedOrder = createFailedOrderResponse(beverageId, insufficientResourceException.getInsufficientIngredients());
                    return failedOrder;
                }
            }
        });

        try {
            Order order = submit.get();
            return Optional.of(order);
        } catch (Exception exception) {
            // TODO: Log the exception
            return Optional.empty();
        }
    }

    private Order createFailedOrderResponse(String beverageId, List<Ingredient> missingIngredients) {
        Order order = new Order();
        order.setOrderStatus(OrderStatus.FAILED);

        StringBuilder responseMessage = new StringBuilder();
        responseMessage.append(beverageService.getBeverageById(beverageId).getName() + " is not prepared because ");

        for(Ingredient ingredient : missingIngredients) {
            if(ingredient.isPresent()) {
                responseMessage.append(ingredient.getName() + " is insufficient; ");
            } else {
                responseMessage.append(ingredient.getName() + " is unavailable; ");
            }
        }

        order.setResponseMessage(responseMessage.toString());

        return order;
    }

}