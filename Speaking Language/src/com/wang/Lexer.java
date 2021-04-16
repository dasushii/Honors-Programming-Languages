package com.wang;

import java.util.ArrayList;
import java.util.HashMap;

import static com.wang.TokenType.*;

public class Lexer {
    private final String source;
    private final ArrayList<Lexeme> lexemes = new ArrayList<>();

    private int currentPosition = 0;
    private int startOfCurrentLexeme = 0;
    private int lineNumber = 1;

    private static final HashMap<String, TokenType> keywords;

    static {
        keywords = new HashMap<>();
        keywords.put("announce", ANNOUNCE);
        keywords.put("loudly", LOUDLY);
        keywords.put("cut", CUT);
        keywords.put("length", LENGTH);
        keywords.put("convert", CONVERT);
        keywords.put("if", IF);
        keywords.put("word", STRING_TYPE);
        keywords.put("words", STRING_TYPE);
        keywords.put("number", INTEGER_TYPE);
        keywords.put("numbers", INTEGER_TYPE);
        keywords.put("decimal", DOUBLE_TYPE);
        keywords.put("decimals", DOUBLE_TYPE);
        keywords.put("fact", BOOLEAN_TYPE);
        keywords.put("facts", BOOLEAN_TYPE);
        keywords.put("yep", TRUE);
        keywords.put("nope", FALSE);
        keywords.put("fun", FUN);
        keywords.put("plus", PLUS);
        keywords.put("minus", MINUS);
        keywords.put("times", TIMES);
        keywords.put("over", OVER);
        keywords.put("increase", INCREASE);
        keywords.put("decrease", DECREASE);
        keywords.put("is", IS);
        keywords.put("has", HAS);
        keywords.put("while", WHILE);
        keywords.put("until", UNTIL);
        keywords.put("my", MY);
        keywords.put("function", FUNCTION);
        keywords.put("accepts", ACCEPTS);
        keywords.put("distributes", DISTRIBUTES);
        keywords.put("and", AND);
        keywords.put("or", OR);
        keywords.put("not", NOT);
        keywords.put("nothing", NULL);
        keywords.put("equal", EQUAL);
        keywords.put("less", LESS);
        keywords.put("greater", GREATER);
        keywords.put("than", THAN);
        keywords.put("to", TO);
        keywords.put("show", SHOW);
        keywords.put("array", ARRAY);
        keywords.put("size", SIZE);
        keywords.put("with", WITH);
        keywords.put("add", ADD);
    }

    public Lexer(String source) {
        this.source = source;
    }

    private char peek() {
        if (isAtEnd()) return '\0';
        return source.charAt(currentPosition);
    }

    private char peekNext() {
        if (currentPosition + 1 >= source.length()) return '\0';
        return source.charAt(currentPosition + 1);
    }

    private boolean match(char expected) {
        if (isAtEnd() || source.charAt(currentPosition) != expected) return false;
        currentPosition++;
        return true;
    }

    private char advance() {
        char currentChar = source.charAt(currentPosition);
        if (currentChar == '\n' || currentChar == '\r') lineNumber++;
        currentPosition++;
        return currentChar;
    }

    private boolean isAtEnd() {
        return currentPosition >= source.length();
    }

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private boolean isAlpha(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c == '_');
        //add more here?
    }

    private boolean isAlphaNumeric(char c) {
        return isDigit(c) || isAlpha(c);
    }

    public ArrayList<Lexeme> lex() {
        while (!isAtEnd()) {
            startOfCurrentLexeme = currentPosition;
            Lexeme nextLexeme = getNextLexeme();
            if (nextLexeme != null) lexemes.add(nextLexeme);
        }
        lexemes.add(new Lexeme(EOF, lineNumber));
        return lexemes;
    }

    private Lexeme getNextLexeme() {
        char c = advance();
        switch (c) {
            case ' ':
            case '\t':
            case '\n':
            case '\r':
                return null;
            //single-character tokens
            case '!':
                return new Lexeme(BANG, lineNumber);
            case '(':
                return new Lexeme(OPEN_PAREN, lineNumber);
            case ')':
                return new Lexeme(CLOSE_PAREN, lineNumber);
            case '{':
                return new Lexeme(OPEN_BRACE, lineNumber);
            case '}':
                return new Lexeme(CLOSE_BRACE, lineNumber);
            case '[':
                return new Lexeme(OPEN_BRACKET, lineNumber);
            case ']':
                return new Lexeme(CLOSE_BRACKET, lineNumber);
            //Strings
            case '\"':
                return lexString();
            default:
                if (isDigit(c)) return lexNumber();
                else if (isAlpha(c)) return lexIdentifierOrKeyword();
                else Speaking.error(lineNumber, "Unexpected character: " + c);
        }
        return null;
    }

    private Lexeme lexNumber() {
        boolean isInteger = true;
        while (isDigit(peek())) advance();
        if (peek() == '.') {
            if (!isDigit(peekNext())) Speaking.error(lineNumber, "Malformed real number (ends in decimal point).");

            isInteger = false;

            advance();
            while (isDigit(peek())) advance();
        }
        String numberString = source.substring(startOfCurrentLexeme, currentPosition);
        if (isInteger) {
            int number = Integer.parseInt(numberString);
            return new Lexeme(INTEGER, number, lineNumber);
        } else {
            double number = Double.parseDouble(numberString);
            return new Lexeme(DOUBLE, number, lineNumber);
        }
    }

    private Lexeme lexString() {
        while (peek() != '"' && currentPosition < source.length()) advance();
        String text = source.substring(startOfCurrentLexeme, currentPosition) + '"';
        currentPosition++;
        return new Lexeme(STRING, text, lineNumber);
    }

    private Lexeme lexIdentifierOrKeyword() {
        while (isAlphaNumeric(peek())) advance();
        String text = source.substring(startOfCurrentLexeme, currentPosition);

        TokenType type = keywords.get(text);

        if (type == null) {
            return new Lexeme(IDENTIFIER, text, lineNumber);
        } else {
            return new Lexeme(type, lineNumber);
        }
    }
}
