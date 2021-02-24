package com.wang;

public enum TokenType {
    //Single-character tokens
    OPEN_BRACE,
    CLOSE_BRACE,
    OPEN_PAREN,
    CLOSE_PAREN,
    PERIOD,
    PLUS,
    MINUS,
    TIMES,
    DIVIDE,

    //Varying length tokens
    INTEGER_TYPE,
    STRING_TYPE,
    REAL_TYPE,
    BOOLEAN_TYPE,

    VARIABLE_NAME,
    
    INTEGER,
    STRING,
    REAL,
    BOOLEAN,
    NULL,

    MY_FUNCTION,
    IS,
    AND,
    OR,


    WHILE,
    UNTIL,

    NOT,
    EQUAL_TO,
    LESS_THAN,
    GREATER_THAN,

    COMMENT,

    //keywords
    ANNOUNCE,
    ANNOUNCE_LOUDLY,
    CUT_WORD,
    WORD_LENGTH,
    CONVERT,
    FUN,
}
