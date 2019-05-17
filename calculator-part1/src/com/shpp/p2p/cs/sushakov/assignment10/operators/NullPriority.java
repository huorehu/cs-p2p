package com.shpp.p2p.cs.sushakov.assignment10.operators;

/**
 * Class abstract mathematical operation that has the lowest priority.
 */
public class NullPriority implements SimpleOperation {

    /** Priority of operation. */
    private int priority = 0;

    /** Stub-method */
    @Override
    public double execute(double firstArg, double secondArg) {
        throw new ArithmeticException("This operation '~' does not exist.");
    }

    @Override
    public int getPriority() {
        return priority;
    }

}
