package com.autohub.repository;

import com.autohub.model.ServiceBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceBookingRepository extends JpaRepository<ServiceBooking, Long> {
    List<ServiceBooking> findByCustomerId(Long customerId);
    List<ServiceBooking> findByVehicleId(Long vehicleId);
    List<ServiceBooking> findByStatus(String status);
}
