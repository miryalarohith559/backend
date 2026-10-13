package com.autohub.service;

import com.autohub.dto.DashboardStatsDTO;
import com.autohub.model.Customer;
import com.autohub.model.Payment;
import com.autohub.model.ServiceBooking;
import com.autohub.model.Vehicle;
import com.autohub.repository.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    private final VehicleRepository vehicleRepository;
    private final CustomerRepository customerRepository;
    private final ServiceBookingRepository bookingRepository;
    private final InvoiceRepository invoiceRepository;
    private final PaymentRepository paymentRepository;

    public DashboardService(VehicleRepository vehicleRepository,
                            CustomerRepository customerRepository,
                            ServiceBookingRepository bookingRepository,
                            InvoiceRepository invoiceRepository,
                            PaymentRepository paymentRepository) {
        this.vehicleRepository = vehicleRepository;
        this.customerRepository = customerRepository;
        this.bookingRepository = bookingRepository;
        this.invoiceRepository = invoiceRepository;
        this.paymentRepository = paymentRepository;
    }

    public DashboardStatsDTO getStats() {
        DashboardStatsDTO stats = new DashboardStatsDTO();

        long totalVehicles = vehicleRepository.count();
        long totalCustomers = customerRepository.count();
        List<ServiceBooking> allBookings = bookingRepository.findAll();
        long totalBookings = allBookings.size();

        long pending = allBookings.stream().filter(b -> "PENDING".equalsIgnoreCase(b.getStatus())).count();
        long inProgress = allBookings.stream().filter(b -> "IN_PROGRESS".equalsIgnoreCase(b.getStatus())).count();
        long completed = allBookings.stream().filter(b -> "COMPLETED".equalsIgnoreCase(b.getStatus())).count();

        List<Payment> allPayments = paymentRepository.findAll();
        double totalRevenue = allPayments.stream()
                .filter(p -> "COMPLETED".equalsIgnoreCase(p.getPaymentStatus()))
                .mapToDouble(Payment::getAmount)
                .sum();

        long totalInvoices = invoiceRepository.count();
        long paidInvoices = invoiceRepository.findAll().stream().filter(i -> "PAID".equalsIgnoreCase(i.getPaymentStatus())).count();
        long unpaidInvoices = totalInvoices - paidInvoices;

        stats.setTotalVehicles(totalVehicles);
        stats.setTotalCustomers(totalCustomers);
        stats.setTotalBookings(totalBookings);
        stats.setPendingBookings(pending);
        stats.setInProgressBookings(inProgress);
        stats.setCompletedBookings(completed);
        stats.setTotalRevenue(Math.round(totalRevenue * 100.0) / 100.0);
        stats.setTotalInvoices(totalInvoices);
        stats.setPaidInvoices(paidInvoices);
        stats.setUnpaidInvoices(unpaidInvoices);

        // Recent bookings (up to 5 latest)
        Map<Long, Customer> customerMap = customerRepository.findAll().stream()
                .collect(Collectors.toMap(Customer::getId, c -> c, (a, b) -> a));
        Map<Long, Vehicle> vehicleMap = vehicleRepository.findAll().stream()
                .collect(Collectors.toMap(Vehicle::getId, v -> v, (a, b) -> a));

        List<Map<String, Object>> recent = allBookings.stream()
                .sorted((b1, b2) -> Long.compare(b2.getId(), b1.getId()))
                .limit(6)
                .map(b -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", b.getId());
                    map.put("serviceType", b.getServiceType());
                    map.put("serviceDate", b.getServiceDate());
                    map.put("status", b.getStatus());
                    map.put("description", b.getDescription());
                    Customer cust = customerMap.get(b.getCustomerId());
                    map.put("customerName", cust != null ? cust.getName() : "Customer #" + b.getCustomerId());
                    map.put("customerPhone", cust != null ? cust.getPhone() : "");
                    Vehicle v = vehicleMap.get(b.getVehicleId());
                    map.put("vehicle", v != null ? (v.getBrand() + " " + v.getModel() + " (" + v.getVehicleNumber() + ")") : "Vehicle #" + b.getVehicleId());
                    return map;
                })
                .collect(Collectors.toList());
        stats.setRecentBookings(recent);

        // Group by service type
        Map<String, Long> serviceCounts = allBookings.stream()
                .collect(Collectors.groupingBy(ServiceBooking::getServiceType, Collectors.counting()));
        List<Map<String, Object>> breakdown = serviceCounts.entrySet().stream()
                .map(e -> {
                    Map<String, Object> item = new HashMap<>();
                    item.put("serviceName", e.getKey());
                    item.put("count", e.getValue());
                    return item;
                })
                .collect(Collectors.toList());
        stats.setRevenueByService(breakdown);

        return stats;
    }
}
