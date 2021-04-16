package com.wang;

public class Evaluator {
    public Lexeme eval(Lexeme tree, Environment environment) {
        if (tree == null) return null;
        switch (tree.getType()) {
            case PROGRAM:
                return eval(tree.getLeft(), environment);
            case STATEMENT_LIST:
                return evalStatementList(tree, environment);
        }
    }

    private Lexeme evalStatementList(Lexeme statementList, Environment environment) {

    }
}
