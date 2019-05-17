package com.shpp.p2p.cs.sushakov.assignment11.advancedcalculator.mathfunctions.expressions;

import com.shpp.p2p.cs.sushakov.assignment11.advancedcalculator.TokenType;
import com.shpp.p2p.cs.sushakov.assignment11.advancedcalculator.mathfunctions.BinaryOperation;
import com.shpp.p2p.cs.sushakov.assignment11.advancedcalculator.mathfunctions.MathFunctions;

/** The expression is presented as binary operation on two other expression. */
public final class BinaryExpression implements Expression {

    /** Two expression on which a binary operation will be performed */
    private final Expression expr1, expr2;
    /** The binary operation which will be performed on the expressions */
    private final BinaryOperation operation;

    public BinaryExpression(TokenType type, Expression expr1, Expression expr2) {
        if (!MathFunctions.isBinaryOperation(type)) {
            throw new RuntimeException("It is not binary operation");
        }
        this.operation = MathFunctions.getBinaryMathOperation(type);
        this.expr1 = expr1;
        this.expr2 = expr2;
    }

    /** Execution of current operation. */
    @Override
    public double eval() {
        return operation.execute(expr1.eval(), expr2.eval());
    }

}
