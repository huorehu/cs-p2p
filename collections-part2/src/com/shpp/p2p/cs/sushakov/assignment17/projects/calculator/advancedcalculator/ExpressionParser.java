package com.shpp.p2p.cs.sushakov.assignment17.projects.calculator.advancedcalculator;

import com.shpp.p2p.cs.sushakov.assignment17.collections.interfaces.ListP2P;
import com.shpp.p2p.cs.sushakov.assignment17.collections.interfaces.MapP2P;
import com.shpp.p2p.cs.sushakov.assignment17.projects.calculator.advancedcalculator.mathfunctions.expressions.*;

/**
 * This class contains methods that allow to make
 * a mathematical expression from the list of tokens.
 */
public final class ExpressionParser {

    /** List of tokens for making mathematical expression */
    private final ListP2P<Token> tokens;
    /** Amount tokens in the list */
    private final int size;
    /** The token position in the list with which the parser is currently running */
    private int pos;
    /** Table of variables */
    private MapP2P<String, Double> variablesTable;

    public ExpressionParser(ListP2P<Token> tokens, MapP2P<String, Double> variablesTable) {
        this.tokens = tokens;
        size = tokens.size();
        this.variablesTable = variablesTable;
    }

    /** Parse tokens and forms expression using the recursive descent method */
    public Expression getPreparedExpression() {
        Expression result = new NumberExpression(0);
        if (!match(TokenType.EOF)) {
            result = additive();
        }
        return result;
    }

    /** Mathematical addition and subtraction */
    private Expression additive() {
        Expression result = multiplicative();

        while (true) {
            Token current = peek();
            if (match(TokenType.SUM) || match(TokenType.SUB)) {
                result = new BinaryExpression(current.getType(), result, multiplicative());
                continue;
            }
            break;
        }
        return result;
    }

    /** Mathematical multiplication and division */
    private Expression multiplicative() {
        Expression result = function();

        while (true) {
            Token current = peek();
            if (match(TokenType.MULT) || match(TokenType.DIV)) {
                result = new BinaryExpression(current.getType(), result, function());
                continue;
            }
            break;
        }
        return result;
    }

    /** Calculation of trigonometric functions */
    private Expression function() {
        final Token current = peek();
        if (match(TokenType.SIN) || match(TokenType.COS) || match(TokenType.TAN) || match(TokenType.ATAN) ||
                match(TokenType.LOG_10) || match(TokenType.LOG_2) || match(TokenType.LN) || match(TokenType.SQRT)) {
            return new FunctionExpression(current.getType(), exponentiation());
        }
        return exponentiation();
    }

    /** Mathematical exponentiation */
    private Expression exponentiation() {
        Expression result = unary();

        while (true) {
            Token current = peek();
            if (match(TokenType.POWER)) {
                result =  new BinaryExpression(current.getType(), result, additive());
                continue;
            }
            break;
        }
        return result;
    }

    /** Unary operation */
    private Expression unary() {
        if (match(TokenType.SUB)) {
            return new UnaryExpression('-', primary());
        }
        return primary();
    }

    /** Identification of the current token: as a number, operator or bracket */
    private Expression primary() {
        final Token current = peek();
        // If token is a real number
        if (match(TokenType.NUMBER)) {
            return new NumberExpression(Double.parseDouble(current.getText()));
        }
        // If token is variable
        if (match(TokenType.VARIABLE)) {
            return new VariableExpression(current.getText(), variablesTable);
        }
        // If token is '(' or ')'
        if (match(TokenType.LBRACKET)) {
            Expression result = additive();
            if (!match(TokenType.RBRACKET)) {
                throw new RuntimeException("Not found right bracket");
            }
            return result;
        }
        throw new RuntimeException("Unknown expression");
    }

    /** Check current token type on match for particular type */
    private boolean match(TokenType type) {
        final Token current = peek();
        if (type != current.getType()) {
            return false;
        }
        pos++;
        return true;
    }

    /** Get current token */
    private Token peek() {
        if (pos >= size) {
            return new Token(TokenType.EOF, "");
        }
        return tokens.get(pos);
    }

}
