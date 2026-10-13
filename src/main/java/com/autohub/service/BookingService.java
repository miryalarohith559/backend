package com.autohub.service;

import com.autohub.dto.BookingRequest;
import com.autohub.model.ServiceBooking;
import com.autohub.repository.CustomerRepository;
import com.autohub.repository.ServiceBookingRepository;
import com.autohub.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    private final ServiceBookingRepository bookingRepository;
    private final CustomerRepository customerRepository;
    private final VehicleRepository vehicleRepository;

    public BookingService(ServiceBookingRepository bookingRepository, CustomerRepository customerRepository, VehicleRepository vehicleRepository) {
        this.bookingRepository = bookingRepository;
        this.customerRepository = customerRepository;
        this.vehicleRepository = vehicleRepository;
    }

    public List<ServiceBooking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Optional<ServiceBooking> getBookingById(Long id) {
        return bookingRepository.findById(id);
    }

    public List<ServiceBooking> getBookingsByCustomer(Long customerId) {
        return bookingRepository.findByCustomerId(customerId);
    }

    public ServiceBooking createBooking(BookingRequest req) {
        if (!customerRepository.existsById(req.getCustomerId())) {
            throw new IllegalArgumentException("Customer not found with id: " + req.getCustomerId());
        }
        if (!vehicleRepository.existsById(req.getVehicleId())) {
            throw new IllegalArgumentException("Vehicle not found with id: " + req.getVehicleId());
        }

        LocalDate date = req.getServiceDate() != null ? req.getServiceDate() : LocalDate.now();
        ServiceBooking booking = new ServiceBooking(
                req.getCustomerId(),
                req.getVehicleId(),
                req.getServiceType() != null ? req.getServiceType() : "General Inspection",
                date,
                "PENDING",
                req.getDescription() != null ? req.getDescription() : "Regular scheduled servicing"
        );
        return bookingRepository.save(booking);
    }

    public ServiceBooking updateStatus(Long id, String status) {
        return bookingRepository.findById(id).map(booking -> {
            booking.setStatus(status.toUpperCase());
            return bookingRepository.save(booking);
        }).orElseThrow(() -> new IllegalArgumentException("Booking not found with id: " + id));
    }

    public void deleteBooking(Long id) {
        if (!bookingRepository.existsById(id)) {
            throw new IllegalArgumentException("Booking not found with id: " + id);
        }
        bookingRepository.deleteById(id);
    }
}
