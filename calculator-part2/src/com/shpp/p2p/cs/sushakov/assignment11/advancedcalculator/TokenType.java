package com.shpp.p2p.cs.sushakov.assignment11.advancedcalculator;

/** Type of the token */
public enum TokenType {

    // Real numbers
    NUMBER,
    VARIABLE,

    // Simple operations
    SUM,        // summation        +
    SUB,        // subtraction      -
    MULT,       // multiplication   *
    DIV,        // division         /
    POWER,      // exponentiation   ^

    LBRACKET,   // left bracket     (
    RBRACKET,   // right bracket    )

    // Mathematical functions
    SIN,
    COS,
    TAN,
    ATAN,
    LOG_10,
    LOG_2,
    LN,         // natural logarithm
    SQRT,

    EOF         // end of file      \0

}
