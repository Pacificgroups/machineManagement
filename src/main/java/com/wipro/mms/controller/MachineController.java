package com.wipro.mms.controller;

import com.wipro.mms.model.Machine;
import com.wipro.mms.payload.ApiResponse;
import com.wipro.mms.payload.MachineDto;
import com.wipro.mms.service.MachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/machine")
public class MachineController {

    @Autowired
    private MachineService machineService;

    @PostMapping("/")
    public ResponseEntity<Machine> addMachine(@RequestBody Machine machine , Principal principal){
        return ResponseEntity.ok(this.machineService.addMachine(machine , principal.getName()));
    }

    @PutMapping("/{machineId}")
    public ResponseEntity<Machine> updateMachine(@RequestBody Machine machine , @PathVariable Long machineId , Principal principal){
        Machine updateMachine = this.machineService.updateMachineById(machine,machineId,principal.getName());
        return new ResponseEntity<>(updateMachine , HttpStatus.OK);
    }

    @DeleteMapping("/{machineId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> deleteMachine(@PathVariable Long machineId ,Principal principal){
        this.machineService.deleteMachineById(machineId,principal.getName());
        return new ResponseEntity<>(new ApiResponse("Machine deleted successfully" , true ) , HttpStatus.OK);
    }

    @GetMapping("/{machineId}")
    public ResponseEntity<Machine> getMachine(@PathVariable Long machineId){
        return ResponseEntity.ok(this.machineService.getMachineById(machineId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<MachineDto>> getAllMachines(){
        return ResponseEntity.ok(this.machineService.getAllMachines());
    }
}
