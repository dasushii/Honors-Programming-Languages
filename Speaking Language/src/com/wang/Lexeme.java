package com.wang;

public class Lexeme {
    private final TokenType type;
    private final int lineNumber;

    private final String stringValue;
    private final Integer intValue;
    private final Double doubleValue;

    private Lexeme left;
    private Lexeme right;

    public Lexeme(TokenType type, int lineNumber) {
        this.type = type;
        this.lineNumber = lineNumber;
        this.stringValue = null;
        this.intValue = null;
        this.doubleValue = null;
    }

    public Lexeme(TokenType type, String stringValue, int lineNumber) {
        this.type = type;
        this.lineNumber = lineNumber;
        this.stringValue = stringValue;
        this.intValue = null;
        this.doubleValue = null;
    }

    public Lexeme(TokenType type, int intValue, int lineNumber) {
        this.type = type;
        this.lineNumber = lineNumber;
        this.stringValue = null;
        this.intValue = intValue;
        this.doubleValue = null;
    }

    public Lexeme(TokenType type, double doubleValue, int lineNumber) {
        this.type = type;
        this.lineNumber = lineNumber;
        this.stringValue = null;
        this.intValue = null;
        this.doubleValue = doubleValue;
    }

    public TokenType getType() {
        return type;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public String toString() {
        String result = "";
        result += this.type + " ";
        if (this.stringValue != null) result += '"' + this.stringValue + '"' + " ";
        else if (this.doubleValue != null) result += this.doubleValue + " ";
        else if (this.intValue != null) result += this.intValue + " ";
        result += "(line: " + this.lineNumber + ")";
        return result;
    }

    public void setLeft(Lexeme left) {
        this.left = left;
    }

    public void setRight(Lexeme right) {
        this.right = right;
    }

    public Lexeme getLeft() {
        return this.left;
    }

    public Lexeme getRight() {
        return this.right;
    }

    public String getStringValue() {
        return this.stringValue;
    }

    public int getIntValue() {
        return this.intValue;
    }

    public double getDoubleValue() {
        return this.doubleValue;
    }

    public boolean equals(Lexeme other) {
        return this.type == TokenType.IDENTIFIER && this.type == other.type && this.stringValue != null && this.stringValue.equals(other.stringValue);
    }

    public static void printTree(Lexeme root) {
        String printableTree = getPrintableTree(root, 1);
        System.out.println(printableTree);
    }

    private static String getPrintableTree(Lexeme root, int level) {
        String result = root.toString();
        StringBuilder spacer = new StringBuilder("\n");
        for (int i = 0; i < level; i++) {
            spacer.append("\t");
        }
        if (root.left != null) {
            result += spacer + "with left child: " + getPrintableTree(root.left, level + 1);
        }
        if (root.right != null) {
            result += spacer + "and right child: " + getPrintableTree(root.right, level + 1);
        }

        return result;
    }
}
