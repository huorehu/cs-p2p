package com.shpp.p2p.cs.sushakov.assignment10.operators;

/**
 * Mathematical division.
 */
public class Div implements SimpleOperation {

    /** Priority of operation. */
    private int priority = 2;

    /**
     * Divide first number on the second.
     * @param firstArg the first argument of expression
     * @param secondArg the second argument of expression
     * @return result of the division first argument on second argument.
     */
    @Override
    public double execute(double firstArg, double secondArg) {
        if (secondArg == 0) {
            throw new ArithmeticException("Zero division attempt");
        }
        return firstArg / secondArg;
    }

    @Override
    public int getPriority() {
        return priority;
    }

}
