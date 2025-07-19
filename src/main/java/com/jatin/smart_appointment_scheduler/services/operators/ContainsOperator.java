package com.jatin.smart_appointment_scheduler.services.operators;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ContainsOperator implements OperatorStrategy {
    
    @Override
    public boolean evaluate(Object inputValue, Object compareValue) {
        if (inputValue == null || compareValue == null) {
            return false;
        }
        
        // If inputValue is a list, check if it contains the compareValue
        if (inputValue instanceof List) {
            List<?> list = (List<?>) inputValue;
            return list.stream().anyMatch(item -> 
                item != null && item.toString().equals(compareValue.toString()));
        }
        
        // If inputValue is a string, check if it contains compareValue as substring
        return inputValue.toString().contains(compareValue.toString());
    }
    
    @Override
    public String getOperatorName() {
        return "CONTAINS";
    }
}
