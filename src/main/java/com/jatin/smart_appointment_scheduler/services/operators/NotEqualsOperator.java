package com.jatin.smart_appointment_scheduler.services.operators;

import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class NotEqualsOperator implements OperatorStrategy {
    
    @Override
    public boolean evaluate(Object inputValue, Object compareValue) {
        if (inputValue == null && compareValue == null) {
            return false;
        }
        if (inputValue == null || compareValue == null) {
            return true;
        }
        
        // Convert to strings for comparison to handle different types
        return !Objects.equals(inputValue.toString(), compareValue.toString());
    }
    
    @Override
    public String getOperatorName() {
        return "NOT_EQUALS";
    }
}
