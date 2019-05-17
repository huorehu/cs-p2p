package com.shpp.p2p.cs.sushakov.assignment10.operators;

/**
 * Mathematical sum.
 */
public class Sum implements SimpleOperation {

    /** Priority of operation. */
    private int priority = 1;

    /**
     * Sum of two arguments.
     * @param firstArg the first argument of expression
     * @param secondArg the second argument of expression
     * @return result of the sum first and second arguments.
     */
    @Override
    public double execute(double firstArg, double secondArg) {
        return firstArg + secondArg;
    }

    @Override
    public int getPriority() {
        return priority;
    }

}
