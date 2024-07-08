package com.wipro.mms.service.impl;

import com.wipro.mms.exception.ResourceNotFoundException;
import com.wipro.mms.model.Machine;
import com.wipro.mms.payload.MachineDto;
import com.wipro.mms.repository.MachineRepository;
import com.wipro.mms.service.AuditLogService;
import com.wipro.mms.service.MachineService;
import com.wipro.mms.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MachineServiceImpl implements MachineService {

    @Autowired
    private MachineRepository machineRepository;



    @Autowired
    private AuditLogService auditLogService;

    @Override
    public List<MachineDto> getAllMachines() {
        List<Machine> machines=this.machineRepository.findAll();
        List<MachineDto> machineDtos=new ArrayList<>();
        machines.forEach((machine)->{
            MachineDto machineDto=new MachineDto();
            machineDto.setMachineType(machine.getMachineType());
            machineDto.setCity(machine.getCity());
            machineDto.setDeviceId(machine.getDeviceId());
            machineDto.setCountry(machine.getCountry());
            machineDto.setId(machine.getId());
            machineDto.setSerialNumber(machine.getSerialNumber());
            machineDto.setSimNo(machine.getSimNo());
            machineDtos.add(machineDto);

        });
        return machineDtos;
    }

    @Override
    public Machine getMachineById(Long machineId) {
        Machine machine=  this.machineRepository.findById(machineId).orElseThrow(() -> new ResourceNotFoundException("Machine" , "machineId" , machineId.toString()));
        machine.setAuditLogs(null);
        return machine;
    }

    @Override
    public Machine updateMachineById(Machine machine, Long id, String username) {
        Machine savedMachine = this.machineRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Machine" , "machineId" , id.toString()));
        if(null!=machine.getMachineType()) savedMachine.setMachineType(machine.getMachineType());
        if(null!=machine.getCity())   savedMachine.setCity(machine.getCity());
        if(null!=machine.getCountry())  savedMachine.setCountry(machine.getCountry());
        if(null!=machine.getDeviceId()) savedMachine.setDeviceId(machine.getDeviceId());
        if(null!=machine.getSerialNumber()) savedMachine.setSerialNumber(machine.getSerialNumber());
        if(null!=machine.getSimNo()) savedMachine.setSimNo(machine.getSimNo());
        this.auditLogService.createLog(savedMachine , username);
        Machine machine1= this.machineRepository.save(savedMachine);
        machine1.setAuditLogs(null);
        return machine1;
    }

    @Override
    public void deleteMachineById(Long machineId, String username) {
        Machine machine = this.machineRepository.findById(machineId).orElseThrow(() -> new ResourceNotFoundException("Machine" , "machineId" , machineId.toString()));
        this.machineRepository.delete(machine);
    }


    @Override
    public Machine addMachine(Machine machine, String username) {
        machine.setId(AppUtils.generateRandomUserId());
        Machine saveMachine=this.machineRepository.save(machine);
        this.auditLogService.createLog(machine , username);
        return saveMachine;
    }






}
