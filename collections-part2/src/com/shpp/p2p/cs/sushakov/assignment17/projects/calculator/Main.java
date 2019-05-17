package com.shpp.p2p.cs.sushakov.assignment17.projects.calculator;

import com.shpp.p2p.cs.sushakov.assignment17.collections.interfaces.MapP2P;
import com.shpp.p2p.cs.sushakov.assignment17.projects.calculator.advancedcalculator.ArgumentParser;
import com.shpp.p2p.cs.sushakov.assignment17.projects.calculator.advancedcalculator.Calculator;
import com.shpp.p2p.cs.sushakov.assignment17.projects.calculator.advancedcalculator.ResultPrinter;

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Stream;

/** Calculator that supports simple mathematical operations such as + - * / ^,
 *  operations with parentheses and some functions as sin, cos, tan, atan, log10, log2, sqrt */
public class Main {

    public static void main(String[] args) {

        // Expression for calculating
        String expression = null;
        if (args.length > 0) {
            expression = args[0];
        }
        ArgumentParser argumentParser = new ArgumentParser();
        Calculator calculator = null;
        MapP2P<String, Double> variables;
        // I/O
        Scanner sc = new Scanner(System.in);
        ResultPrinter printer = new ResultPrinter();

        // Waiting correct expression
        while (calculator == null) {
            try {
                calculator = new Calculator(expression);
            } catch (RuntimeException ex) {
                System.out.println(ex.getMessage() + " " + expression);
                System.out.println("Enter correct expression...");
                expression = sc.nextLine();
            }
        }
        System.out.println("Expression is correct...");

        // Definition the stream of variables that are presented as strings
        Stream<String> streamVars = Arrays.stream(args).skip(1);
        String userInputs = null;

        // Main calculation loop
        while (!"quit".equals(userInputs)) {
            try {
                variables = argumentParser.parseArguments(streamVars);
                double result = calculator.calculate(variables);
                printer.printResult(result, calculator);
            } catch (RuntimeException ex) {
                System.out.println(ex.getMessage());
            }

            if (calculator.isContainsVariable()) {
                printer.printInvitationMessage(calculator);
                System.out.println("For quit program enter 'quit'");
                userInputs = sc.nextLine();
                // Creating from user input the stream of variables for expression
                streamVars = Arrays.stream(userInputs.split(" "));
            } else {
                userInputs = "quit";
            }
        }
        System.out.println("Ending of program...");
    }

}
