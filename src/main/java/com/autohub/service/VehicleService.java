package com.autohub.service;

import com.autohub.model.Vehicle;
import com.autohub.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    public Optional<Vehicle> getVehicleById(Long id) {
        return vehicleRepository.findById(id);
    }

    public List<Vehicle> getVehiclesByCustomerId(Long customerId) {
        return vehicleRepository.findByCustomerId(customerId);
    }

    public Vehicle addVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    public Vehicle updateVehicle(Long id, Vehicle updated) {
        return vehicleRepository.findById(id).map(existing -> {
            existing.setVehicleNumber(updated.getVehicleNumber());
            existing.setBrand(updated.getBrand());
            existing.setModel(updated.getModel());
            existing.setYear(updated.getYear());
            existing.setFuelType(updated.getFuelType());
            existing.setCustomerId(updated.getCustomerId());
            return vehicleRepository.save(existing);
        }).orElseThrow(() -> new IllegalArgumentException("Vehicle not found with id: " + id));
    }

    public void deleteVehicle(Long id) {
        if (!vehicleRepository.existsById(id)) {
            throw new IllegalArgumentException("Vehicle not found with id: " + id);
        }
        vehicleRepository.deleteById(id);
    }
}
