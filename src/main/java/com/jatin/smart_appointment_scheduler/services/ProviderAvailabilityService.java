package com.jatin.smart_appointment_scheduler.services;

import com.jatin.smart_appointment_scheduler.entities.ProviderAvailability;
import com.jatin.smart_appointment_scheduler.repositories.ProviderAvailabilityRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ProviderAvailabilityService {
    private static final Logger logger = LoggerFactory.getLogger(ProviderAvailabilityService.class);
    private final ProviderAvailabilityRepository repository;

    public ProviderAvailability create(ProviderAvailability availability) {
        try {
            availability.setIsActive(true);
            return repository.save(availability);
        } catch (Exception e) {
            logger.error("Unexpected error in create availability", e);
            throw new RuntimeException("Failed to create provider availability", e);
        }
    }

    public List<ProviderAvailability> getAll() {
        try {
            return repository.findAllByIsActiveTrue();
        } catch (Exception e) {
            logger.error("Unexpected error in getAll availabilities", e);
            throw new RuntimeException("Failed to fetch provider availabilities", e);
        }
    }

    public ProviderAvailability getById(Long id) {
        try {
            return repository.findByIdAndIsActiveTrue(id)
                    .orElseThrow(() -> new NoSuchElementException("Provider availability not found or inactive"));
        } catch (NoSuchElementException e) {
            logger.warn("Provider availability not found for id {}: {}", id, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error in getById availability", e);
            throw new RuntimeException("Failed to fetch provider availability by id", e);
        }
    }

    public ProviderAvailability update(Long id, ProviderAvailability updated) {
        try {
            ProviderAvailability existing = repository.findByIdAndIsActiveTrue(id)
                    .orElseThrow(() -> new NoSuchElementException("Provider availability not found or inactive"));
            existing.setProvider(updated.getProvider());
            existing.setClinic(updated.getClinic());
            existing.setDays(updated.getDays());
            existing.setStartTime(updated.getStartTime());
            existing.setEndTime(updated.getEndTime());
            // updatedAt will be set by @PreUpdate
            return repository.save(existing);
        } catch (NoSuchElementException e) {
            logger.warn("Provider availability not found for update id {}: {}", id, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error in update availability", e);
            throw new RuntimeException("Failed to update provider availability", e);
        }
    }

    public void delete(Long id) {
        try {
            ProviderAvailability availability = repository.findByIdAndIsActiveTrue(id)
                    .orElseThrow(() -> new NoSuchElementException("Provider availability not found or already inactive"));
            availability.setIsActive(false);
            repository.save(availability);
        } catch (NoSuchElementException e) {
            logger.warn("Provider availability not found for delete id {}: {}", id, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error in delete availability", e);
            throw new RuntimeException("Failed to delete provider availability", e);
        }
    }
} 