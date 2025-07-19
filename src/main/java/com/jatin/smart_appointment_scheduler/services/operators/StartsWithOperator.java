package com.jatin.smart_appointment_scheduler.services.operators;

import org.springframework.stereotype.Component;

@Component
public class StartsWithOperator implements OperatorStrategy {
    
    @Override
    public boolean evaluate(Object inputValue, Object compareValue) {
        if (inputValue == null || compareValue == null) {
            return false;
        }
        
        String input = inputValue.toString();
        String prefix = compareValue.toString();
        
        return input.startsWith(prefix);
    }
    
    @Override
    public String getOperatorName() {
        return "STARTS_WITH";
    }
}
