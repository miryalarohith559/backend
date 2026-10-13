package com.autohub.service;

import com.autohub.model.ServiceEntity;
import com.autohub.repository.ServiceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceCatalogService {

    private final ServiceRepository serviceRepository;

    public ServiceCatalogService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    public List<ServiceEntity> getAllServices() {
        return serviceRepository.findAll();
    }

    public Optional<ServiceEntity> getServiceById(Long id) {
        return serviceRepository.findById(id);
    }

    public ServiceEntity addService(ServiceEntity service) {
        return serviceRepository.save(service);
    }

    public ServiceEntity updateService(Long id, ServiceEntity updated) {
        return serviceRepository.findById(id).map(existing -> {
            existing.setServiceName(updated.getServiceName());
            existing.setDescription(updated.getDescription());
            existing.setCost(updated.getCost());
            existing.setDurationMinutes(updated.getDurationMinutes());
            return serviceRepository.save(existing);
        }).orElseThrow(() -> new IllegalArgumentException("Service not found with id: " + id));
    }

    public void deleteService(Long id) {
        if (!serviceRepository.existsById(id)) {
            throw new IllegalArgumentException("Service not found with id: " + id);
        }
        serviceRepository.deleteById(id);
    }
}
