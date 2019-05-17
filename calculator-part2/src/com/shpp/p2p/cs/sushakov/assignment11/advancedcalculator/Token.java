package com.shpp.p2p.cs.sushakov.assignment11.advancedcalculator;

/** Token of an expression */
public final class Token {

    /** Type of the token */
    private TokenType type;
    /** Text representation of the token */
    private String text;

    public Token(TokenType type, String name) {
        this.type = type;
        this.text = name;
    }

    /** Returns the type of this token */
    public TokenType getType() {
        return type;
    }

    /** Returns text representation of this token */
    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return type + " " + text;
    }

}
