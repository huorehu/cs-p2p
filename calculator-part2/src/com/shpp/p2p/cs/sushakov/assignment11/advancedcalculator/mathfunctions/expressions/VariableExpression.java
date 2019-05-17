package com.shpp.p2p.cs.sushakov.assignment11.advancedcalculator.mathfunctions.expressions;

import java.util.Map;

/** The expression is presented as variable. */
public class VariableExpression implements Expression {

    /** The name of variable */
    private final String name;
    /** The table of variables */
    private final Map<String, Double> variablesTable;

    public VariableExpression(String name, Map<String, Double> variablesTable) {
        this.name = name;
        this.variablesTable = variablesTable;
    }

    /**
     * @return the value of the variable obtained from the variable table
     * @exception RuntimeException if variable does not exists.
     */
    @Override
    public double eval() {
        if (!variablesTable.containsKey(name)) {
            throw new RuntimeException("Variable with name '" + name + "' not found");
        }
        return variablesTable.get(name);
    }

}
