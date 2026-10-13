package com.autohub.dto;

public class InvoiceRequest {
    private Long bookingId;
    private Long customerId;
    private Long vehicleId;
    private double serviceCost;
    private double labourCost;
    private double partsCost;
    private double taxRatePercent; // e.g. 18.0 for 18% GST

    public InvoiceRequest() {}

    public InvoiceRequest(Long bookingId, Long customerId, Long vehicleId, double serviceCost, double labourCost, double partsCost, double taxRatePercent) {
        this.bookingId = bookingId;
        this.customerId = customerId;
        this.vehicleId = vehicleId;
        this.serviceCost = serviceCost;
        this.labourCost = labourCost;
        this.partsCost = partsCost;
        this.taxRatePercent = taxRatePercent;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
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

    public double getServiceCost() {
        return serviceCost;
    }

    public void setServiceCost(double serviceCost) {
        this.serviceCost = serviceCost;
    }

    public double getLabourCost() {
        return labourCost;
    }

    public void setLabourCost(double labourCost) {
        this.labourCost = labourCost;
    }

    public double getPartsCost() {
        return partsCost;
    }

    public void setPartsCost(double partsCost) {
        this.partsCost = partsCost;
    }

    public double getTaxRatePercent() {
        return taxRatePercent;
    }

    public void setTaxRatePercent(double taxRatePercent) {
        this.taxRatePercent = taxRatePercent;
    }
}
