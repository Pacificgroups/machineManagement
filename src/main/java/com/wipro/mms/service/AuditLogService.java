package com.wipro.mms.service;

import com.wipro.mms.model.AuditLog;
import com.wipro.mms.model.Machine;
import com.wipro.mms.payload.AuditLogDto;

import java.util.List;

public interface AuditLogService {

    List<AuditLogDto> getLogsByMachineId(Long machineId);

    AuditLog createLog(Machine machine , String username);
}
