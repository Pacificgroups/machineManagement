package com.wipro.mms.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Machine {

    @Id
    @Column(name = "machine_id")
    private long id;

    @Column(unique = true)
    private String serialNumber;

    private String deviceId;

    @Enumerated(EnumType.STRING)
    private MachineType machineType;

    private String simNo;

    private  String country;

    private String city;

    @OneToMany(mappedBy = "machine", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<AuditLog> auditLogs;



    public long getId() {
        return id;
    }

    public List<AuditLog> getAuditLogs() {
        return auditLogs;
    }

    public void setAuditLogs(List<AuditLog> auditLogs) {
        this.auditLogs = auditLogs;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public MachineType getMachineType() {
        return machineType;
    }

    public void setMachineType(MachineType machineType) {
        this.machineType = machineType;
    }

    public String getSimNo() {
        return simNo;
    }

    public void setSimNo(String simNo) {
        this.simNo = simNo;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
