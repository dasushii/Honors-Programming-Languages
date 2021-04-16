package com.wang;

import static com.wang.TokenType.*;


public class EnvironmentTester {
    public static void main(String[] args) {
        Environment test1 = new Environment(null);
        Environment test2 = new Environment(test1);
        Environment test3 = new Environment(test2);

        Lexeme a = new Lexeme(IDENTIFIER, "a", 1);
        Lexeme b = new Lexeme(IDENTIFIER, "b", 1);
        Lexeme c = new Lexeme(IDENTIFIER, "c", 1);

        Lexeme integer = new Lexeme(INTEGER, 10, 1);
        Lexeme decimal = new Lexeme(DOUBLE, 10.1, 1);
        Lexeme bool1 = new Lexeme(TRUE, 1);
        Lexeme bool2 = new Lexeme(FALSE, 1);
        Lexeme str = new Lexeme(STRING, "this is a test string", 1);

        test1.insert(a, integer);
        test1.update(a, decimal);
        test2.insert(b, decimal);
        test3.insert(c, bool1);
        test3.insert(a, bool2);
        test2.insert(b, str);

        System.out.println(test1);
        System.out.println(test2);
        System.out.println(test3);

        System.out.println(test1.lookup(a));
    }
}
