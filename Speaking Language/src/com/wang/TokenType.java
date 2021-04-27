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

    MY,
    FUNCTION,
    IS,
    HAS,
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

    //End of File
    EOF,

    //Additional Lexemes for parsing
    PROGRAM,
    STATEMENT_LIST,
    STATEMENT,
    EXPRESSION,
    DECLARATION,
    ASSIGNMENT,
    LOOP,
    CONDITIONAL,
    BLOCK,
    PRIMARY,
    UNARY,
    BINARY,
    FUNCTION_CALL,
    VARIABLE_DECLARATION,
    FUNCTION_DECLARATION,
    VARIABLE_ASSIGNMENT,
    WHILE_LOOP,
    UNTIL_LOOP,
    LITERAL,
    BINARY_OPERATION,
    OPTIONAL_RETURN,
    PARAMETER_LIST,
    VARIABLE_TYPE,
    TYPE_LIST,
    MATH_OPERATOR,
    COMPARATOR,

    //Closure
    CLOSURE,

    //Glue!!!!
    GLUE,
}
