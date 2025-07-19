package com.jatin.smart_appointment_scheduler.services.operators;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OperatorFactory {
    
    private final Map<String, OperatorStrategy> operators;
    
    public OperatorFactory(List<OperatorStrategy> operatorStrategies) {
        this.operators = new HashMap<>();
        
        // Register all operator strategies
        for (OperatorStrategy strategy : operatorStrategies) {
            operators.put(strategy.getOperatorName(), strategy);
        }
    }
    
    /**
     * Gets the operator strategy for the given operator name
     * @param operatorName The name of the operator (e.g., "EQUALS", "GREATER_THAN")
     * @return The operator strategy
     * @throws IllegalArgumentException if operator is not found
     */
    public OperatorStrategy getOperator(String operatorName) {
        OperatorStrategy strategy = operators.get(operatorName);
        if (strategy == null) {
            throw new IllegalArgumentException("Unknown operator: " + operatorName);
        }
        return strategy;
    }
    
    /**
     * Returns all available operator names
     */
    public java.util.Set<String> getAvailableOperators() {
        return operators.keySet();
    }
}
