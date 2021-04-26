package com.wang;

import static com.wang.TokenType.*;

public class Evaluator {
    public Lexeme eval(Lexeme tree, Environment environment) {
        Lexeme.printTree(tree);
        if (tree == null) return null;
        switch (tree.getType()) {
            case PROGRAM:
            case STATEMENT_LIST:
                return eval(tree.getLeft(), environment);
            case INTEGER:
            case DOUBLE:
            case STRING:
            case TRUE:
            case FALSE:
                return tree;
            case EQUAL:
            case LESS:
            case GREATER:
                return evalComparator(tree, environment);
            case PLUS:
            case MINUS:
            case TIMES:
            case OVER:
                return evalSimpleOperator(tree, environment);
            case IDENTIFIER:
                return
            case FUNCTION:
                return evalFunctionDeclaration();
            case ARRAY:
                return evalArrayDeclaration();
            case ADD:
                return evalArrayAssignment();
            case STRING_TYPE:
            case INTEGER_TYPE:
            case DOUBLE_TYPE:
            case BOOLEAN_TYPE:
                return evalVariableDeclaration(tree, environment);
            case HAS:
                return evalVariableAssignment(tree, environment);
            case FUNCTION_CALL:
                return evalFunctionCall();
            case WHILE:
                return evalWhileLoop();
            case UNTIL:
                return evalUntilLoop();
            case ARRAY_CALL:
                return evalArrayCall();
            case SHOW:
                return evalReturn();
            case IF:
                return evalConditional();
            default:
                return null;
        }
    }

    private Lexeme evalStatementList(Lexeme statementList, Environment environment) {
        switch (statementList.getType()) {
            case INTEGER:
            case DOUBLE:
            case STRING:
            case TRUE:
            case FALSE:
                return statementList;
            case PLUS:
            case MINUS:
            case TIMES:
            case OVER:
                return evalSimpleOperator(statementList, environment);
            case IDENTIFIER:
        }
        return null;
    }

    private Lexeme evalSimpleOperator(Lexeme tree, Environment environment) {
        switch (tree.getType()) {
            case PLUS:
                return evalPlus(tree, environment);
            case MINUS:
                return evalMinus(tree, environment);
            case OVER:
                return evalOver(tree, environment);
            case TIMES:
                return evalTimes(tree, environment);
            case INCREASE:
                return evalIncrease(tree, environment);
            case DECREASE:
                return evalDecrease(tree, environment);
            default:

                return null;
        }
    }

    private Lexeme evalPlus(Lexeme tree, Environment environment) {
        Lexeme leftHandSide = eval(tree.getLeft(), environment);
        Lexeme rightHandSide = eval(tree.getRight(), environment);

        TokenType leftType = leftHandSide.getType();
        TokenType rightType = rightHandSide.getType();

        if (leftType == INTEGER) {
            switch (rightType) {
                case INTEGER:
                    return new Lexeme(INTEGER, leftHandSide.getIntValue() + rightHandSide.getIntValue(), leftHandSide.getLineNumber());
                case DOUBLE:
                    int tempValue = (int) rightHandSide.getDoubleValue();
                    return new Lexeme(INTEGER, leftHandSide.getIntValue() + tempValue, leftHandSide.getLineNumber());
                case STRING:
                    return new Lexeme(INTEGER, leftHandSide.getIntValue() + rightHandSide.getStringValue().length(), leftHandSide.getLineNumber());
                case TRUE:
                    return new Lexeme(INTEGER, leftHandSide.getIntValue() + 4, leftHandSide.getLineNumber());
                case FALSE:
                    return new Lexeme(INTEGER, leftHandSide.getIntValue() + 5, leftHandSide.getLineNumber());
                default:
                    Speaking.error(leftHandSide, "Incompatible types. Attempt to add " + leftType + " to " + rightType);
                    return null;
            }
        } else if (leftType == DOUBLE) {
            switch (rightType) {
                case INTEGER:
                    return new Lexeme(DOUBLE, leftHandSide.getDoubleValue() + rightHandSide.getIntValue(), leftHandSide.getLineNumber());
                case DOUBLE:
                    return new Lexeme(DOUBLE, leftHandSide.getDoubleValue() + rightHandSide.getDoubleValue(), leftHandSide.getLineNumber());
                case STRING:
                    return new Lexeme(DOUBLE, leftHandSide.getDoubleValue() + rightHandSide.getStringValue().length(), leftHandSide.getLineNumber());
                case TRUE:
                    return new Lexeme(DOUBLE, leftHandSide.getDoubleValue() + 4.0, leftHandSide.getLineNumber());
                case FALSE:
                    return new Lexeme(DOUBLE, leftHandSide.getDoubleValue() + 5.0, leftHandSide.getLineNumber());
                default:
                    Speaking.error(leftHandSide, "Incompatible types. Attempt to add " + leftType + " to " + rightType);
                    return null;
            }
        } else if (leftType == STRING) {
            switch (rightType) {
                case INTEGER:
                    return new Lexeme(STRING, leftHandSide.getStringValue() + rightHandSide.getIntValue(), leftHandSide.getLineNumber());
                case DOUBLE:
                    return new Lexeme(STRING, leftHandSide.getStringValue() + rightHandSide.getDoubleValue(), leftHandSide.getLineNumber());
                case STRING:
                    return new Lexeme(STRING, leftHandSide.getStringValue() + rightHandSide.getStringValue(), leftHandSide.getLineNumber());
                case TRUE:
                    return new Lexeme(STRING, leftHandSide.getStringValue() + "true", leftHandSide.getLineNumber());
                case FALSE:
                    return new Lexeme(STRING, leftHandSide.getStringValue() + "false", leftHandSide.getLineNumber());
                default:
                    Speaking.error(leftHandSide, "Incompatible types. Attempt to add " + leftType + " to " + rightType);
                    return null;
            }
        } else {
            Speaking.error(leftHandSide, "Incompatible types. Attempt to add " + leftType + " to " + rightType);
            return null;
        }
    }

    private Lexeme evalMinus(Lexeme tree, Environment environment) {
        Lexeme leftHandSide = eval(tree.getLeft(), environment);
        Lexeme rightHandSide = eval(tree.getRight(), environment);

        TokenType leftType = leftHandSide.getType();
        TokenType rightType = rightHandSide.getType();

        if (leftType == INTEGER) {
            switch (rightType) {
                case INTEGER:
                    return new Lexeme(INTEGER, leftHandSide.getIntValue() - rightHandSide.getIntValue(), leftHandSide.getLineNumber());
                case DOUBLE:
                    int tempValue = (int) rightHandSide.getDoubleValue();
                    return new Lexeme(INTEGER, leftHandSide.getIntValue() - tempValue, leftHandSide.getLineNumber());
                case STRING:
                    return new Lexeme(INTEGER, leftHandSide.getIntValue() - rightHandSide.getStringValue().length(), leftHandSide.getLineNumber());
                case TRUE:
                    return new Lexeme(INTEGER, leftHandSide.getIntValue() - 4, leftHandSide.getLineNumber());
                case FALSE:
                    return new Lexeme(INTEGER, leftHandSide.getIntValue() - 5, leftHandSide.getLineNumber());
                default:
                    Speaking.error(leftHandSide, "Incompatible types. Attempt to subtract " + rightType + " from " + leftType);
                    return null;
            }
        } else if (leftType == DOUBLE) {
            switch (rightType) {
                case INTEGER:
                    return new Lexeme(DOUBLE, leftHandSide.getDoubleValue() - rightHandSide.getIntValue(), leftHandSide.getLineNumber());
                case DOUBLE:
                    return new Lexeme(DOUBLE, leftHandSide.getDoubleValue() - rightHandSide.getDoubleValue(), leftHandSide.getLineNumber());
                case STRING:
                    return new Lexeme(DOUBLE, leftHandSide.getDoubleValue() - rightHandSide.getStringValue().length(), leftHandSide.getLineNumber());
                case TRUE:
                    return new Lexeme(DOUBLE, leftHandSide.getDoubleValue() - 4.0, leftHandSide.getLineNumber());
                case FALSE:
                    return new Lexeme(DOUBLE, leftHandSide.getDoubleValue() - 5.0, leftHandSide.getLineNumber());
                default:
                    Speaking.error(leftHandSide, "Incompatible types. Attempt to subtract " + rightType + " from " + leftType);
                    return null;
            }
        } else if (leftType == STRING) {
            switch (rightType) {
                case INTEGER:
                    return new Lexeme(STRING, leftHandSide.getStringValue().substring(0, leftHandSide.getStringValue().length() - rightHandSide.getIntValue()), leftHandSide.getLineNumber());
                case DOUBLE:
                    int tempValue = (int) rightHandSide.getDoubleValue();
                    return new Lexeme(STRING, leftHandSide.getStringValue().substring(0, leftHandSide.getStringValue().length() - tempValue), leftHandSide.getLineNumber());
                case STRING:
                    return new Lexeme(STRING, stringSubtraction(leftHandSide.getStringValue(), rightHandSide.getStringValue()), leftHandSide.getLineNumber());
                case TRUE:
                    return new Lexeme(STRING, stringSubtraction(leftHandSide.getStringValue(), "true"), leftHandSide.getLineNumber());
                case FALSE:
                    return new Lexeme(STRING, stringSubtraction(leftHandSide.getStringValue(), "false"), leftHandSide.getLineNumber());
                default:
                    Speaking.error(leftHandSide, "Incompatible types. Attempt to subtract " + rightType + " from " + leftType);
                    return null;
            }
        } else {
            Speaking.error(leftHandSide, "Incompatible types. Attempt to subtract " + rightType + " from " + leftType);
            return null;
        }
    }

    private String stringSubtraction(String left, String right) {
        String result = left;
        String subtractor = right;
        boolean proceed = true;
        if (subtractor.length() == 0) {
            proceed = false;
        }
        int i = 0;
        while (proceed) {
            char temp = subtractor.charAt(0);
            for (int x = 0; x < result.length(); x++) {
                if (temp == result.charAt(x)) {
                    result = result.substring(0, x) + result.substring(x + 1);
                    x--;
                }
            }
            if ((i + 1) >= subtractor.length()) {
                proceed = false;
            } else {
                i++;
                subtractor = subtractor.substring(i);
            }
        }
        return result;
    }

    private Lexeme evalTimes(Lexeme tree, Environment environment) {
        Lexeme leftHandSide = eval(tree.getLeft(), environment);
        Lexeme rightHandSide = eval(tree.getRight(), environment);

        TokenType leftType = leftHandSide.getType();
        TokenType rightType = rightHandSide.getType();

        if (leftType == INTEGER) {
            switch (rightType) {
                case INTEGER:
                    return new Lexeme(INTEGER, leftHandSide.getIntValue() * rightHandSide.getIntValue(), leftHandSide.getLineNumber());
                case DOUBLE:
                    int tempValue = (int) rightHandSide.getDoubleValue();
                    return new Lexeme(INTEGER, leftHandSide.getIntValue() * tempValue, leftHandSide.getLineNumber());
                case STRING:
                    return new Lexeme(INTEGER, leftHandSide.getIntValue() + rightHandSide.getStringValue().length(), leftHandSide.getLineNumber());
                case TRUE:
                    return new Lexeme(INTEGER, leftHandSide.getIntValue() * 4, leftHandSide.getLineNumber());
                case FALSE:
                    return new Lexeme(INTEGER, leftHandSide.getIntValue() * 5, leftHandSide.getLineNumber());
                default:
                    Speaking.error(leftHandSide, "Incompatible types. Attempt to multiply " + leftType + " with " + rightType);
                    return null;
            }
        } else if (leftType == DOUBLE) {
            switch (rightType) {
                case INTEGER:
                    return new Lexeme(DOUBLE, leftHandSide.getDoubleValue() * rightHandSide.getIntValue(), leftHandSide.getLineNumber());
                case DOUBLE:
                    return new Lexeme(DOUBLE, leftHandSide.getDoubleValue() * rightHandSide.getDoubleValue(), leftHandSide.getLineNumber());
                case STRING:
                    return new Lexeme(DOUBLE, leftHandSide.getDoubleValue() * rightHandSide.getStringValue().length(), leftHandSide.getLineNumber());
                case TRUE:
                    return new Lexeme(DOUBLE, leftHandSide.getDoubleValue() * 4.0, leftHandSide.getLineNumber());
                case FALSE:
                    return new Lexeme(DOUBLE, leftHandSide.getDoubleValue() * 5.0, leftHandSide.getLineNumber());
                default:
                    Speaking.error(leftHandSide, "Incompatible types. Attempt to multiply " + leftType + " with " + rightType);
                    return null;
            }
        } else if (leftType == STRING) {
            switch (rightType) {
                case INTEGER:
                    return new Lexeme(STRING, stringMultiplication(leftHandSide.getStringValue(), rightHandSide.getIntValue()), leftHandSide.getLineNumber());
                case DOUBLE:
                    int temp1 = (int) rightHandSide.getDoubleValue();
                    String result = stringMultiplication(leftHandSide.getStringValue(), temp1);
                    //This is gonna be a funny way to do this
                    String temp2 = "" + rightHandSide.getDoubleValue();
                    int decimalPosition = 0;
                    for (int i = 0; i < temp2.length(); i++) {
                        if (temp2.charAt(i) == '.') {
                            decimalPosition = i;
                        }
                    }
                    if (temp2.charAt(decimalPosition + 1) == '0' && temp2.length() == (decimalPosition + 2)) {
                    } else {
                        int value = (int) rightHandSide.getDoubleValue() + 1;
                        double difference = value - rightHandSide.getDoubleValue();
                        double factor = difference * leftHandSide.getStringValue().length();
                        int factorer = (int) factor;
                        result = result + stringMultiplication(leftHandSide.getStringValue(), factorer);
                    }
                    return new Lexeme(STRING, result, leftHandSide.getLineNumber());
                case STRING:
                    return new Lexeme(STRING, leftHandSide.getStringValue() + rightHandSide.getStringValue().length(), leftHandSide.getLineNumber());
                case TRUE:
                    return new Lexeme(STRING, leftHandSide.getStringValue() + "true", leftHandSide.getLineNumber());
                case FALSE:
                    return new Lexeme(STRING, leftHandSide.getStringValue() + "false", leftHandSide.getLineNumber());
                default:
                    Speaking.error(leftHandSide, "Incompatible types. Attempt to multiply " + leftType + " with " + rightType);
                    return null;
            }
        } else {
            Speaking.error(leftHandSide, "Incompatible types. Attempt to multiply " + leftType + " with " + rightType);
            return null;
        }
    }

    private String stringMultiplication(String input, int factor) {
        while (factor != 0) {
            input = input + input;
            factor--;
        }
        return input;
    }

    private Lexeme evalOver(Lexeme tree, Environment environment) {
        Lexeme leftHandSide = eval(tree.getLeft(), environment);
        Lexeme rightHandSide = eval(tree.getRight(), environment);

        TokenType leftType = leftHandSide.getType();
        TokenType rightType = rightHandSide.getType();

        if (leftType == INTEGER) {
            switch (rightType) {
                case INTEGER:
                    return new Lexeme(INTEGER, leftHandSide.getIntValue() + rightHandSide.getIntValue(), leftHandSide.getLineNumber());
                case DOUBLE:
                    int tempValue = (int) rightHandSide.getDoubleValue();
                    return new Lexeme(INTEGER, leftHandSide.getIntValue() + tempValue, leftHandSide.getLineNumber());
                case STRING:
                    return new Lexeme(INTEGER, leftHandSide.getIntValue() + rightHandSide.getStringValue().length(), leftHandSide.getLineNumber());
                case TRUE:
                    return new Lexeme(INTEGER, leftHandSide.getIntValue() + 4, leftHandSide.getLineNumber());
                case FALSE:
                    return new Lexeme(INTEGER, leftHandSide.getIntValue() + 5, leftHandSide.getLineNumber());
                default:
                    Speaking.error(leftHandSide, "Incompatible types. Attempt to divide " + leftType + " by " + rightType);
                    return null;
            }
        } else if (leftType == DOUBLE) {
            switch (rightType) {
                case INTEGER:
                    return new Lexeme(DOUBLE, leftHandSide.getDoubleValue() + rightHandSide.getIntValue(), leftHandSide.getLineNumber());
                case DOUBLE:
                    return new Lexeme(DOUBLE, leftHandSide.getDoubleValue() + rightHandSide.getDoubleValue(), leftHandSide.getLineNumber());
                case STRING:
                    return new Lexeme(DOUBLE, leftHandSide.getDoubleValue() + rightHandSide.getStringValue().length(), leftHandSide.getLineNumber());
                case TRUE:
                    return new Lexeme(DOUBLE, leftHandSide.getDoubleValue() + 4.0, leftHandSide.getLineNumber());
                case FALSE:
                    return new Lexeme(DOUBLE, leftHandSide.getDoubleValue() + 5.0, leftHandSide.getLineNumber());
                default:
                    Speaking.error(leftHandSide, "Incompatible types. Attempt to divide " + leftType + " by " + rightType);
                    return null;
            }
        } else if (leftType == STRING) {
            switch (rightType) {
                case INTEGER:
                    return new Lexeme(STRING, leftHandSide.getStringValue() + rightHandSide.getIntValue(), leftHandSide.getLineNumber());
                case DOUBLE:
                    return new Lexeme(STRING, leftHandSide.getStringValue() + rightHandSide.getDoubleValue(), leftHandSide.getLineNumber());
                case STRING:
                    return new Lexeme(STRING, leftHandSide.getStringValue() + rightHandSide.getStringValue().length(), leftHandSide.getLineNumber());
                case TRUE:
                    return new Lexeme(STRING, leftHandSide.getStringValue() + "true", leftHandSide.getLineNumber());
                case FALSE:
                    return new Lexeme(STRING, leftHandSide.getStringValue() + "false", leftHandSide.getLineNumber());
                default:
                    Speaking.error(leftHandSide, "Incompatible types. Attempt to divide " + leftType + " by " + rightType);
                    return null;
            }
        } else {
            Speaking.error(leftHandSide, "Incompatible types. Attempt to divide " + leftType + " by " + rightType);
            return null;
        }
    }

    private Lexeme evalIncrease(Lexeme tree, Environment environment) {
        return null;
    }

    private Lexeme evalDecrease(Lexeme tree, Environment environment) {
        return null;
    }

    private Lexeme evalVariableDeclaration(Lexeme lexeme, Environment environment) {

    }

    private Lexeme evalVariableAssignment(Lexeme lexeme, Environment environment) {

    }

    private Lexeme evalComparator(Lexeme comparator, Environment environment) {
        Lexeme left = comparator.getLeft();
        Lexeme right = comparator.getRight();

        if (comparator.getType() == GREATER) {
            if (left.getType() == INTEGER) {
                switch (right.getType()) {
                    case INTEGER:
                    case DOUBLE:
                    case STRING:
                    case TRUE:
                    case FALSE:
                    default:
                        Speaking.error(left, "Incompatible types. Attempt to compare " + left + " to " + right);
                }
            } else if (left.getType() == DOUBLE) {
                switch (right.getType()) {
                    case INTEGER:
                    case DOUBLE:
                    case STRING:
                    case TRUE:
                    case FALSE:
                    default:
                        Speaking.error(left, "Incompatible types. Attempt to compare " + left + " to " + right);
                }
            } else if (left.getType() == STRING) {
                switch (right.getType()) {
                    case INTEGER:
                    case DOUBLE:
                    case STRING:
                    case TRUE:
                    case FALSE:
                    default:
                        Speaking.error(left, "Incompatible types. Attempt to compare " + left + " to " + right);
                }
            } else {
                Speaking.error(left, "Incompatible types. Attempt to compare " + left + " to " + right);
            }
        } else if (comparator.getType() == LESS) {
        } else {
            //comparator type will be EQUAL
            return new Lexeme();
        }
    }

    private Lexeme evalFunctionDeclaration() {

    }

    private Lexeme evalArrayDeclaration() {

    }

    private Lexeme evalArrayAssignment() {

    }

    private Lexeme evalFunctionCall() {

    }

    private Lexeme evalWhileLoop() {

    }

    private Lexeme evalUntilLoop() {

    }

    private Lexeme evalArrayCall() {

    }

    private Lexeme evalReturn() {

    }

    private Lexeme evalConditional() {

    }
}
