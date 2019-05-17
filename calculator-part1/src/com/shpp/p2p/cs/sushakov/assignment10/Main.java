package com.shpp.p2p.cs.sushakov.assignment10;

import com.huorehu.menutemplate.Menu;
import com.huorehu.menutemplate.MenuEntry;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Calculator for simple expression as + - * / ^.
 * Program has console menu.
 */
public class Main {

    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        // Arguments parser
        ArgumentParser parser = new ArgumentParser();
        Scanner scanner = new Scanner(System.in);

        try {
            // Set expression from command line
            calculator.setExpression(args[0]);
            // Calculating expression
            double result = calculator.calculate(parser.parseArguments(Arrays.stream(args).skip(1)));
            printResult(calculator.getCurrentExpression(), result + "");
        } catch (IllegalArgumentException | ArithmeticException ex) {
            System.out.println(ex.getMessage());
        }

        // Console menu
        Menu menu = new Menu();
        menu.addMenuEntry(new MenuEntry("Set new expression") {
            @Override
            public void run() {
                System.out.println("Setting new expression...");
                try {
                    calculator.setExpression(scanner.nextLine());
                    System.out.println("Expression set");
                } catch (IllegalArgumentException iae) {
                    System.out.println(iae.getMessage());
                }
            }
        });

        menu.addMenuEntry(new MenuEntry("Calculate with new arguments") {
            @Override
            public void run() {
                System.out.println("Enter arguments...");
                String[] args = scanner.nextLine().replaceAll(",", ".").split(" ");
                if (args[0].equals("")) {
                    args = new String[0];
                }
                String result;
                try {
                    // Calculating expression
                    double tempResult = calculator.calculate(parser.parseArguments(Arrays.stream(args)));
                    result = tempResult + "";
                } catch (IllegalArgumentException | ArithmeticException exc) {
                    result = exc.getMessage() + ", try again";
                }
                printResult(calculator.getCurrentExpression(), result + "");
            }
        });

        menu.addMenuEntry(new MenuEntry("Show current expression") {
            @Override
            public void run() {
                System.out.println("Current expression: " + calculator.getCurrentExpression());
            }
        });
        menu.run();
    }

    private static void printResult(String currentExpression, String result) {
        System.out.println("Mathematical expression: " + currentExpression);
        System.out.println("Result: " + result);
    }

}
