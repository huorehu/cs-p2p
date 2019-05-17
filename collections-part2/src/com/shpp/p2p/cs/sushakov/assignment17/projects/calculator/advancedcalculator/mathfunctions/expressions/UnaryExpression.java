package com.shpp.p2p.cs.sushakov.assignment17.projects.calculator.advancedcalculator.mathfunctions.expressions;

/** The expression is presented as simple operation on other expression. */
public class UnaryExpression implements Expression {

    /** Simple operation */
    private final char operation;
    /** The expression over which will be a simple operation */
    private final Expression expr;

    public UnaryExpression(char operation, Expression expr) {
        this.operation = operation;
        this.expr = expr;
    }

    /** Performs an unary operation on the expression */
    @Override
    public double eval() {
        if (operation == '-') {
            return -expr.eval();
        }
        return expr.eval();
    }

}
