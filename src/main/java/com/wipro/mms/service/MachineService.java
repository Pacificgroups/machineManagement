package com.wipro.mms.service;

import com.wipro.mms.model.Machine;
import com.wipro.mms.payload.MachineDto;

import java.util.List;

public interface MachineService {

    List<MachineDto> getAllMachines();

    Machine getMachineById(Long machineId);

    Machine updateMachineById(Machine machine,Long machineId , String username);

    void deleteMachineById(Long machineId , String username);

    Machine addMachine(Machine machine, String username);


}
