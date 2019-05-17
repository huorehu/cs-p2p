package com.shpp.p2p.cs.sushakov.assignment17.projects.calculator.advancedcalculator;

import com.shpp.p2p.cs.sushakov.assignment17.collections.ArrayListP2P;
import com.shpp.p2p.cs.sushakov.assignment17.collections.interfaces.ListP2P;
import com.shpp.p2p.cs.sushakov.assignment17.projects.calculator.advancedcalculator.mathfunctions.MathFunctions;

/** This class contains methods that analyze the incoming string
 *  and prepare tokens from it to perform calculations */
public final class LexicalAnalyzer {

    /** Input expression */
    private final String input;
    /** Length of the input expression */
    private final int length;
    /** List tokens of expression */
    private final ListP2P<Token> tokens;
    /** Current getPreparedExpression position */
    private int pos;

    public LexicalAnalyzer(String input) {
        this.input = input;
        length = input.length();
        tokens = new ArrayListP2P<>(Token.class);
    }

    /** Splitting an incoming expression into tokens */
    public ListP2P<Token> tokenize() {
        while (pos < length) {
            final char current = peek();
            if (Character.isDigit(current)) {
                tokenizeNumber();
            } else if (Character.isLetter(current)) {
                tokenizeWord();
            } else if (MathFunctions.isExists(Character.toString(current))) {
                tokenizeOperator();
            } else {
                next();     // spaces
            }
        }
        return tokens;
    }

    /** Go to the next token */
    private char next() {
        pos++;
        return peek();
    }

    /**
     * Get token in current position of analyzer.
     * @return the token in current position.
     */
    private char peek() {
        if (pos >= length) {
            return '\0';
        }
        return input.charAt(pos);
    }

    /**
     * Add token to list of tokens
     * @param type the type of adding token.
     */
    private void addToken(TokenType type) {
        addToken(type, "");
    }

    /**
     * Add token to list of tokens.
     * @param type the type of adding token.
     * @param text the text that displays the current token.
     */
    private void addToken(TokenType type, String text) {
        tokens.add(new Token(type, text));
    }

    /** Tokenize number starting from current character position. */
    private void tokenizeNumber() {
        final StringBuilder buffer = new StringBuilder();
        char current = peek();
        while(Character.isDigit(current) || current == '.' || current == ',') {
            if ((current == '.' || current == ',') && (buffer.indexOf(".") != -1 || buffer.indexOf(",") != -1)) {
                throw new RuntimeException("Incorrect number format. Multiple points in expression");
            }
            buffer.append(current);
            current = next();
        }
        addToken(TokenType.NUMBER, buffer.toString().replace(",", "."));
    }

    /** Tokenize simple operator */
    private void tokenizeOperator() {
        String currentOperator = Character.toString(peek());
        if (!MathFunctions.isExists(currentOperator)) {
            throw new RuntimeException("Unknown operator");
        }
        addToken(MathFunctions.getFunctionType(currentOperator));
        next();
    }

    /** Tokenize variables and mathematical functions */
    private void tokenizeWord() {
        final StringBuilder buffer = new StringBuilder();
        char current = peek();
        while (Character.isLetter(current) || Character.toString(current).matches("[012]")) {
            buffer.append(current);
            current = next();
        }
        if (MathFunctions.isExists(buffer.toString())) {
            addToken(MathFunctions.getFunctionType(buffer.toString()));
            return;
        }
        addToken(TokenType.VARIABLE, buffer.toString());
    }

}
