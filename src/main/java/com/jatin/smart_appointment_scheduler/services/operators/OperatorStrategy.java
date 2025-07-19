package com.jatin.smart_appointment_scheduler.services.operators;

/**
 * Strategy interface for different operator implementations
 */
public interface OperatorStrategy {
    
    /**
     * Evaluates the comparison between two values
     * @param inputValue The left operand value
     * @param compareValue The right operand value
     * @return true if the comparison passes, false otherwise
     */
    boolean evaluate(Object inputValue, Object compareValue);
    
    /**
     * Returns the operator name this strategy handles
     */
    String getOperatorName();
}
