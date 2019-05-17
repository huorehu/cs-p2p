package com.shpp.p2p.cs.sushakov.assignment17.projects.calculator.advancedcalculator.mathfunctions.expressions;

import com.shpp.p2p.cs.sushakov.assignment17.projects.calculator.advancedcalculator.TokenType;
import com.shpp.p2p.cs.sushakov.assignment17.projects.calculator.advancedcalculator.mathfunctions.MathFunctions;

/** The expression is presented as mathematical function. */
public class FunctionExpression implements Expression {

    /** Type of mathematical function */
    private final TokenType operation;
    /** Expression which is the argument of a mathematical function */
    private final Expression expr;

    public FunctionExpression(TokenType operation, Expression expr) {
        this.operation = operation;
        this.expr = expr;
    }

    /** Calculation of mathematical function. */
    @Override
    public double eval() {
        return MathFunctions.getTrigonometric(operation).execute(expr.eval());
    }

}
