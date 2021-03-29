package com.wang;

import java.util.ArrayList;
import static com.wang.TokenType.*;

public class Recognizer {
    private static final boolean debug = true;
    //instance variables
    private final  ArrayList<Lexeme> lexemes;
    private int nextLexemeIndex = 0;
    private Lexeme currentLexeme;

    //Constructor
    public Recognizer(ArrayList<Lexeme> lexemes){
        this.lexemes = lexemes;
        advance();
    }
    //Utility Methods
    private boolean check(TokenType expected){
        return currentLexeme.getType() == expected;
    }
    private boolean checkNext(TokenType expected){
        if(nextLexemeIndex >= lexemes.size()) return false;
        return lexemes.get(nextLexemeIndex).getType() == expected;

    }
    private void consume(TokenType expected){
        if(check(expected)) advance();
        else Speaking.error(currentLexeme, "Expected " + expected + " but found " + currentLexeme);
    }
    private void advance(){
        currentLexeme = lexemes.get(nextLexemeIndex);
        nextLexemeIndex++;
    }
    //Consumption Methods
    public void program(){
        if(debug) System.out.println("-- program --");
        if(statementListPending()) statementList();
    }
    private void statementList() {
        if (debug) System.out.println("-- statementList --");
        if (statementPending()) statement();
        //what else goes in here??
    }
    private void statement(){
        if (debug) System.out.println("-- statement --");
        if(expressionPending()) expression();
        if(declarationPending()) declaration();
        if(assignmentPending()) assignment();
        if(loopPending()) loop();
        if(conditionalPending()) conditional();
        if(blockPending()) block();
    }
    public void expression(){
        if(primaryPending()) primary();
        if(unaryPending()) unary();
        if(binaryPending()) binary();
        if(functionCallPending()) functionCall();
        if(arrayCallPending()) arrayCall();
        if(check(NULL)) consume(NULL);
    }
    public void declaration(){
        if(variableDeclarationPending()) variableDeclaration();
        if(functionDeclarationPending()) functionDeclaration();
        if(arrayDeclarationPending()) arrayDeclaration();
    }
    public void assignment(){
        if(variableAssignmentPending()) variableAssignment();
        if(arrayAssignmentPending()) arrayAssignment();
    }
    public void loop(){
        if(whileLoopPending()) whileLoop();
        if(untilLoopPending()) untilLoop();
    }
    public void conditional(){
        consume(IF);
        consume(OPEN_PAREN);
        expression();
        consume(CLOSE_PAREN);
        block();
    }
    public void block(){
        //need help on this
        consume();
        consume();
    }
    public void primary(){
        if(literalPending()) literal();
        if(groupingPending()) grouping();
        if(check(IDENTIFIER)) consume(IDENTIFIER);
    }
    public void unary(){
        
    }
    public void binary(){

    }
    public void functionCall(){

    }
    public void arrayCall(){

    }
    public void variableDeclaration(){

    }
    public void functionDeclaration(){

    }
    public void arrayDeclaration(){

    }
    public void variableAssignment(){

    }
    public void arrayAssignment(){

    }
    public void whileLoop(){

    }
    public void untilLoop(){

    }
    public void literal(){

    }
    public void grouping(){

    }

    //Pending Methods
    private boolean statementListPending(){
        return statementPending();
    }
    private boolean statementPending(){
        return expressionPending() || declarationPending() || assignmentPending() || loopPending() || conditionalPending() || blockPending();
    }
    private boolean expressionPending(){
        return primaryPending() || unaryPending() || binaryPending() || functionCallPending() || arrayCallPending() || check(NULL);
    }
    private boolean declarationPending() {
        return variableDeclarationPending() || functionDeclarationPending() || arrayDeclarationPending();
    }
    private boolean assignmentPending(){
        return variableAssignmentPending() || arrayAssignmentPending();
    }
    private boolean loopPending(){
        return whileLoopPending() || untilLoopPending();
    }
    private boolean conditionalPending(){
        return check(IF);
    }
    private boolean blockPending(){
        return check(OPEN_BRACE);
    }
    private boolean unaryPending(){
        return check(INCREASE) || check(DECREASE) || check(NOT);
    }
    private boolean binaryPending(){
        return expressionPending() && binaryOperatorPending();
    }
    private boolean functionCallPending() {
        return (check(IDENTIFIER) && checkNext(OPEN_PAREN)) || check(ANNOUNCE) || check(CUT) || (check(STRING_TYPE) && checkNext(LENGTH)) || check(FUN);
    }
    private boolean arrayCallPending(){
        return check(IDENTIFIER) && checkNext(OPEN_BRACKET);
    }
    private boolean functionDeclarationPending(){
        return check(MY) && checkNext(FUNCTION);
    }
    private boolean arrayDeclarationPending(){
        return check(MY) && checkNext(ARRAY);
    }
    private boolean variableAssignmentPending(){
        return check(IDENTIFIER) && checkNext(IS);
    }
    private boolean arrayAssignmentPending(){
        return check(IDENTIFIER) && checkNext(ADD);
    }
    private boolean whileLoopPending(){
        return check(WHILE);
    }
    private boolean untilLoopPending(){
        return check(UNTIL);
    }
    private boolean binaryOperatorPending(){
        return mathOperatorPending() || comparatorPending();
    }
    private boolean mathOperatorPending(){
        return check(PLUS) || check(MINUS) || check(TIMES) || check(OVER);
    }
    private boolean comparatorPending(){
        return check(IS);
    }
    private boolean variableDeclarationPending(){
        return check(MY);
    }
    private boolean primaryPending(){
        return literalPending() || groupingPending() || check(IDENTIFIER);
    }
    private boolean literalPending(){
        return check(INTEGER) || check(STRING) || check(DOUBLE) || check(TRUE) || check(FALSE);
    }
    private boolean groupingPending(){
        return check(OPEN_PAREN);
    }
    private boolean typeListPending(){
        return variableTypePending();
    }
    private boolean optionalReturnPending(){
        return check(SHOW);
    }
    private boolean variableTypePending(){
        return check(INTEGER_TYPE) || check(STRING_TYPE) || check(DOUBLE_TYPE) || check(BOOLEAN_TYPE);
    }
}
