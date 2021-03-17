package com.wang;

public enum TokenType {
    //Single-character tokens
    OPEN_BRACE,
    CLOSE_BRACE,
    OPEN_PAREN,
    CLOSE_PAREN,
    OPEN_A_BRACKET,
    CLOSE_A_BRACKET,
    OPEN_S_BRACKET,
    CLOSE_S_BRACKET,
    BANG,

    //Multi-character tokens
    PLUS,
    MINUS,
    TIMES,
    DIVIDE,

    THE,

    ACCEPTS,
    DISTRIBUTES,

    RETURN,

    IDENTIFIER,

    INTEGER,
    INTEGER_TYPE,
    STRING,
    STRING_TYPE,
    DOUBLE,
    DOUBLE_TYPE,
    BOOLEAN_TYPE,
    TRUE,
    FALSE,

    NULL,

    ARRAY_LIST,

    MY,
    FUNCTION,
    IS,
    AND,
    OR,

    WHILE,
    UNTIL,

    NOT,
    EQUAL,
    LESS,
    GREATER,
    TO,
    THAN,

    COMMENT,

    //keywords
    ANNOUNCE,
    LOUDLY,
    CUT,
    LENGTH,
    CONVERT,
    FUN,

    //End of File
    EOF,
}
