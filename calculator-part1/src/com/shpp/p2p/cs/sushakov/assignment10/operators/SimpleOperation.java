package com.shpp.p2p.cs.sushakov.assignment10.operators;

/**
 * Simple abstract mathematical operation. The object implementing
 * this interface is a mathematical operation. In this case, the object
 * must contain a field indicating the priority of the mathematical operation.
 */
public interface SimpleOperation {

    double execute(double firstArg, double secondArg);

    int getPriority();

}
