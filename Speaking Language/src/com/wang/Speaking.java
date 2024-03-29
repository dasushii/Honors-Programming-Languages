package com.wang;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Speaking {
    public static void main(String[] args) throws IOException {
        try {
            if (singlePathProvided(args)) runFile(args[0]);
            else {
                System.out.println("Usage: speaking [path to .speak file]");
                System.exit(64);
            }
        } catch (IOException exception) {
            throw new IOException(exception.toString());
        }
    }

    private static boolean singlePathProvided(String[] args) {
        return args.length == 1;
    }

    public static void runFile(String path) throws IOException {
        String sourceCode = getSourceCodeFromFile(path);
        run(sourceCode);
    }

    private static String getSourceCodeFromFile(String path) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        return new String(bytes, Charset.defaultCharset());
    }

    public static void run(String sourceCode) {
        Lexer lexer = new Lexer(sourceCode);
        ArrayList<Lexeme> lexemes = lexer.lex();
        Parser parser = new Parser(lexemes);
        //printLexemes(lexemes);
        Lexeme programParseTree = parser.program();
        //Lexeme.printTree(parser.program());

        Environment globalEnvironment = new Environment(null);
        Evaluator evaluator = new Evaluator();
        Lexeme programResult = evaluator.eval(programParseTree, globalEnvironment);

        System.out.println("Program result: " + programResult);
    }

    public static void printLexemes(ArrayList<Lexeme> lexemes) {
        for (Lexeme lexeme : lexemes) {
            System.out.println(lexeme);
        }
    }

    public static void error(int lineNumber, String message) {
        report(lineNumber, "", message);
    }

    public static void error(Lexeme lexeme, String message) {
        if (lexeme.getType() == TokenType.EOF) {
            report(lexeme.getLineNumber(), "at end of file", message);
        } else {
            report(lexeme.getLineNumber(), "at '" + lexeme + "'", message);
        }
    }

    public static void report(int lineNumber, String where, String message) {
        System.err.println("[line " + lineNumber + "] Error " + where + ": " + message);
    }
}
