package com.autohub.dto;

import java.time.LocalDate;

public class BookingRequest {
    private Long customerId;
    private Long vehicleId;
    private String serviceType;
    private LocalDate serviceDate;
    private String description;

    public BookingRequest() {}

    public BookingRequest(Long customerId, Long vehicleId, String serviceType, LocalDate serviceDate, String description) {
        this.customerId = customerId;
        this.vehicleId = vehicleId;
        this.serviceType = serviceType;
        this.serviceDate = serviceDate;
        this.description = description;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public LocalDate getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(LocalDate serviceDate) {
        this.serviceDate = serviceDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
