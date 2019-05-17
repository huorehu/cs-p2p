package com.shpp.p2p.cs.sushakov.assignment10.operators;

import java.util.HashMap;
import java.util.Map;

/**
 * Contains a list of mathematical operations to which objects containing methods
 * that perform these mathematical operations by means of functions.
 */
public class Operators {

    /** The list of mathematical operations as an objects. */
    private Map<String, SimpleOperation> operators = new HashMap<>();

    public Operators() {
        operators.put("~", new NullPriority());          // border priority
        operators.put("+", new Sum());
        operators.put("-", new Sub());
        operators.put("*", new Mult());
        operators.put("/", new Div());
        operators.put("^", new Power());
    }

    /**
     * @param operator the mathematical operator which represents as a string.
     * @return the object which contains the method reflecting mathematical operation.
     */
    public SimpleOperation getOperator(String operator) {
        return operators.get(operator);
    }

    /**
     * Priority of the particular mathematical operation.
     * @param operator string representation of the mathematical operation.
     * @return object as mathematical operation.
     */
    public int getOperatorPriority(String operator) {
        return operators.get(operator).getPriority();
    }

}
