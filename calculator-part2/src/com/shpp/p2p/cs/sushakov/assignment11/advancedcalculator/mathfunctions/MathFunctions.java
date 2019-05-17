package com.shpp.p2p.cs.sushakov.assignment11.advancedcalculator.mathfunctions;

import com.shpp.p2p.cs.sushakov.assignment11.advancedcalculator.TokenType;

import java.util.HashMap;
import java.util.Map;

/** Helps LexicalAnalyzer identify the type of token */
public class MathFunctions {

    /** Set type of mathematical tokens */
    private static final Map<String, TokenType> TOKEN_TYPE_MAP;
    /** Set of mathematical functions */
    private static final Map<TokenType, BinaryOperation> BINARY_OPERATIONS;
    /** Set of trigonometric functions */
    private static final Map<TokenType, UnaryOperation> MATH_FUNCTIONS;

    static {
        TOKEN_TYPE_MAP = new HashMap<>();

        // Simple math operators
        TOKEN_TYPE_MAP.put("+", TokenType.SUM);
        TOKEN_TYPE_MAP.put("-", TokenType.SUB);
        TOKEN_TYPE_MAP.put("*", TokenType.MULT);
        TOKEN_TYPE_MAP.put("/", TokenType.DIV);
        TOKEN_TYPE_MAP.put("^", TokenType.POWER);
        TOKEN_TYPE_MAP.put("(", TokenType.LBRACKET);
        TOKEN_TYPE_MAP.put(")", TokenType.RBRACKET);

        // Math functions
        TOKEN_TYPE_MAP.put("sin", TokenType.SIN);
        TOKEN_TYPE_MAP.put("cos", TokenType.COS);
        TOKEN_TYPE_MAP.put("tan", TokenType.TAN);
        TOKEN_TYPE_MAP.put("atan", TokenType.ATAN);
        TOKEN_TYPE_MAP.put("log10", TokenType.LOG_10);
        TOKEN_TYPE_MAP.put("log2", TokenType.LOG_2);
        TOKEN_TYPE_MAP.put("ln", TokenType.LN);
        TOKEN_TYPE_MAP.put("sqrt", TokenType.SQRT);


        // Binary operations
        BINARY_OPERATIONS = new HashMap<>();
        BINARY_OPERATIONS.put(TokenType.SUM, (firstArgument, secondArgument) -> firstArgument + secondArgument);
        BINARY_OPERATIONS.put(TokenType.SUB, (firstArgument, secondArgument) -> firstArgument - secondArgument);
        BINARY_OPERATIONS.put(TokenType.MULT, (firstArgument, secondArgument) -> firstArgument * secondArgument);
        BINARY_OPERATIONS.put(TokenType.DIV, (firstArgument, secondArgument) -> {
            if (secondArgument == 0) {
                throw new ArithmeticException("Zero division attempt");
            }
            return firstArgument / secondArgument;
        });
        BINARY_OPERATIONS.put(TokenType.POWER, Math::pow);

        // Trigonometric functions
        MATH_FUNCTIONS = new HashMap<>();
        MATH_FUNCTIONS.put(TokenType.SIN, Math::sin);
        MATH_FUNCTIONS.put(TokenType.COS, Math::cos);
        MATH_FUNCTIONS.put(TokenType.TAN, Math::tan);
        MATH_FUNCTIONS.put(TokenType.ATAN, Math::atan);
        MATH_FUNCTIONS.put(TokenType.LOG_10, Math::log10);
        MATH_FUNCTIONS.put(TokenType.LOG_2, arg -> Math.log10(arg) / Math.log10(2));
        MATH_FUNCTIONS.put(TokenType.LN, Math::log);
        MATH_FUNCTIONS.put(TokenType.SQRT, Math::sqrt);
    }

    /**
     * Check exists particular function.
     * @param function the function name.
     * @return true - if the particular function is exists.
     */
    public static boolean isExists(String function) {
        return TOKEN_TYPE_MAP.containsKey(function.toLowerCase());
    }

    /**
     * Getting type of function by it name.
     * @param function the function name.
     * @return the type of function by it name.
     */
    public static TokenType getFunctionType(String function) {
        return TOKEN_TYPE_MAP.get(function.toLowerCase());
    }

    /**
     * @return the object of binary operation by it type.
     */
    public static BinaryOperation getBinaryMathOperation(TokenType type) {
        return BINARY_OPERATIONS.get(type);
    }

    /**
     * Check if there is a binary operation of a particular type.
     * @param type the type of binary operation.
     * @return true - if operation exists.
     */
    public static boolean isBinaryOperation(TokenType type) {
        return BINARY_OPERATIONS.containsKey(type);
    }

    /**
     * Getting trigonometric function by type of token.
     * @param type the type of token.
     * @return the object of mathematical function.
     */
    public static UnaryOperation getTrigonometric(TokenType type) {
        return MATH_FUNCTIONS.get(type);
    }

}
