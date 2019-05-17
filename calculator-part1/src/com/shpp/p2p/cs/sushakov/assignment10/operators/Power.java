package com.shpp.p2p.cs.sushakov.assignment10.operators;

/**
 * Mathematical power of a number.
 */
public class Power implements SimpleOperation {

    /** Priority of operation. */
    private int priority = 3;

    /**
     * Exponentiation base to exponent.
     * @param firstArg the base.
     * @param secondArg the exponent.
     * @return power of the number.
     */
    @Override
    public double execute(double firstArg, double secondArg) {
        return Math.pow(firstArg, secondArg);
    }

    @Override
    public int getPriority() {
        return priority;
    }

}
