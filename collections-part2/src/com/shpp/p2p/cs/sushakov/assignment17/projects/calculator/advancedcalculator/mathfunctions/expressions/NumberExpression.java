package com.shpp.p2p.cs.sushakov.assignment17.projects.calculator.advancedcalculator.mathfunctions.expressions;

/** The expression is presented as real number. */
public final class NumberExpression implements Expression {

    /** The value of expression. */
    private final double value;

    public NumberExpression(double value) {
        this.value = value;
    }

    /** @return the value of expression */
    @Override
    public double eval() {
        return value;
    }

}
