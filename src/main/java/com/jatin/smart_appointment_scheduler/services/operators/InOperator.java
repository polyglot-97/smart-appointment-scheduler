package com.jatin.smart_appointment_scheduler.services.operators;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InOperator implements OperatorStrategy {
    
    @Override
    public boolean evaluate(Object inputValue, Object compareValue) {
        if (inputValue == null || compareValue == null) {
            return false;
        }
        
        // compareValue should be a list
        if (compareValue instanceof List) {
            List<?> list = (List<?>) compareValue;
            return list.stream().anyMatch(item -> 
                item != null && item.toString().equals(inputValue.toString()));
        }
        
        // If compareValue is a string, treat it as comma-separated values
        String[] values = compareValue.toString().split(",");
        for (String value : values) {
            if (value.trim().equals(inputValue.toString())) {
                return true;
            }
        }
        
        return false;
    }
    
    @Override
    public String getOperatorName() {
        return "IN";
    }
}
