package com.company.adapters;

import com.company.models.Machine;
import com.company.requests.MachineCreateRequest;

public class MachineAdapter {

    public static Machine toMachine(MachineCreateRequest machineCreateRequest) {

        Machine machine = new Machine();
        machine.setName(machineCreateRequest.getName());
        machine.setNumberOfSlots(machineCreateRequest.getNumberOfSlots());

        return machine;
    }
}
