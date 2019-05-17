package com.shpp.p2p.cs.sushakov.assignment17.projects.calculator.advancedcalculator;

/** Printer calculation result */
public class ResultPrinter {

    /**
     * Print calculation result to console.
     * @param result the result of calculation.
     * @param calculator the module that performed the calculation of the expression.
     */
    public void printResult(double result, Calculator calculator) {
        if (calculator == null) {
            throw new IllegalArgumentException("calculator can not be null");
        }
        System.out.print(calculator.getCurrentTextExpression() + " = " + result + " where ");
        if (calculator.getVariables().size() != 0) {
            calculator.getVariables().forEach((name, value) -> System.out.print(name + " = " + value + "; "));
        } else {
            System.out.print("variables were not used");
        }
        System.out.println("");
        System.out.println("************************************************");
    }

    /**
     * Printing invitation message.
     * @param calculator the module that performed the calculation of the expression.
     */
    public void printInvitationMessage(Calculator calculator) {
        if (calculator == null) {
            throw new IllegalArgumentException("calculator can not be null");
        }
        System.out.println("For expression " + calculator.getCurrentTextExpression() + " enter arguments:");
    }

}
