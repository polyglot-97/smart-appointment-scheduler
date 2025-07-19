package com.jatin.smart_appointment_scheduler.services.operators;

import org.springframework.stereotype.Component;

@Component
public class LessThanOrEqualOperator implements OperatorStrategy {
    
    @Override
    public boolean evaluate(Object inputValue, Object compareValue) {
        if (inputValue == null || compareValue == null) {
            return false;
        }
        
        try {
            // Try to parse as numbers first
            Double input = Double.parseDouble(inputValue.toString());
            Double compare = Double.parseDouble(compareValue.toString());
            return input <= compare;
        } catch (NumberFormatException e) {
            // If not numbers, compare as strings
            return inputValue.toString().compareTo(compareValue.toString()) <= 0;
        }
    }
    
    @Override
    public String getOperatorName() {
        return "LESS_THAN_OR_EQUAL";
    }
}
