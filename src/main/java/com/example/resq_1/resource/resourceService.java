package com.example.resq_1.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class resourceService {

    private final resourcerepository resourceRepository;

    @Autowired
    public resourceService(resourcerepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    // Get all resources
    public List<resource> getAllResources() {
        return resourceRepository.findAll();
    }

    // Get resource by ID
    public Optional<resource> getResourceById(Integer resId) {
        return resourceRepository.findById(resId);
    }

    // Save a resource
    public resource saveResource(resource resource) {
        return resourceRepository.save(resource);
    }

    // Delete a resource
    public void deleteResource(Integer resId) {
        resourceRepository.deleteById(resId);
    }

    // Find resource by subtype (NEW: for adminService)
    public resource getResourceBySubtype(String subtype) {
        Optional<resource> resourceOpt = resourceRepository.findByResSubtype(subtype);
        return resourceOpt.orElse(null);
    }

    // Deduct resource quantity (specifically for aid verification)
    public void deductResourceQuantity(String subtype, Integer quantity) {
        resource resourceItem = getResourceBySubtype(subtype);
        if (resourceItem == null) {
            throw new RuntimeException("Resource with subtype '" + subtype + "' not found.");
        }

        if (resourceItem.getResQuantity() < quantity) {
            throw new RuntimeException("Not enough resource quantity. Available: " + resourceItem.getResQuantity());
        }

        resourceItem.setResQuantity(resourceItem.getResQuantity() - quantity);
        resourceRepository.save(resourceItem);
    }

    // Add resource quantity (for donation or other cases)
    public void addResourceQuantity(String subtype, Integer quantity) {
        resource resourceItem = getResourceBySubtype(subtype);
        if (resourceItem == null) {
            throw new RuntimeException("Resource with subtype '" + subtype + "' not found.");
        }

        resourceItem.setResQuantity(resourceItem.getResQuantity() + quantity);
        resourceRepository.save(resourceItem);
    }
    // Update resource quantity based on whether it's an addition or deduction
    public void updateResourceQuantity(String subtype, Integer quantity, boolean isDeduct) {
        if (isDeduct) {
            deductResourceQuantity(subtype, quantity);
        } else {
            addResourceQuantity(subtype, quantity);
        }
    }

}
