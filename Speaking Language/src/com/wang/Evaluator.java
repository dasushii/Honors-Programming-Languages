package com.wang;

public class Evaluator {
    public Lexeme eval(Lexeme tree, Environment environment) {
        if (tree == null) return null;
        switch (tree.getType()) {
            case PROGRAM:
                return eval(tree.getLeft(), environment);
            case STATEMENT_LIST:
                return evalStatementList(tree, environment);
            case INTEGER:
            case DOUBLE:
            case STRING:
            case TRUE:
            case FALSE:
                return tree;
            case PLUS:
            case MINUS:
            case TIMES:
            case OVER:
            case :
            case :
            case :
            case :
            case :
        }
    }

    private Lexeme evalStatementList(Lexeme statementList, Environment environment) {

    }
}
