package com.shpp.p2p.cs.sushakov.assignment10;

import com.shpp.p2p.cs.sushakov.assignment10.operators.Operators;

import java.util.*;

/**
 * Calculator for simple mathematical operations as + - * / ^.
 */
public class Calculator {

    private static final String VALID_EXPRESSION = "-?(\\d+(.\\d+)?|[A-Za-z]+)([-+/*^](\\d+(.\\d+)?|[A-Za-z]+))*";
    /** String representation of simple mathematical operations. */
    private static final String MATH_OPERATIONS = "+-*/^";
    /** Set of operations which assignment10 can does. */
    private Operators operators = new Operators();
    /** Mathematical expression which divide on tokens. */
    private List<String> expressionTokens;

    /** Constructor initialize default expression in assignment10. */
    public Calculator() {
        this.expressionTokens = Arrays.asList("1", "+", "1");   // default expression in assignment10
    }

    /**
     * Receives an expression in the form of a string and forms a list of its elements,
     * each representing a separate token
     * @param expression the string representation of the expression.
     * @return list tokens of the expression.
     */
    public List<String> expressionToTokens(String expression) {
        if (expression == null || !expression.matches(VALID_EXPRESSION)) {
            throw new IllegalArgumentException("Expression is not valid");
        }
        // Tokens list
        List<String> result = new ArrayList<>();
        if (expression.charAt(0) == '-') {
            expression = 0 + expression;
        }
        while (expression.length() > 0) {
            expression = getNextTokens(result, expression);
        }
        return result;
    }

    /**
     * Add in list of tokens two token: operator and operand.
     * @param tokenList the list of tokens.
     * @param expression mathematical expression.
     * @return expression as string without left operator and operand.
     */
    private String getNextTokens(List<String> tokenList, String expression) {
        char[] exprInChars = expression.toCharArray();
        int startArgIndex = 0;
        // Added simple mathematical operation.
        if (isSimpleOperator(exprInChars[startArgIndex])) {
            tokenList.add(exprInChars[startArgIndex] + "");
            startArgIndex++;
        }
        // Added variable or literal of the expression.
        for (int i = startArgIndex; i < exprInChars.length; i++) {
            if (isSimpleOperator(exprInChars[i])) {
                tokenList.add(expression.substring(startArgIndex, i));
                return expression.substring(i);
            }
        }
        tokenList.add(expression.substring(startArgIndex));
        return "";
    }

    /**
     * Check whether the character is a simple operator.
     * @param expressionCharacter the character to be checked.
     * @return true if character is a simple mathematical operation.
     */
    private boolean isSimpleOperator(char expressionCharacter) {
        return MATH_OPERATIONS.indexOf(expressionCharacter) >= 0;
    }

    /**
     * Calculating mathematical expression.
     * @param expArguments set of the arguments of the expression.
     * @return result of mathematical calculation.
     */
    public double calculate(Map<String, Double> expArguments) {
        if (!checkConformityArguments(expArguments)) {
            throw new IllegalArgumentException("Doesn't match arguments in expression and in list of arguments");
        }
        // Operands of current expression
        Stack<Double> operands = new Stack<>();
        // Contains simple mathematical operators
        Stack<String> operators = new Stack<>();
        operators.push("~");

        // Calculate expression
        for (int i = 0; i < expressionTokens.size(); i++) {
            String currentToken = expressionTokens.get(i);

            if (isNumber(currentToken)) {
                operands.push(Double.parseDouble(currentToken));
            } else if (isVariable(currentToken)) {
                operands.push(expArguments.get(currentToken));
            } else if (isSimpleOperator(currentToken.charAt(0))) {
                if (this.operators.getOperatorPriority(currentToken) > this.operators.getOperatorPriority(operators.peek())) {
                    operators.push(currentToken);
                } else {
                    double secondArg = operands.pop();
                    double firstArg = operands.pop();
                    operands.push(this.operators.getOperator(operators.pop()).execute(firstArg, secondArg));
                    i--;
                }
            }
        }
        while (!operators.peek().equals("~")) {
            String currentOperation = operators.pop();
            double secondArg = operands.pop();
            double firstArg = operands.pop();
            operands.push(this.operators.getOperator(currentOperation).execute(firstArg, secondArg));
        }

        return operands.peek();
    }

    /**
     * Checking string symbol is it a text variable or not.
     * @param currentToken the checked character.
     * @return true - if symbol is a text-variable.
     */
    private boolean isVariable(String currentToken) {
        return currentToken.matches("[A-Za-z]+");
    }

    /**
     * Checking string symbol is it a digit.
     * @param currentToken the checked character.
     * @return true - if symbol is a digit.
     */
    private boolean isNumber(String currentToken) {
        return currentToken.matches("-?\\d+(.\\d+)?");
    }

    /**
     * Checking the correspondence between variable expressions and arguments.
     * @param expArguments arguments of expression.
     * @return true - if arguments and variables are the same.
     */
    private boolean checkConformityArguments(Map<String, Double> expArguments) {
        if (expArguments == null) {
            throw new IllegalArgumentException("'expArguments' does not can be 'null'");
        }
        Set<Map.Entry<String, Double>> entries = expArguments.entrySet();
        for (Map.Entry<String, Double> entry : entries) {
            if (!isVariable(entry.getKey()) || !isNumber(entry.getValue() + "")) {
                return false;
            }
        }
        for (String token : expressionTokens) {
            if (isVariable(token) && !expArguments.containsKey(token)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Setting a new mathematical expression.
     * @param expression the mathematical expression.
     */
    public void setExpression(String expression) {
        this.expressionTokens = expressionToTokens(expression.replaceAll(",", "."));
    }

    /**
     * @return current expression that is stored in the assignment10's memory.
     */
    public String getCurrentExpression() {
        StringBuilder expression = new StringBuilder();
        expressionTokens.forEach(expression::append);
        return expression.charAt(0) != '0' ? expression.toString() : expression.substring(1);
    }

}
