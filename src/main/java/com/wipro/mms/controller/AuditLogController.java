package com.wipro.mms.controller;

import com.wipro.mms.model.AuditLog;
import com.wipro.mms.payload.AuditLogDto;
import com.wipro.mms.service.AuditLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class AuditLogController {
    @Autowired
    private AuditLogService auditLogService;

    @GetMapping("/log/{machineId}")

    public ResponseEntity<List<AuditLogDto>> getLog(@PathVariable Long machineId){
        return ResponseEntity.ok(this.auditLogService.getLogsByMachineId(machineId));
    }

}
