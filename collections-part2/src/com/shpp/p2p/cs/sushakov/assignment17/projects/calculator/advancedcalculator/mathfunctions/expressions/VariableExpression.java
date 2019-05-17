package com.shpp.p2p.cs.sushakov.assignment17.projects.calculator.advancedcalculator.mathfunctions.expressions;

import com.shpp.p2p.cs.sushakov.assignment17.collections.interfaces.MapP2P;

/** The expression is presented as variable. */
public class VariableExpression implements Expression {

    /** The name of variable */
    private final String name;
    /** The table of variables */
    private final MapP2P<String, Double> variablesTable;

    public VariableExpression(String name, MapP2P<String, Double> variablesTable) {
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
