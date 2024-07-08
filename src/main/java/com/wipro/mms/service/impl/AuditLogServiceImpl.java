package com.wipro.mms.service.impl;

import com.wipro.mms.model.AuditLog;
import com.wipro.mms.model.Machine;
import com.wipro.mms.payload.AuditLogDto;
import com.wipro.mms.repository.AuditLogRepository;
import com.wipro.mms.repository.MachineRepository;
import com.wipro.mms.service.AuditLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuditLogServiceImpl implements AuditLogService {

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Autowired
    private MachineRepository machineRepository;

    @Override
    public List<AuditLogDto> getLogsByMachineId(Long machineId) {

        List<AuditLogDto> auditLogDtos=new ArrayList<>();

        List<AuditLog> auditLogs=this.auditLogRepository.findByMachineId(machineId);

        auditLogs.forEach((logs)->{
            AuditLogDto auditLogDto=new AuditLogDto();
            auditLogDto.setChangedAt(logs.getChangedAt());
            auditLogDto.setCity(logs.getCity());
            auditLogDto.setId(logs.getId());
            auditLogDto.setCountry(logs.getCountry());
            auditLogDto.setMachineType(logs.getMachineType());
            auditLogDto.setDeviceId(logs.getDeviceId());
            auditLogDto.setSerialNumber(logs.getSerialNumber());
            auditLogDto.setSimNo(logs.getSimNo());
            auditLogDto.setChangedBy(logs.getChangedBy());

            auditLogDtos.add(auditLogDto);

        });

        return auditLogDtos;
    }

    @Override
    public AuditLog createLog(Machine machine, String username) {
        AuditLog log = new AuditLog();
        log.setMachine(machine);
        log.setSerialNumber(machine.getSerialNumber());
        log.setDeviceId(machine.getDeviceId());
        log.setMachineType(machine.getMachineType());
        log.setSimNo(machine.getSimNo());
        log.setCountry(machine.getCountry());
        log.setCity(machine.getCity());
        log.setChangedAt(new Timestamp(System.currentTimeMillis()));
        log.setChangedBy(username);
        return this.auditLogRepository.save(log);
    }
}
