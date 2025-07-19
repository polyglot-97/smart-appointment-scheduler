package com.jatin.smart_appointment_scheduler.services.resolvers;

import com.jatin.smart_appointment_scheduler.entities.Appointment;
import com.jatin.smart_appointment_scheduler.entities.Encounter;
import com.jatin.smart_appointment_scheduler.repositories.EncounterRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
@RequiredArgsConstructor
public class VariableResolver {
    
    private static final Logger logger = LoggerFactory.getLogger(VariableResolver.class);
    
    private final EncounterRepository encounterRepository;
    
    /**
     * Resolves variable expressions like:
     * - patient.age
     * - appointment.type
     * - encounter.icdCodes
     * - encounter.lastInjectionDays (custom calculation)
     * - provider.specialization
     * etc.
     */
    public Object resolveVariable(String variableExpression, Appointment appointment) {
        logger.debug("Resolving variable: {}", variableExpression);
        
        try {
            String[] parts = variableExpression.split("\\.");
            if (parts.length < 2) {
                throw new IllegalArgumentException("Invalid variable expression: " + variableExpression);
            }
            
            String entityName = parts[0];
            String propertyPath = String.join(".", java.util.Arrays.copyOfRange(parts, 1, parts.length));
            
            Object entity = getEntityByName(entityName, appointment);
            if (entity == null) {
                logger.warn("Entity '{}' not found for appointment {}", entityName, appointment.getId());
                return null;
            }
            
            return resolvePropertyPath(entity, propertyPath, appointment);
            
        } catch (Exception e) {
            logger.error("Error resolving variable '{}': {}", variableExpression, e.getMessage());
            throw new RuntimeException("Failed to resolve variable: " + variableExpression, e);
        }
    }
    
    /**
     * Gets the root entity based on name
     */
    private Object getEntityByName(String entityName, Appointment appointment) {
        switch (entityName.toLowerCase()) {
            case "appointment":
                return appointment;
            case "patient":
                return appointment.getPatient();
            case "provider":
                return appointment.getProvider();
            case "clinic":
                return appointment.getClinic();
            case "encounter":
                // Get the most recent encounter for this patient
                return getLatestEncounterForPatient(appointment.getPatient().getId());
            case "encounters":
                // Get all encounters for this patient
                return getAllEncountersForPatient(appointment.getPatient().getId());
            default:
                throw new IllegalArgumentException("Unknown entity: " + entityName);
        }
    }
    
    /**
     * Resolves nested property paths using reflection
     */
    private Object resolvePropertyPath(Object entity, String propertyPath, Appointment appointment) throws Exception {
        if (entity == null) {
            return null;
        }
        
        String[] properties = propertyPath.split("\\.");
        Object currentObject = entity;
        
        for (String property : properties) {
            if (currentObject == null) {
                return null;
            }
            
            // Handle special calculated properties
            if (isSpecialProperty(property)) {
                currentObject = resolveSpecialProperty(currentObject, property, appointment);
            } else {
                currentObject = getPropertyValue(currentObject, property);
            }
        }
        
        return currentObject;
    }
    
    /**
     * Checks if this is a special calculated property
     */
    private boolean isSpecialProperty(String property) {
        return property.equals("lastInjectionDays") || 
               property.equals("age") || 
               property.equals("daysSinceLastEncounter") ||
               property.startsWith("count") ||
               property.startsWith("has");
    }
    
    /**
     * Resolves special calculated properties
     */
    private Object resolveSpecialProperty(Object entity, String property, Appointment appointment) {
        switch (property) {
            case "lastInjectionDays":
                return calculateLastInjectionDays(appointment.getPatient().getId());
                
            case "age":
                // If entity is Patient, calculate age from dateOfBirth
                return calculateAge(entity);
                
            case "daysSinceLastEncounter":
                return calculateDaysSinceLastEncounter(appointment.getPatient().getId());
                
            default:
                if (property.startsWith("countIcdStartingWith")) {
                    String prefix = property.substring("countIcdStartingWith".length());
                    return countIcdCodesStartingWith(appointment.getPatient().getId(), prefix);
                }
                if (property.startsWith("hasIcdStartingWith")) {
                    String prefix = property.substring("hasIcdStartingWith".length());
                    return hasIcdCodeStartingWith(appointment.getPatient().getId(), prefix);
                }
                throw new IllegalArgumentException("Unknown special property: " + property);
        }
    }
    
    /**
     * Gets property value using reflection (getter method or field access)
     */
    private Object getPropertyValue(Object object, String propertyName) throws Exception {
        Class<?> clazz = object.getClass();
        
        // Try getter method first
        try {
            String getterName = "get" + capitalize(propertyName);
            Method getter = clazz.getMethod(getterName);
            return getter.invoke(object);
        } catch (NoSuchMethodException e) {
            // Try boolean getter
            try {
                String booleanGetterName = "is" + capitalize(propertyName);
                Method getter = clazz.getMethod(booleanGetterName);
                return getter.invoke(object);
            } catch (NoSuchMethodException e2) {
                // Try direct field access
                try {
                    Field field = clazz.getDeclaredField(propertyName);
                    field.setAccessible(true);
                    return field.get(object);
                } catch (NoSuchFieldException e3) {
                    throw new IllegalArgumentException("Property '" + propertyName + "' not found in " + clazz.getSimpleName());
                }
            }
        }
    }
    
    /**
     * Calculates days since last injection (ICD code starting with 'J')
     */
    private Long calculateLastInjectionDays(Long patientId) {
        List<Encounter> encounters = encounterRepository.findByAppointmentPatientIdAndIsActiveTrueOrderByServiceDateDesc(patientId);
        
        for (Encounter encounter : encounters) {
            if (encounter.getIcdCodes() != null) {
                boolean hasInjection = encounter.getIcdCodes().stream()
                    .anyMatch(code -> code.startsWith("J"));
                
                if (hasInjection) {
                    return ChronoUnit.DAYS.between(encounter.getServiceDate(), LocalDateTime.now());
                }
            }
        }
        
        return Long.MAX_VALUE; // No previous injection found
    }
    
    /**
     * Calculates patient age (placeholder - would need dateOfBirth in Patient entity)
     */
    private Integer calculateAge(Object patientEntity) {
        // This would require a dateOfBirth field in Patient entity
        // For now, return null or implement based on your Patient structure
        logger.warn("Age calculation not implemented - requires dateOfBirth field in Patient entity");
        return null;
    }
    
    /**
     * Calculates days since last encounter
     */
    private Long calculateDaysSinceLastEncounter(Long patientId) {
        List<Encounter> encounters = encounterRepository.findByAppointmentPatientIdAndIsActiveTrueOrderByServiceDateDesc(patientId);
        
        if (!encounters.isEmpty()) {
            Encounter lastEncounter = encounters.get(0);
            return ChronoUnit.DAYS.between(lastEncounter.getServiceDate(), LocalDateTime.now());
        }
        
        return Long.MAX_VALUE; // No previous encounters
    }
    
    /**
     * Counts ICD codes starting with specific prefix
     */
    private Long countIcdCodesStartingWith(Long patientId, String prefix) {
        List<Encounter> encounters = encounterRepository.findByAppointmentPatientIdAndIsActiveTrueOrderByServiceDateDesc(patientId);
        
        return encounters.stream()
            .flatMap(encounter -> encounter.getIcdCodes() != null ? encounter.getIcdCodes().stream() : java.util.stream.Stream.empty())
            .filter(code -> code.startsWith(prefix))
            .count();
    }
    
    /**
     * Checks if patient has any ICD code starting with specific prefix
     */
    private Boolean hasIcdCodeStartingWith(Long patientId, String prefix) {
        return countIcdCodesStartingWith(patientId, prefix) > 0;
    }
    
    /**
     * Gets latest encounter for patient
     */
    private Encounter getLatestEncounterForPatient(Long patientId) {
        List<Encounter> encounters = encounterRepository.findByAppointmentPatientIdAndIsActiveTrueOrderByServiceDateDesc(patientId);
        return encounters.isEmpty() ? null : encounters.get(0);
    }
    
    /**
     * Gets all encounters for patient
     */
    private List<Encounter> getAllEncountersForPatient(Long patientId) {
        return encounterRepository.findByAppointmentPatientIdAndIsActiveTrueOrderByServiceDateDesc(patientId);
    }
    
    /**
     * Capitalizes first letter of string
     */
    private String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
