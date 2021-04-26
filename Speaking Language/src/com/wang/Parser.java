package com.wang;

import java.util.ArrayList;

import static com.wang.TokenType.*;

public class Parser {
    private static final boolean debug = false;
    //instance variables
    private final ArrayList<Lexeme> lexemes;
    private int nextLexemeIndex = 0;
    private Lexeme currentLexeme;

    //Constructor
    public Parser(ArrayList<Lexeme> lexemes) {
        this.lexemes = lexemes;
        advance();
    }

    //Utility Methods
    private boolean check(TokenType expected) {
        return currentLexeme.getType() == expected;
    }

    private boolean checkNext(TokenType expected) {
        if (nextLexemeIndex >= lexemes.size()) return false;
        return lexemes.get(nextLexemeIndex).getType() == expected;

    }

    private Lexeme consume(TokenType expected) {
        if (check(expected)) {
            Lexeme temp = currentLexeme;
            advance();
            return temp;
        } else {
            Speaking.error(currentLexeme, "Expected " + expected + " but found " + currentLexeme);
            return null;
        }
    }

    private void advance() {
        currentLexeme = lexemes.get(nextLexemeIndex);
        nextLexemeIndex++;
    }

    //Consumption Methods
    public Lexeme program() {
        if (debug) System.out.println("-- program --");
        Lexeme program = new Lexeme(PROGRAM, currentLexeme.getLineNumber());
        if (statementListPending()) {
            Lexeme statementList = statementList();
            program.setLeft(statementList);
        }
        return program;
    }

    private Lexeme statementList() {
        if (debug) System.out.println("-- statementList --");
        Lexeme statementList = new Lexeme(STATEMENT_LIST, currentLexeme.getLineNumber());
        Lexeme statement = statement();
        statementList.setLeft(statement);
        if (statementListPending()) {
            Lexeme statementList2 = statementList();
            statementList.setRight(statementList2);
            System.out.println(statementList.getRight());
        }
        return statementList;
    }

    private Lexeme statement() {
        if (debug) System.out.println("-- statement --");
        Lexeme statement = new Lexeme(STATEMENT, currentLexeme.getLineNumber());
        if (declarationPending()) return declaration();
        else if (assignmentPending()) return assignment();
        else if (loopPending()) return loop();
        else if (conditionalPending()) return conditional();
        else if (blockPending()) return block();
        else if (optionalReturnPending()) return optionalReturn();
        else if (expressionPending()) return expression();
        else {
            Speaking.error(currentLexeme, "Expected " + "statement" + " but found " + currentLexeme);
        }
        return statement;
    }

    private Lexeme expression() {
        if (debug) System.out.println("-- expression --");
        Lexeme expression = new Lexeme(EXPRESSION, currentLexeme.getLineNumber());
        if (check(NULL)) return consume(NULL);
        else if (unaryPending()) return unary();
        else if (functionCallPending()) return functionCall();
        else if (arrayCallPending()) return arrayCall();
        else if (binaryOperationPending()) return binaryOperation();
        else if (primaryPending()) return primary();
        else {
            Speaking.error(currentLexeme, "Expected " + "expression" + " but found " + currentLexeme);
        }
        return expression;
    }

    private Lexeme declaration() {
        if (debug) System.out.println("-- declaration --");
        Lexeme declaration = new Lexeme(DECLARATION, currentLexeme.getLineNumber());
        if (arrayDeclarationPending()) return arrayDeclaration();
        else if (functionDeclarationPending()) return functionDeclaration();
        else if (variableDeclarationPending()) return variableDeclaration();
        else {
            Speaking.error(currentLexeme, "Expected " + "declaration" + " but found " + currentLexeme);
        }
        return declaration;
    }

    private Lexeme assignment() {
        if (debug) System.out.println("-- assignment --");
        Lexeme assignment = new Lexeme(ASSIGNMENT, currentLexeme.getLineNumber());
        if (variableAssignmentPending()) return variableAssignment();
        else if (arrayAssignmentPending()) return arrayAssignment();
        else {
            Speaking.error(currentLexeme, "Expected " + "assignment" + " but found " + currentLexeme);
        }
        return assignment;
    }

    private Lexeme loop() {
        if (debug) System.out.println("-- loop --");
        Lexeme loop = new Lexeme(LOOP, currentLexeme.getLineNumber());
        if (whileLoopPending()) return whileLoop();
        else if (untilLoopPending()) return untilLoop();
        else {
            Speaking.error(currentLexeme, "Expected " + "loop" + " but found " + currentLexeme);
        }
        return loop;
    }

    private Lexeme conditional() {
        if (debug) System.out.println("-- conditional --");
        Lexeme ifLexeme = consume(IF);
        consume(OPEN_PAREN);
        Lexeme expression = expression();
        consume(CLOSE_PAREN);
        Lexeme block = block();
        ifLexeme.setLeft(expression);
        ifLexeme.setRight(block);
        return ifLexeme;
    }

    private Lexeme block() {
        if (debug) System.out.println("-- block --");
        Lexeme block = new Lexeme(BLOCK, currentLexeme.getLineNumber());
        consume(OPEN_BRACE);
        if (statementListPending()) {
            block.setLeft(statementList());
        }
        consume(CLOSE_BRACE);
        return block;
    }

    private Lexeme primary() {
        if (debug) System.out.println("-- primary --");
        Lexeme primary = new Lexeme(PRIMARY, currentLexeme.getLineNumber());
        if (literalPending()) {
            return literal();
        } else if (check(IDENTIFIER)) {
            Lexeme id = consume(IDENTIFIER);
            return id;
        } else {
            Speaking.error(currentLexeme, "Expected " + "primary" + " but found " + currentLexeme);
        }
        return primary;
    }

    private Lexeme unary() {
        if (debug) System.out.println("-- unary --");
        Lexeme unary = new Lexeme(UNARY, currentLexeme.getLineNumber());
        if (check(INCREASE)) {
            Lexeme increase = consume(INCREASE);
            return increase;
        } else if (check(DECREASE)) {
            Lexeme decrease = consume(DECREASE);
            return decrease;
        } else if (check(NOT)) {
            Lexeme not = consume(NOT);
            return not;
        } else {
            Speaking.error(currentLexeme, "Expected " + "unary" + " but found " + currentLexeme);
        }
        return unary;
    }

    private Lexeme binary() {
        if (debug) System.out.println("-- binary --");
        Lexeme binaryOperation = binaryOperation();
        binaryOperation.setRight(primary());
        return binaryOperation;
    }

    private Lexeme functionCall() {
        if (debug) System.out.println("-- functionCall --");
        Lexeme functionCall = new Lexeme(FUNCTION_CALL, currentLexeme.getLineNumber());
        if (check(IDENTIFIER)) {
            Lexeme id = consume(IDENTIFIER);
            functionCall.setLeft(id);
            consume(OPEN_PAREN);
            if (parameterListPending()) {
                functionCall.setRight(parameterList());
            }
            consume(CLOSE_PAREN);
        } else if (check(ANNOUNCE)) {
            Lexeme announce = consume(ANNOUNCE);
            functionCall.setLeft(announce);
            if (check(LOUDLY)) {
                Lexeme loudly = consume(LOUDLY);
                Lexeme glue = new Lexeme(GLUE, currentLexeme.getLineNumber());
                glue.setLeft(loudly);
                glue.setRight(expression());
                functionCall.setRight(glue);
            } else {
                functionCall.setRight(expression());
            }
        } else if (check(CUT)) {
            Lexeme cut = consume(CUT);
            functionCall.setLeft(cut);
            consume(OPEN_PAREN);
            Lexeme id = consume(IDENTIFIER);
            Lexeme and = consume(AND);
            Lexeme integer1 = consume(INTEGER);
            Lexeme to = consume(TO);
            Lexeme integer2 = consume(INTEGER);
            Lexeme glue1 = new Lexeme(GLUE, currentLexeme.getLineNumber());
            Lexeme glue2 = new Lexeme(GLUE, currentLexeme.getLineNumber());
            Lexeme glue3 = new Lexeme(GLUE, currentLexeme.getLineNumber());
            Lexeme glue4 = new Lexeme(GLUE, currentLexeme.getLineNumber());
            glue1.setLeft(id);
            glue1.setRight(glue2);
            glue2.setLeft(and);
            glue2.setRight(glue3);
            glue3.setLeft(integer1);
            glue3.setRight(glue4);
            glue4.setLeft(to);
            glue4.setRight(integer2);
            functionCall.setRight(glue1);
            consume(CLOSE_PAREN);
        } else if (check(LENGTH)) {
            Lexeme length = consume(LENGTH);
            Lexeme id = consume(IDENTIFIER);
            functionCall.setLeft(length);
            functionCall.setRight(id);
        } else if (check(CONVERT)) {
            Lexeme convert = consume(CONVERT);
            consume(OPEN_PAREN);
            Lexeme string = consume(STRING);
            consume(CLOSE_PAREN);
            functionCall.setLeft(convert);
            functionCall.setRight(string);
        } else if (check(FUN)) {
            Lexeme fun = consume(FUN);
            functionCall.setLeft(fun);
        } else {
            Speaking.error(currentLexeme, "Expected " + "functionCall" + " but found " + currentLexeme);
        }
        return functionCall;
    }

    private Lexeme arrayCall() {
        if (debug) System.out.println("-- arrayCall --");
        Lexeme arrayCall = new Lexeme(ARRAY_CALL, currentLexeme.getLineNumber());
        Lexeme id = consume(IDENTIFIER);
        arrayCall.setLeft(id);
        consume(OPEN_BRACKET);
        arrayCall.setRight(expression());
        consume(CLOSE_BRACKET);
        return arrayCall;
    }

    private Lexeme variableDeclaration() {
        if (debug) System.out.println("-- variableDeclaration --");
        Lexeme my = consume(MY);
        Lexeme variableType = variableType();
        variableType.setLeft(my);
        Lexeme id = consume(IDENTIFIER);
        if (check(HAS)) {
            Lexeme has = consume(HAS);
            Lexeme expression = expression();
            Lexeme glue1 = new Lexeme(GLUE, currentLexeme.getLineNumber());
            Lexeme glue2 = new Lexeme(GLUE, currentLexeme.getLineNumber());
            glue1.setLeft(id);
            glue1.setRight(glue2);
            glue2.setLeft(has);
            glue2.setRight(expression);
        } else {
            variableType.setRight(id);
        }
        consume(BANG);
        return variableType;
    }

    private Lexeme functionDeclaration() {
        if (debug) System.out.println("-- functionDeclaration --");
        Lexeme my = consume(MY);
        Lexeme function = consume(FUNCTION);
        Lexeme id = consume(IDENTIFIER);
        Lexeme accepts = consume(ACCEPTS);
        consume(OPEN_PAREN);
        Lexeme typeList1 = typeList();
        consume(CLOSE_PAREN);
        Lexeme and = consume(AND);
        Lexeme distributes = consume(DISTRIBUTES);
        consume(OPEN_PAREN);
        Lexeme typeList2 = typeList();
        consume(CLOSE_PAREN);
        Lexeme block = block();
        Lexeme glue1 = new Lexeme(GLUE, currentLexeme.getLineNumber());
        Lexeme glue2 = new Lexeme(GLUE, currentLexeme.getLineNumber());
        Lexeme glue3 = new Lexeme(GLUE, currentLexeme.getLineNumber());
        Lexeme glue4 = new Lexeme(GLUE, currentLexeme.getLineNumber());
        Lexeme glue5 = new Lexeme(GLUE, currentLexeme.getLineNumber());
        Lexeme glue6 = new Lexeme(GLUE, currentLexeme.getLineNumber());
        function.setLeft(my);
        glue1.setLeft(id);
        glue1.setRight(glue2);
        glue2.setLeft(accepts);
        glue2.setRight(glue3);
        glue3.setLeft(typeList1);
        glue3.setRight(glue4);
        glue4.setLeft(and);
        glue4.setRight(glue5);
        glue5.setLeft(distributes);
        glue5.setRight(glue6);
        glue6.setLeft(typeList2);
        glue6.setRight(block);
        function.setRight(glue1);
        return function;
    }

    private Lexeme arrayDeclaration() {
        if (debug) System.out.println("-- arrayDeclaration --");
        Lexeme my = consume(MY);
        Lexeme array = consume(ARRAY);
        Lexeme id = consume(IDENTIFIER);
        Lexeme is = consume(IS);
        Lexeme size = consume(SIZE);
        Lexeme integer = consume(INTEGER);
        Lexeme with = consume(WITH);
        Lexeme variableType = variableType();
        Lexeme glue1 = new Lexeme(GLUE, currentLexeme.getLineNumber());
        Lexeme glue2 = new Lexeme(GLUE, currentLexeme.getLineNumber());
        Lexeme glue3 = new Lexeme(GLUE, currentLexeme.getLineNumber());
        Lexeme glue4 = new Lexeme(GLUE, currentLexeme.getLineNumber());
        Lexeme glue5 = new Lexeme(GLUE, currentLexeme.getLineNumber());
        array.setLeft(my);
        glue1.setLeft(id);
        glue1.setRight(glue2);
        glue2.setLeft(is);
        glue2.setRight(glue3);
        glue3.setLeft(size);
        glue3.setRight(glue4);
        glue4.setLeft(integer);
        glue4.setRight(glue5);
        glue5.setLeft(with);
        glue5.setRight(variableType);
        consume(BANG);
        return array;
    }

    private Lexeme variableAssignment() {
        if (debug) System.out.println("-- variableAssignment --");
        Lexeme id = consume(IDENTIFIER);
        Lexeme has = consume(HAS);
        Lexeme expression = expression();
        has.setLeft(id);
        has.setRight(expression);
        consume(BANG);
        return has;
    }

    private Lexeme arrayAssignment() {
        if (debug) System.out.println("-- arrayAssignment --");
        Lexeme id = consume(IDENTIFIER);
        Lexeme add = consume(ADD);
        Lexeme expression = expression();
        consume(OPEN_BRACKET);
        Lexeme integer = consume(INTEGER);
        consume(CLOSE_BRACKET);
        consume(BANG);
        add.setLeft(id);
        Lexeme glue = new Lexeme(GLUE, currentLexeme.getLineNumber());
        glue.setLeft(expression);
        glue.setRight(integer);
        add.setRight(glue);
        return add;
    }

    private Lexeme whileLoop() {
        if (debug) System.out.println("-- whileLoop --");
        Lexeme whileLexeme = consume(WHILE);
        consume(OPEN_PAREN);
        Lexeme expression = expression();
        whileLexeme.setLeft(expression);
        consume(CLOSE_PAREN);
        Lexeme block = block();
        whileLexeme.setRight(block);
        return whileLexeme;
    }

    private Lexeme untilLoop() {
        if (debug) System.out.println("-- untilLoop --");
        Lexeme until = consume(UNTIL);
        consume(OPEN_PAREN);
        Lexeme expression = expression();
        consume(CLOSE_PAREN);
        Lexeme block = block();
        until.setLeft(expression);
        until.setRight(block);
        return until;
    }

    private Lexeme literal() {
        if (debug) System.out.println("-- literal --");
        Lexeme literal = new Lexeme(LITERAL, currentLexeme.getLineNumber());
        if (check(INTEGER)) return consume(INTEGER);
        else if (check(STRING)) return consume(STRING);
        else if (check(DOUBLE)) return consume(DOUBLE);
        else if (check(TRUE)) return consume(TRUE);
        else if (check(FALSE)) return consume(FALSE);
        else {
            Speaking.error(currentLexeme, "Expected " + "literal" + " but found " + currentLexeme);
        }
        return literal;
    }

    private Lexeme binaryOperation() {
        if (debug) System.out.println("-- binaryOperator --");
        Lexeme binaryOperation = new Lexeme(BINARY_OPERATION, currentLexeme.getLineNumber());
        Lexeme primary1 = primary();
        if (mathOperatorPending()) {
            Lexeme mathOperator = mathOperator();
            mathOperator.setLeft(primary1);
            Lexeme primary2 = primary();
            mathOperator.setRight(primary2);
            return mathOperator;
        } else if (comparatorPending()) {
            Lexeme comparator = comparator();
            comparator.setLeft(primary1);
            Lexeme primary2 = primary();
            comparator.setRight(primary2);
            return comparator;
        } else {
            Speaking.error(currentLexeme, "Expected " + "binaryOperator" + " but found " + currentLexeme);
        }
        return binaryOperation;
    }

    private Lexeme optionalReturn() {
        if (debug) System.out.println("-- optionalReturn --");
        Lexeme show = consume(SHOW);
        if (expressionPending()) show.setLeft(expression());
        consume(BANG);
        return show;
    }

    private Lexeme parameterList() {
        if (debug) System.out.println("-- parameterList --");
        Lexeme parameterList = new Lexeme(PARAMETER_LIST, currentLexeme.getLineNumber());
        parameterList.setLeft(expression());
        if (check(AND)) {
            Lexeme and = consume(AND);
            Lexeme glue = new Lexeme(GLUE, currentLexeme.getLineNumber());
            glue.setLeft(and);
            glue.setRight(parameterList());
            parameterList.setRight(glue);
        }
        return parameterList;
    }

    private Lexeme variableType() {
        if (debug) System.out.println("-- variableType --");
        Lexeme variableType = new Lexeme(VARIABLE_TYPE, currentLexeme.getLineNumber());
        if (check(INTEGER_TYPE)) return consume(INTEGER_TYPE);
        else if (check(STRING_TYPE)) return consume(STRING_TYPE);
        else if (check(BOOLEAN_TYPE)) return consume(BOOLEAN_TYPE);
        else if (check(DOUBLE_TYPE)) return consume(DOUBLE_TYPE);
        else if (check(NULL)) return consume(NULL);
        else {
            Speaking.error(currentLexeme, "Expected " + "variableType or FUNCTION or ARRAY" + " but found " + currentLexeme);
        }
        return variableType;
    }

    private Lexeme typeList() {
        if (debug) System.out.println("-- typeList --");
        Lexeme typeList = new Lexeme(TYPE_LIST, currentLexeme.getLineNumber());
        typeList.setLeft(variableType());
        if (check(AND)) {
            Lexeme and = consume(AND);
            Lexeme glue = new Lexeme(GLUE, currentLexeme.getLineNumber());
            glue.setLeft(and);
            glue.setRight(typeList());
            typeList.setRight(glue);
        }
        return typeList;
    }

    private Lexeme mathOperator() {
        if (debug) System.out.println("-- mathOperator --");
        Lexeme mathOperator = new Lexeme(MATH_OPERATOR, currentLexeme.getLineNumber());
        if (check(PLUS)) return consume(PLUS);
        else if (check(MINUS)) return consume(MINUS);
        else if (check(TIMES)) return consume(TIMES);
        else if (check(OVER)) return consume(OVER);
        else {
            Speaking.error(currentLexeme, "Expected " + "mathOperator" + " but found " + currentLexeme);
        }
        return mathOperator;
    }

    private Lexeme comparator() {
        if (debug) System.out.println("-- comparator --");
        Lexeme comparator = new Lexeme(COMPARATOR, currentLexeme.getLineNumber());
        if (check(IS)) {
            consume(IS);
            if (check(GREATER)) {
                Lexeme greater = consume(GREATER);
                consume(THAN);
                return greater;
            } else if (check(LESS)) {
                Lexeme less = consume(LESS);
                consume(THAN);
                return less;
            } else if (check(EQUAL)) {
                Lexeme equal = consume(EQUAL);
                consume(TO);
                return equal;
            }
        } else {
            Speaking.error(currentLexeme, "Expected " + "comparator" + " but found " + currentLexeme);
        }
        return comparator;
    }

    //Pending Methods
    private boolean statementListPending() {
        return statementPending();
    }

    private boolean statementPending() {
        return expressionPending() || declarationPending() || assignmentPending() || loopPending() || conditionalPending() || blockPending() || optionalReturnPending();
    }

    private boolean expressionPending() {
        return primaryPending() || unaryPending() || binaryOperationPending() || functionCallPending() || arrayCallPending() || check(NULL);
    }

    private boolean declarationPending() {
        return variableDeclarationPending() || functionDeclarationPending() || arrayDeclarationPending();
    }

    private boolean assignmentPending() {
        return variableAssignmentPending() || arrayAssignmentPending();
    }

    private boolean loopPending() {
        return whileLoopPending() || untilLoopPending();
    }

    private boolean conditionalPending() {
        return check(IF);
    }

    private boolean blockPending() {
        return check(OPEN_BRACE);
    }

    private boolean optionalReturnPending() {
        return check(SHOW);
    }

    private boolean unaryPending() {
        return check(INCREASE) || check(DECREASE) || check(NOT);
    }

    private boolean binaryOperationPending() {
        return binaryOperatorNextPending();
    }

    private boolean binaryOperatorNextPending() {
        return mathOperatorNextPending() || comparatorNextPending();
    }

    private boolean mathOperatorNextPending() {
        return checkNext(PLUS) || checkNext(MINUS) || checkNext(TIMES) || checkNext(OVER);
    }

    private boolean comparatorNextPending() {
        return checkNext(IS) || checkNext(AND) || checkNext(OR);
    }

    private boolean functionCallPending() {
        return (check(IDENTIFIER) && checkNext(OPEN_PAREN)) || check(ANNOUNCE) || check(CUT) || check(LENGTH) || check(FUN);
    }

    private boolean arrayCallPending() {
        return check(IDENTIFIER) && checkNext(OPEN_BRACKET);
    }

    private boolean functionDeclarationPending() {
        return check(MY) && checkNext(FUNCTION);
    }

    private boolean arrayDeclarationPending() {
        return check(MY) && checkNext(ARRAY);
    }

    private boolean variableAssignmentPending() {
        return check(IDENTIFIER) && checkNext(HAS);
    }

    private boolean arrayAssignmentPending() {
        return check(IDENTIFIER) && checkNext(ADD);
    }

    private boolean whileLoopPending() {
        return check(WHILE);
    }

    private boolean untilLoopPending() {
        return check(UNTIL);
    }

    private boolean binaryOperatorPending() {
        return mathOperatorPending() || comparatorPending();
    }

    private boolean mathOperatorPending() {
        return check(PLUS) || check(MINUS) || check(TIMES) || check(OVER);
    }

    private boolean comparatorPending() {
        return check(IS);
    }

    private boolean variableDeclarationPending() {
        return check(MY);
    }

    private boolean primaryPending() {
        return literalPending() || check(IDENTIFIER);
    }

    private boolean literalPending() {
        return check(INTEGER) || check(STRING) || check(DOUBLE) || check(TRUE) || check(FALSE);
    }

    private boolean parameterListPending() {
        return expressionPending();
    }

    private boolean typeListPending() {
        return variableTypePending();
    }

    private boolean variableTypePending() {
        return check(INTEGER_TYPE) || check(STRING_TYPE) || check(DOUBLE_TYPE) || check(BOOLEAN_TYPE) || check(NULL);
    }
}
