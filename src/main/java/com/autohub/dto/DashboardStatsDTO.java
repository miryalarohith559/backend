package com.autohub.dto;

import java.util.List;
import java.util.Map;

public class DashboardStatsDTO {
    private long totalVehicles;
    private long totalCustomers;
    private long totalBookings;
    private long pendingBookings;
    private long inProgressBookings;
    private long completedBookings;
    private double totalRevenue;
    private long totalInvoices;
    private long unpaidInvoices;
    private long paidInvoices;
    private List<Map<String, Object>> recentBookings;
    private List<Map<String, Object>> revenueByService;

    public DashboardStatsDTO() {}

    public long getTotalVehicles() {
        return totalVehicles;
    }

    public void setTotalVehicles(long totalVehicles) {
        this.totalVehicles = totalVehicles;
    }

    public long getTotalCustomers() {
        return totalCustomers;
    }

    public void setTotalCustomers(long totalCustomers) {
        this.totalCustomers = totalCustomers;
    }

    public long getTotalBookings() {
        return totalBookings;
    }

    public void setTotalBookings(long totalBookings) {
        this.totalBookings = totalBookings;
    }

    public long getPendingBookings() {
        return pendingBookings;
    }

    public void setPendingBookings(long pendingBookings) {
        this.pendingBookings = pendingBookings;
    }

    public long getInProgressBookings() {
        return inProgressBookings;
    }

    public void setInProgressBookings(long inProgressBookings) {
        this.inProgressBookings = inProgressBookings;
    }

    public long getCompletedBookings() {
        return completedBookings;
    }

    public void setCompletedBookings(long completedBookings) {
        this.completedBookings = completedBookings;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public long getTotalInvoices() {
        return totalInvoices;
    }

    public void setTotalInvoices(long totalInvoices) {
        this.totalInvoices = totalInvoices;
    }

    public long getUnpaidInvoices() {
        return unpaidInvoices;
    }

    public void setUnpaidInvoices(long unpaidInvoices) {
        this.unpaidInvoices = unpaidInvoices;
    }

    public long getPaidInvoices() {
        return paidInvoices;
    }

    public void setPaidInvoices(long paidInvoices) {
        this.paidInvoices = paidInvoices;
    }

    public List<Map<String, Object>> getRecentBookings() {
        return recentBookings;
    }

    public void setRecentBookings(List<Map<String, Object>> recentBookings) {
        this.recentBookings = recentBookings;
    }

    public List<Map<String, Object>> getRevenueByService() {
        return revenueByService;
    }

    public void setRevenueByService(List<Map<String, Object>> revenueByService) {
        this.revenueByService = revenueByService;
    }
}
