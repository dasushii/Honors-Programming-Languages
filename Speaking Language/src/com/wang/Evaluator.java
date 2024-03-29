package com.wang;

import java.util.ArrayList;

import static com.wang.TokenType.*;

public class Evaluator {
    public Lexeme eval(Lexeme tree, Environment environment) {
        Lexeme.printTree(tree);
        if (tree == null) return null;
        switch (tree.getType()) {
            case PROGRAM:
                if (tree.getLeft() != null) {
                    return eval(tree.getLeft(), environment);
                }
                return null;
            case COMMENT:
            case STATEMENT_LIST:
                return evalStatementList(tree, environment);
            case INTEGER:
            case DOUBLE:
            case STRING:
            case TRUE:
            case FALSE:
            case NULL:
                return tree;
            case IDENTIFIER:
                return evalPossibleVariableCall(tree, environment);
            case EQUAL:
            case LESS:
            case GREATER:
                return evalComparator(tree, environment);
            case PLUS:
            case MINUS:
            case TIMES:
            case OVER:
                return evalSimpleOperator(tree, environment);
            case FUNCTION:
                return evalFunctionDefinition(tree, environment);
            case STRING_TYPE:
            case INTEGER_TYPE:
            case DOUBLE_TYPE:
            case BOOLEAN_TYPE:
                return evalVariableDeclaration(tree, environment);
            case HAS:
                return evalVariableAssignment(tree, environment);
            case ANNOUNCE:
            case CUT:
            case LENGTH:
            case FUN:
            case FUNCTION_CALL:
                return evalFunctionCall(tree, environment);
            case WHILE:
                return evalWhileLoop(tree, environment);
            case UNTIL:
                return evalUntilLoop(tree, environment);
            case SHOW:
                return evalReturn(tree, environment);
            case IF:
                return evalConditional(tree, environment);
            case BLOCK:
                return evalBlock(tree, environment);
            case NOT:
                return evalNot(tree, environment);
            default:
                return null;
        }
    }

    private Lexeme evalStatementList(Lexeme tree, Environment environment) {
        if (tree.getRight() == null) {
            if (tree.getLeft() == null || tree.getLeft().getType() == NULL) {
                return null;
            }
            return eval(tree.getLeft(), environment);
        } else {
            if (tree.getLeft().getType() == NULL) {
                return evalStatementList(tree.getRight(), environment);
            } else {
                eval(tree.getLeft(), environment);
                return evalStatementList(tree.getRight(), environment);
            }
        }
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
        if (tree.getLeft().getType() == INTEGER) {
            return new Lexeme(INTEGER, tree.getLeft().getIntValue() + 1, tree.getLineNumber());
        } else {
            Speaking.error(tree, "Cannot increase type " + tree.getLeft());
            return null;
        }
    }

    private Lexeme evalDecrease(Lexeme tree, Environment environment) {
        if (tree.getLeft().getType() == INTEGER) {
            return new Lexeme(INTEGER, tree.getLeft().getIntValue() - 1, tree.getLineNumber());
        } else {
            Speaking.error(tree, "Cannot decrease type " + tree.getLeft());
            return null;
        }
    }

    private Lexeme evalVariableDeclaration(Lexeme variableType, Environment environment) {
        Lexeme id = variableType.getLeft();
        if (environment.identifiers.contains(id)) {
            Speaking.error(id, "Variable identifier " + id + " already in use. Please choose a different name");
            return null;
        }
        if (variableType.getRight() != null) {
            if (variableType.getType() == STRING_TYPE && eval(variableType.getRight(), environment).getType() != STRING) {
                Speaking.error(id, "Variable " + id.getStringValue() + " is not properly defined with the given type " + variableType);
            } else if (variableType.getType() == INTEGER_TYPE && eval(variableType.getRight(), environment).getType() != INTEGER) {
                Speaking.error(id, "Variable " + id.getStringValue() + " is not properly defined with the given type " + variableType);
            } else if (variableType.getType() == DOUBLE_TYPE && eval(variableType.getRight(), environment).getType() != DOUBLE) {
                Speaking.error(id, "Variable " + id.getStringValue() + " is not properly defined with the given type " + variableType);
            } else if (variableType.getType() == BOOLEAN_TYPE && eval(variableType.getRight(), environment).getType() != TRUE) {
                Speaking.error(id, "Variable " + id.getStringValue() + " is not properly defined with the given type " + variableType);
            } else if (variableType.getType() == BOOLEAN_TYPE && eval(variableType.getRight(), environment).getType() != FALSE) {
                Speaking.error(id, "Variable " + id.getStringValue() + " is not properly defined with the given type " + variableType);
            } else {
                environment.insert(id, eval(variableType.getRight(), environment));
            }
        } else {
            environment.insert(id, null);
        }
        return null;
    }

    private Lexeme evalVariableAssignment(Lexeme has, Environment environment) {
        Lexeme id = has.getLeft();
        Lexeme expression = has.getRight();
        boolean replaced = false;
        for (int i = 0; i < environment.identifiers.size(); i++) {
            if (environment.identifiers.get(i).getStringValue().equals(id.getStringValue())) {
                if (environment.values.get(i).getType() != eval(expression, environment).getType()) {
                    Speaking.error(id, "Variable " + id.getStringValue() + " is not properly assigned with the given type " + environment.values.get(i).getType());
                } else {
                    environment.identifiers.set(i, eval(expression, environment));
                    replaced = true;
                }
            }
        }
        if (!replaced) {
            Speaking.error(id, "Variable " + id + " is not properly declared or initialized");
        }
        return null;
    }

    private Lexeme evalComparator(Lexeme comparator, Environment environment) {
        Lexeme left = comparator.getLeft();
        Lexeme right = comparator.getRight();

        if (comparator.getType() == GREATER) {
            if (left.getType() == INTEGER) {
                switch (right.getType()) {
                    case INTEGER:
                        if (left.getIntValue() > right.getIntValue()) {
                            return new Lexeme(TRUE, left.getLineNumber());
                        } else {
                            return new Lexeme(FALSE, left.getLineNumber());
                        }
                    case DOUBLE:
                        if (left.getIntValue() > right.getDoubleValue()) {
                            return new Lexeme(TRUE, left.getLineNumber());
                        } else {
                            return new Lexeme(FALSE, left.getLineNumber());
                        }
                    case STRING:
                        if (left.getIntValue() > right.getStringValue().length()) {
                            return new Lexeme(TRUE, left.getLineNumber());
                        } else {
                            return new Lexeme(FALSE, left.getLineNumber());
                        }
                    case TRUE:
                        if (left.getIntValue() > 4) {
                            return new Lexeme(TRUE, left.getLineNumber());
                        } else {
                            return new Lexeme(FALSE, left.getLineNumber());
                        }
                    case FALSE:
                        if (left.getIntValue() > 5) {
                            return new Lexeme(TRUE, left.getLineNumber());
                        } else {
                            return new Lexeme(FALSE, left.getLineNumber());
                        }
                    default:
                        Speaking.error(left, "Incompatible types. Attempt to compare " + left + " to " + right);
                }
            } else if (left.getType() == DOUBLE) {
                switch (right.getType()) {
                    case INTEGER:
                        if (left.getDoubleValue() > right.getIntValue()) {
                            return new Lexeme(TRUE, left.getLineNumber());
                        } else {
                            return new Lexeme(FALSE, left.getLineNumber());
                        }
                    case DOUBLE:
                        if (left.getDoubleValue() > right.getDoubleValue()) {
                            return new Lexeme(TRUE, left.getLineNumber());
                        } else {
                            return new Lexeme(FALSE, left.getLineNumber());
                        }
                    case STRING:
                        if (left.getDoubleValue() > right.getStringValue().length()) {
                            return new Lexeme(TRUE, left.getLineNumber());
                        } else {
                            return new Lexeme(FALSE, left.getLineNumber());
                        }
                    case TRUE:
                        if (left.getDoubleValue() > 4.0) {
                            return new Lexeme(TRUE, left.getLineNumber());
                        } else {
                            return new Lexeme(FALSE, left.getLineNumber());
                        }
                    case FALSE:
                        if (left.getIntValue() > 5.0) {
                            return new Lexeme(TRUE, left.getLineNumber());
                        } else {
                            return new Lexeme(FALSE, left.getLineNumber());
                        }
                    default:
                        Speaking.error(left, "Incompatible types. Attempt to compare " + left + " to " + right);
                }
            } else if (left.getType() == STRING) {
                switch (right.getType()) {
                    case INTEGER:
                        if (left.getStringValue().length() > right.getIntValue()) {
                            return new Lexeme(TRUE, left.getLineNumber());
                        } else {
                            return new Lexeme(FALSE, left.getLineNumber());
                        }
                    case DOUBLE:
                        if (left.getStringValue().length() > right.getDoubleValue()) {
                            return new Lexeme(TRUE, left.getLineNumber());
                        } else {
                            return new Lexeme(FALSE, left.getLineNumber());
                        }
                    case STRING:
                        if (left.getStringValue().length() > right.getStringValue().length()) {
                            return new Lexeme(TRUE, left.getLineNumber());
                        } else {
                            return new Lexeme(FALSE, left.getLineNumber());
                        }
                    case TRUE:
                        if (left.getStringValue().length() > 4) {
                            return new Lexeme(TRUE, left.getLineNumber());
                        } else {
                            return new Lexeme(FALSE, left.getLineNumber());
                        }
                    case FALSE:
                        if (left.getStringValue().length() > 5) {
                            return new Lexeme(TRUE, left.getLineNumber());
                        } else {
                            return new Lexeme(FALSE, left.getLineNumber());
                        }
                    default:
                        Speaking.error(left, "Incompatible types. Attempt to compare " + left + " to " + right);
                }
            } else {
                Speaking.error(left, "Incompatible types. Attempt to compare " + left + " to " + right);
            }
        } else if (comparator.getType() == LESS) {
            if (left.getType() == INTEGER) {
                switch (right.getType()) {
                    case INTEGER:
                        if (left.getIntValue() < right.getIntValue()) {
                            return new Lexeme(TRUE, left.getLineNumber());
                        } else {
                            return new Lexeme(FALSE, left.getLineNumber());
                        }
                    case DOUBLE:
                        if (left.getIntValue() < right.getDoubleValue()) {
                            return new Lexeme(TRUE, left.getLineNumber());
                        } else {
                            return new Lexeme(FALSE, left.getLineNumber());
                        }
                    case STRING:
                        if (left.getIntValue() < right.getStringValue().length()) {
                            return new Lexeme(TRUE, left.getLineNumber());
                        } else {
                            return new Lexeme(FALSE, left.getLineNumber());
                        }
                    case TRUE:
                        if (left.getIntValue() < 4) {
                            return new Lexeme(TRUE, left.getLineNumber());
                        } else {
                            return new Lexeme(FALSE, left.getLineNumber());
                        }
                    case FALSE:
                        if (left.getIntValue() < 5) {
                            return new Lexeme(TRUE, left.getLineNumber());
                        } else {
                            return new Lexeme(FALSE, left.getLineNumber());
                        }
                    default:
                        Speaking.error(left, "Incompatible types. Attempt to compare " + left + " to " + right);
                        return null;
                }
            } else if (left.getType() == DOUBLE) {
                switch (right.getType()) {
                    case INTEGER:
                        if (left.getDoubleValue() < right.getIntValue()) {
                            return new Lexeme(TRUE, left.getLineNumber());
                        } else {
                            return new Lexeme(FALSE, left.getLineNumber());
                        }
                    case DOUBLE:
                        if (left.getDoubleValue() < right.getDoubleValue()) {
                            return new Lexeme(TRUE, left.getLineNumber());
                        } else {
                            return new Lexeme(FALSE, left.getLineNumber());
                        }
                    case STRING:
                        if (left.getDoubleValue() < right.getStringValue().length()) {
                            return new Lexeme(TRUE, left.getLineNumber());
                        } else {
                            return new Lexeme(FALSE, left.getLineNumber());
                        }
                    case TRUE:
                        if (left.getDoubleValue() < 4.0) {
                            return new Lexeme(TRUE, left.getLineNumber());
                        } else {
                            return new Lexeme(FALSE, left.getLineNumber());
                        }
                    case FALSE:
                        if (left.getIntValue() < 5.0) {
                            return new Lexeme(TRUE, left.getLineNumber());
                        } else {
                            return new Lexeme(FALSE, left.getLineNumber());
                        }
                    default:
                        Speaking.error(left, "Incompatible types. Attempt to compare " + left + " to " + right);
                        return null;
                }
            } else if (left.getType() == STRING) {
                switch (right.getType()) {
                    case INTEGER:
                        if (left.getStringValue().length() < right.getIntValue()) {
                            return new Lexeme(TRUE, left.getLineNumber());
                        } else {
                            return new Lexeme(FALSE, left.getLineNumber());
                        }
                    case DOUBLE:
                        if (left.getStringValue().length() < right.getDoubleValue()) {
                            return new Lexeme(TRUE, left.getLineNumber());
                        } else {
                            return new Lexeme(FALSE, left.getLineNumber());
                        }
                    case STRING:
                        if (left.getStringValue().length() < right.getStringValue().length()) {
                            return new Lexeme(TRUE, left.getLineNumber());
                        } else {
                            return new Lexeme(FALSE, left.getLineNumber());
                        }
                    case TRUE:
                        if (left.getStringValue().length() < 4) {
                            return new Lexeme(TRUE, left.getLineNumber());
                        } else {
                            return new Lexeme(FALSE, left.getLineNumber());
                        }
                    case FALSE:
                        if (left.getStringValue().length() < 5) {
                            return new Lexeme(TRUE, left.getLineNumber());
                        } else {
                            return new Lexeme(FALSE, left.getLineNumber());
                        }
                    default:
                        Speaking.error(left, "Incompatible types. Attempt to compare " + left + " to " + right);
                        return null;
                }
            } else {
                Speaking.error(left, "Incompatible types. Attempt to compare " + left + " to " + right);
                return null;
            }
        } else if (comparator.getType() == EQUAL) {
            if (left.getType() == INTEGER) {
                switch (right.getType()) {
                    case INTEGER:
                        if (left.getIntValue() == right.getIntValue()) {
                            return new Lexeme(TRUE, left.getLineNumber());
                        } else {
                            return new Lexeme(FALSE, left.getLineNumber());
                        }
                    case DOUBLE:
                        if (left.getIntValue() == right.getDoubleValue()) {
                            return new Lexeme(TRUE, left.getLineNumber());
                        } else {
                            return new Lexeme(FALSE, left.getLineNumber());
                        }
                    case STRING:
                        if (left.getIntValue() == right.getStringValue().length()) {
                            return new Lexeme(TRUE, left.getLineNumber());
                        } else {
                            return new Lexeme(FALSE, left.getLineNumber());
                        }
                    case TRUE:
                        if (left.getIntValue() == 4) {
                            return new Lexeme(TRUE, left.getLineNumber());
                        } else {
                            return new Lexeme(FALSE, left.getLineNumber());
                        }
                    case FALSE:
                        if (left.getIntValue() == 5) {
                            return new Lexeme(TRUE, left.getLineNumber());
                        } else {
                            return new Lexeme(FALSE, left.getLineNumber());
                        }
                    default:
                        Speaking.error(left, "Incompatible types. Attempt to compare " + left + " to " + right);
                        return null;
                }
            } else if (left.getType() == DOUBLE) {
                switch (right.getType()) {
                    case INTEGER:
                        if (left.getDoubleValue() == right.getIntValue()) {
                            return new Lexeme(TRUE, left.getLineNumber());
                        } else {
                            return new Lexeme(FALSE, left.getLineNumber());
                        }
                    case DOUBLE:
                        if (left.getDoubleValue() == right.getDoubleValue()) {
                            return new Lexeme(TRUE, left.getLineNumber());
                        } else {
                            return new Lexeme(FALSE, left.getLineNumber());
                        }
                    case STRING:
                        if (left.getDoubleValue() == right.getStringValue().length()) {
                            return new Lexeme(TRUE, left.getLineNumber());
                        } else {
                            return new Lexeme(FALSE, left.getLineNumber());
                        }
                    case TRUE:
                        if (left.getDoubleValue() == 4.0) {
                            return new Lexeme(TRUE, left.getLineNumber());
                        } else {
                            return new Lexeme(FALSE, left.getLineNumber());
                        }
                    case FALSE:
                        if (left.getIntValue() == 5.0) {
                            return new Lexeme(TRUE, left.getLineNumber());
                        } else {
                            return new Lexeme(FALSE, left.getLineNumber());
                        }
                    default:
                        Speaking.error(left, "Incompatible types. Attempt to compare " + left + " to " + right);
                        return null;
                }
            } else if (left.getType() == STRING) {
                switch (right.getType()) {
                    case INTEGER:
                        if (left.getStringValue().length() == right.getIntValue()) {
                            return new Lexeme(TRUE, left.getLineNumber());
                        } else {
                            return new Lexeme(FALSE, left.getLineNumber());
                        }
                    case DOUBLE:
                        if (left.getStringValue().length() == right.getDoubleValue()) {
                            return new Lexeme(TRUE, left.getLineNumber());
                        } else {
                            return new Lexeme(FALSE, left.getLineNumber());
                        }
                    case STRING:
                        if (left.getStringValue().length() == right.getStringValue().length()) {
                            return new Lexeme(TRUE, left.getLineNumber());
                        } else {
                            return new Lexeme(FALSE, left.getLineNumber());
                        }
                    case TRUE:
                        if (left.getStringValue().length() == 4) {
                            return new Lexeme(TRUE, left.getLineNumber());
                        } else {
                            return new Lexeme(FALSE, left.getLineNumber());
                        }
                    case FALSE:
                        if (left.getStringValue().length() == 5) {
                            return new Lexeme(TRUE, left.getLineNumber());
                        } else {
                            return new Lexeme(FALSE, left.getLineNumber());
                        }
                    default:
                        Speaking.error(left, "Incompatible types. Attempt to compare " + left + " to " + right);
                }
            } else {
                Speaking.error(left, "Incompatible types. Attempt to compare " + left + " to " + right);
                return null;
            }
        }
        Speaking.error(left, "Incompatible types. Attempt to compare " + left + " to " + right);
        return null;
    }

    private Lexeme evalFunctionDefinition(Lexeme function, Environment environment) {
        Lexeme id = function.getLeft();
        if (environment.identifiers.contains(id)) {
            Speaking.error(id, "Function " + id + " already exists.");
            return null;
        }
        Lexeme closure = function.getRight();
        environment.insert(id, closure);
        return null;
    }

    private Lexeme evalFunctionCall(Lexeme lexeme, Environment environment) {
        switch (lexeme.getType()) {
            case FUNCTION_CALL:
                return evalActualFunctionCall(lexeme, environment);
            case ANNOUNCE:
                if (lexeme.getLeft() != null) {
                    System.out.println(lexeme.getRight().getStringValue().toUpperCase());
                } else {
                    System.out.println(lexeme.getRight().getStringValue());
                }
                return null;
            case LENGTH:
                return new Lexeme(INTEGER, lexeme.getLeft().getStringValue().length(), lexeme.getLineNumber());
            case FUN:
                System.out.println("https://www.boredbutton.com/");
                return null;
            case CUT:
                String s = lexeme.getLeft().getStringValue();
                int lower = lexeme.getRight().getLeft().getIntValue();
                int upper = lexeme.getRight().getRight().getIntValue();
                return new Lexeme(STRING, s.substring(lower, upper), lexeme.getLineNumber());
        }
        return null;
    }

    private Lexeme evalActualFunctionCall(Lexeme functionCall, Environment environment) {
        Lexeme id = functionCall.getLeft();
        int index = -1;
        for (int i = 0; i < environment.identifiers.size(); i++) {
            if (environment.identifiers.get(i).getStringValue().equals(id.getStringValue())) {
                index = i;
            }
        }
        if (index == -1) {
            Speaking.error(functionCall, "Invalid function call. Function " + id.getStringValue() + " does not exist.");
            return null;
        }
        Lexeme closure = environment.values.get(index);
        ArrayList<Lexeme> argList = getList(functionCall.getRight());
        ArrayList<Lexeme> typeList = getList(closure.getLeft());
        if (argList.size() != typeList.size()) {
            Speaking.error(functionCall, "Invalid function call. Arguments do not match defined parameters.");
            return null;
        }
        for (int i = 0; i < argList.size(); i++) {
            if (typeList.get(i).getLeft().getType() == STRING_TYPE && eval(argList.get(i), environment).getType() != STRING) {
                Speaking.error(functionCall, "Invalid function call. Arguments do not match defined parameters.");
                return null;
            } else if (typeList.get(i).getLeft().getType() == INTEGER_TYPE && eval(argList.get(i), environment).getType() != INTEGER) {
                Speaking.error(functionCall, "Invalid function call. Arguments do not match defined parameters.");
                return null;
            } else if (typeList.get(i).getLeft().getType() == BOOLEAN_TYPE && eval(argList.get(i), environment).getType() != TRUE) {
                if (eval(argList.get(i), environment).getType() != FALSE) {
                    Speaking.error(functionCall, "Invalid function call. Arguments do not match defined parameters.");
                    return null;
                }
            } else if (typeList.get(i).getLeft().getType() == DOUBLE_TYPE && eval(argList.get(i), environment).getType() != DOUBLE) {
                Speaking.error(functionCall, "Invalid function call. Arguments do not match defined parameters.");
                return null;
            } else if (typeList.get(i).getLeft().getType() == NULL && eval(argList.get(i), environment).getType() != NULL) {
                Speaking.error(functionCall, "Invalid function call. Arguments do not match defined parameters.");
                return null;
            }
        }
        Environment tempEnvironment = new Environment(environment);
        for (int i = 0; i < argList.size(); i++) {
            tempEnvironment.insert(typeList.get(i), eval(argList.get(i), environment));
        }
        ArrayList<String> identifierList = getStringList(tempEnvironment.identifiers);
        ArrayList<String> parentIdentifierList = getStringList(environment.identifiers);
        for (int i = 0; i < parentIdentifierList.size(); i++) {
            if (!identifierList.contains(parentIdentifierList.get(i))) {
                tempEnvironment.insert(environment.identifiers.get(i), environment.values.get(i));
            }
        }
        Lexeme block = closure.getRight();
        return eval(block, tempEnvironment);
    }

    private Lexeme evalWhileLoop(Lexeme whileLexeme, Environment environment) {
        boolean pass = false;
        if (eval(whileLexeme.getLeft(), environment).getType() == TRUE) {
            pass = true;
        }
        while (pass) {
            evalBlock(whileLexeme.getLeft(), environment);
            if (eval(whileLexeme.getLeft(), environment).getType() == FALSE) {
                pass = false;
            }
        }
        return null;
    }

    private Lexeme evalUntilLoop(Lexeme until, Environment environment) {
        boolean pass = false;
        if (eval(until.getLeft(), environment).getType() == TRUE) {
            pass = true;
        }
        while (!pass) {
            evalBlock(until.getLeft(), environment);
            if (eval(until.getLeft(), environment).getType() == TRUE) {
                pass = true;
            }
        }
        return null;
    }


    private Lexeme evalReturn(Lexeme returnLexeme, Environment environment) {
        return eval(returnLexeme.getLeft(), environment);
    }

    private Lexeme evalConditional(Lexeme ifLexeme, Environment environment) {
        if (eval(ifLexeme.getRight(), environment).getType() == TRUE) {
            return evalBlock(ifLexeme.getRight().getLeft(), environment);
        } else if (ifLexeme.getRight().getRight() != null) {
            return evalBlock(ifLexeme.getRight().getRight(), environment);
        }
        return null;
    }

    private Lexeme evalBlock(Lexeme block, Environment environment) {
        return evalStatementList(block.getLeft(), environment);
    }

    private Lexeme evalPossibleVariableCall(Lexeme id, Environment environment) {
        for (int i = 0; i < environment.identifiers.size(); i++) {
            if (environment.identifiers.get(i).getStringValue().equals(id.getStringValue())) {
                return environment.values.get(i);
            }
        }
        return id;
    }

    private ArrayList<Lexeme> getList(Lexeme list) {
        ArrayList<Lexeme> result = new ArrayList<>();
        if (list.getLeft() != null) {
            result.add(list.getLeft());
        }
        if (list.getRight() != null) {
            getList(list.getRight());
        }
        return result;
    }

    private ArrayList<String> getStringList(ArrayList<Lexeme> identifierList) {
        ArrayList<String> result = new ArrayList<>();
        for (int i = 0; i < identifierList.size(); i++) {
            result.add(identifierList.get(i).getStringValue());
        }
        return result;
    }

    private Lexeme evalNot(Lexeme not, Environment environment) {
        if (eval(not.getLeft(), environment).getType() == TRUE) {
            return new Lexeme(FALSE, not.getLineNumber());
        } else if (eval(not.getLeft(), environment).getType() == FALSE) {
            return new Lexeme(TRUE, not.getLineNumber());
        } else {
            Speaking.error(not, "Cannot use unary NOT on non-Boolean expressions");
            return null;
        }
    }
}
