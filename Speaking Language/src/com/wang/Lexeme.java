package com.wang;
//finish by friday
public class Lexeme {
    private final TokenType type;
    private final int lineNumber;

    private final String stringValue;
    private final Integer intValue;
    private final Double doubleValue;
    private final Boolean booleanValue;

    public Lexeme(TokenType type, int lineNumber){
        this.type = type;
        this.lineNumber = lineNumber;
        this.stringValue = null;
        this.intValue = null;
        this.doubleValue = null;
        this.booleanValue = null;
    }

    public TokenType getType(){
        return type;
    }

    public int getLineNumber(){
        return lineNumber;
    }

    public String toString(){
        return ;
    }
}
