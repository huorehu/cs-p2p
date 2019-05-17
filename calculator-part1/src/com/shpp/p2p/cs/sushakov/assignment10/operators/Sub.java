package com.shpp.p2p.cs.sushakov.assignment10.operators;

/**
 * Mathematical subtraction.
 */
public class Sub implements SimpleOperation {

    /** Priority of operation. */
    private int priority = 1;

    /**
     * Subtraction of the second argument from the first
     * @param firstArg the first argument of expression
     * @param secondArg the second argument of expression
     * @return result of the subtraction.
     */
    @Override
    public double execute(double firstArg, double secondArg) {
        return firstArg - secondArg;
    }

    @Override
    public int getPriority() {
        return priority;
    }

}
