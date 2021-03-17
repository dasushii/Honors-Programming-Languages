package com.wang;
//finish by friday
public class Lexeme {
    private final TokenType type;
    private final int lineNumber;

    private final String stringValue;
    private final Integer intValue;
    private final Double doubleValue;

    public Lexeme(TokenType type, int lineNumber){
        this.type = type;
        this.lineNumber = lineNumber;
        this.stringValue = null;
        this.intValue = null;
        this.doubleValue = null;
    }
    public Lexeme(TokenType type, String stringValue, int lineNumber){
        this.type = type;
        this.lineNumber = lineNumber;
        this.stringValue = stringValue;
        this.intValue = null;
        this.doubleValue = null;
    }
    public Lexeme(TokenType type, int intValue, int lineNumber){
        this.type = type;
        this.lineNumber = lineNumber;
        this.stringValue = null;
        this.intValue = intValue;
        this.doubleValue = null;
    }
    public Lexeme(TokenType type, double doubleValue, int lineNumber){
        this.type = type;
        this.lineNumber = lineNumber;
        this.stringValue = null;
        this.intValue = null;
        this.doubleValue = doubleValue;
    }
    public TokenType getType(){
        return type;
    }

    public int getLineNumber(){
        return lineNumber;
    }

    public String toString(){
        String result = "";
        result += this.type + " ";
        if(this.stringValue != null) result += this.stringValue + " ";
        else if(this.doubleValue != null) result += this.doubleValue + " ";
        else if(this.intValue != null) result += this.intValue + " ";
        result += "(line: " + this.lineNumber + ")";
        return result;
    }
}
