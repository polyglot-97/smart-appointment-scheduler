package com.jatin.smart_appointment_scheduler.services;

import com.jatin.smart_appointment_scheduler.entities.*;
import com.jatin.smart_appointment_scheduler.repositories.RuleRepository;
import com.jatin.smart_appointment_scheduler.services.operators.OperatorFactory;
import com.jatin.smart_appointment_scheduler.services.operators.OperatorStrategy;
import com.jatin.smart_appointment_scheduler.services.resolvers.VariableResolver;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RulesEngineService {
    
    private static final Logger logger = LoggerFactory.getLogger(RulesEngineService.class);
    
    private final RuleRepository ruleRepository;
    private final OperatorFactory operatorFactory;
    private final VariableResolver variableResolver;
    
    /**
     * Evaluates all active rules for appointment creation
     * @param appointment The appointment being created
     * @return Map of rule results and actions to execute
     */
    public Map<String, Object> evaluateRulesForAppointment(Appointment appointment) {
        logger.info("Starting rule evaluation for appointment creation");
        
        Map<String, Object> results = new HashMap<>();
        List<Rule> activeRules = ruleRepository.findByIsActiveTrue();
        
        logger.info("Found {} active rules to evaluate", activeRules.size());
        
        for (Rule rule : activeRules) {
            try {
                RuleEvaluationResult result = evaluateRule(rule, appointment);
                results.put(rule.getName(), result);
                
                if (result.isPassed()) {
                    logger.info("Rule '{}' PASSED - Action: {}", rule.getName(), rule.getPassAction());
                    results.put(rule.getName() + "_action", rule.getPassAction());
                } else {
                    logger.info("Rule '{}' FAILED - Last failed step: {}", rule.getName(), result.getFailedStep());
                }
                
            } catch (Exception e) {
                logger.error("Error evaluating rule '{}': {}", rule.getName(), e.getMessage(), e);
                results.put(rule.getName() + "_error", e.getMessage());
            }
        }
        
        return results;
    }
    
    /**
     * Evaluates a single rule by traversing its step chain
     */
    private RuleEvaluationResult evaluateRule(Rule rule, Appointment appointment) {
        logger.debug("Evaluating rule: {}", rule.getName());
        
        Step currentStep = rule.getStartingStep();
        
        while (currentStep != null) {
            boolean stepResult = evaluateStep(currentStep, appointment);
            
            if (!stepResult) {
                // Step failed - return failure with failed step info
                return RuleEvaluationResult.builder()
                        .passed(false)
                        .failedStep(currentStep.getName())
                        .failAction(currentStep.getFailAction())
                        .build();
            }
            
            // Step passed - move to next step
            currentStep = currentStep.getNextStep();
        }
        
        // All steps passed
        return RuleEvaluationResult.builder()
                .passed(true)
                .build();
    }
    
    /**
     * Evaluates a single step: inputOperand OPERATOR valueOperand
     */
    private boolean evaluateStep(Step step, Appointment appointment) {
        try {
            logger.debug("Evaluating step: {}", step.getName());
            
            // Resolve operand values
            Object inputValue = resolveOperandValue(step.getInputOperand(), appointment);
            Object valueToCompare = resolveOperandValue(step.getValueOperand(), appointment);
            
            // Get operator strategy
            OperatorStrategy operatorStrategy = operatorFactory.getOperator(step.getOperator().getName());
            
            // Execute comparison
            boolean result = operatorStrategy.evaluate(inputValue, valueToCompare);
            
            logger.debug("Step '{}': {} {} {} = {}", 
                step.getName(), inputValue, step.getOperator().getName(), valueToCompare, result);
            
            return result;
            
        } catch (Exception e) {
            logger.error("Error evaluating step '{}': {}", step.getName(), e.getMessage());
            throw new RuntimeException("Step evaluation failed: " + step.getName(), e);
        }
    }
    
    /**
     * Resolves operand value based on its type
     */
    private Object resolveOperandValue(Operand operand, Appointment appointment) {
        switch (operand.getOperandType()) {
            case CONSTANT:
                return operand.getValue();
                
            case VARIABLE:
                return variableResolver.resolveVariable(operand.getValue(), appointment);
                
            case LIST:
                // Split comma-separated values into list
                return List.of(operand.getValue().split(","));
                
            default:
                throw new IllegalArgumentException("Unknown operand type: " + operand.getOperandType());
        }
    }
    
    /**
     * Result of rule evaluation
     */
    public static class RuleEvaluationResult {
        private boolean passed;
        private String failedStep;
        private String failAction;
        
        public static RuleEvaluationResultBuilder builder() {
            return new RuleEvaluationResultBuilder();
        }
        
        // Getters
        public boolean isPassed() { return passed; }
        public String getFailedStep() { return failedStep; }
        public String getFailAction() { return failAction; }
        
        // Builder
        public static class RuleEvaluationResultBuilder {
            private boolean passed;
            private String failedStep;
            private String failAction;
            
            public RuleEvaluationResultBuilder passed(boolean passed) {
                this.passed = passed;
                return this;
            }
            
            public RuleEvaluationResultBuilder failedStep(String failedStep) {
                this.failedStep = failedStep;
                return this;
            }
            
            public RuleEvaluationResultBuilder failAction(String failAction) {
                this.failAction = failAction;
                return this;
            }
            
            public RuleEvaluationResult build() {
                RuleEvaluationResult result = new RuleEvaluationResult();
                result.passed = this.passed;
                result.failedStep = this.failedStep;
                result.failAction = this.failAction;
                return result;
            }
        }
    }
}
