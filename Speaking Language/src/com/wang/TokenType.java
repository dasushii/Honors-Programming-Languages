package com.wang;

public enum TokenType {
    //Single-character tokens
    OPEN_BRACE,
    CLOSE_BRACE,
    OPEN_PAREN,
    CLOSE_PAREN,
    OPEN_BRACKET,
    CLOSE_BRACKET,
    BANG,

    //Multi-character tokens
    PLUS,
    MINUS,
    TIMES,
    OVER,

    INCREASE,
    DECREASE,

    ACCEPTS,
    DISTRIBUTES,

    SHOW,

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

    ARRAY,
    WITH,
    ADD,

    MY,
    FUNCTION,
    IS,
    AND,
    OR,

    WHILE,
    UNTIL,

    IF,

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
    OF,

    //End of File
    EOF,
}
