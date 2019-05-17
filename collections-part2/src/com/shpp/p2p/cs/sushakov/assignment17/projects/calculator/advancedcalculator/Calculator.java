package com.shpp.p2p.cs.sushakov.assignment17.projects.calculator.advancedcalculator;

import com.shpp.p2p.cs.sushakov.assignment17.collections.HashMapP2P;
import com.shpp.p2p.cs.sushakov.assignment17.collections.interfaces.ListP2P;
import com.shpp.p2p.cs.sushakov.assignment17.collections.interfaces.MapP2P;
import com.shpp.p2p.cs.sushakov.assignment17.projects.calculator.advancedcalculator.mathfunctions.expressions.Expression;

import java.util.Set;

/**
 * Provides a API for calculating a mathematical expression with given arguments.
 * This module is capable of processing the following mathematical operations and functions:
 * +, -, *, /, ^, sin, cos, tan, atan, log10, log2, sqrt.
 */
public class Calculator {

    /** Text representation of expression */
    private String expression;
    /** Prepared expression for calculation */
    private Expression preparedExpression;
    /** Table of variables */
    private MapP2P<String, Double> variables;
    /** True - if expression contains variables */
    private boolean containsVar = false;

    public Calculator(String expression) {
        variables = new HashMapP2P<>();
        setExpression(expression);
    }

    /**
     * Calculating current expression with particular arguments.
     * @param variables the arguments for calculating expression.
     * @return real number as calculation result of expression.
     */
    public Double calculate(MapP2P<String, Double> variables) {
        if (variables == null) {
            throw new IllegalArgumentException("variables can not be null");
        }
        setVariables(variables);
        return preparedExpression.eval();
    }

    /**
     * Setting new expression.
     * @param expression the string representation of expression.
     */
    public void setExpression(String expression) {
        if (expression == null) {
            throw new IllegalArgumentException("expression can not be null");
        }
        this.expression = expression;
        variables.clear();
        // List of tokens formed from received text expression.
        ListP2P<Token> tokens = new LexicalAnalyzer(expression).tokenize();
        preparedExpression = new ExpressionParser(tokens, variables).getPreparedExpression();
        // Checks whether the variables are contained in the expression
        for (Token token : tokens) {
            if (token.getType() == TokenType.VARIABLE) {
                containsVar = true;
            }
        }
    }

    /**
     * Receiving current expression in text representation.
     * @return the text representation of expression.
     */
    public String getCurrentTextExpression() {
        return expression;
    }

    /**
     * Setting new variables into table of variables.
     * @param variables a table of variables that will be copied into the internal table.
     */
    public void setVariables(MapP2P<String, Double> variables) {
        this.variables.clear();
        Set<MapP2P.Node<String, Double>> entries = variables.entrySet();
        for (MapP2P.Node<String, Double> entry : entries) {
            setVariable(entry.getKey(), entry.getValue());
        }
    }

    /**
     * @return the inner table of variables that were using in calculation.
     */
    public MapP2P<String, Double> getVariables() {
        return variables;
    }

    public boolean isContainsVariable() {
        return containsVar;
    }

    /**
     * Add variable into table of variables.
     * @param name the name of variable.
     * @param value the value of variable.
     */
    private void setVariable(String name, double value) {
        variables.put(name, value);
    }

}
