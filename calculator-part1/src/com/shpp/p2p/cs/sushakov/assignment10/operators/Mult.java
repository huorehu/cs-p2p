package com.shpp.p2p.cs.sushakov.assignment10.operators;

/**
 * Mathematical multiplication.
 */
public class Mult implements SimpleOperation {

    /** Priority of operation. */
    private int priority = 2;

    /**
     * Multiplication first number on the second.
     * @param firstArg the first argument of expression
     * @param secondArg the second argument of expression
     * @return result of the multiplication first argument on second argument.
     */
    @Override
    public double execute(double firstArg, double secondArg) {
        return firstArg * secondArg;
    }

    @Override
    public int getPriority() {
        return priority;
    }

}
