package com.wang;

import java.util.ArrayList;

public class Environment {
    //instance variables
    private Environment parent;

    ArrayList<Lexeme> identifiers;
    ArrayList<Lexeme> values;

    //constructor
    public Environment(Environment parent) {
        this.parent = parent;
        this.identifiers = new ArrayList<>();
        this.values = new ArrayList<>();
        this.hashCode();

    }

    //public environment methods
    public void insert(Lexeme identifier, Lexeme value) {
        identifiers.add(identifier);
        values.add(value);
    }

    public void update(Lexeme target, Lexeme newValue) {
        for (int i = 0; i < identifiers.size(); i++) {
            if (identifiers.get(i).equals(target)) {
                values.set(i, newValue);
                return;
            }
        }
        if (parent != null) parent.update(target, newValue);
        else
            Speaking.error(target, "Variable " + target.toString() + " is undefined and therefore cannot be updated");
    }

    public Lexeme lookup(Lexeme target) {
        Lexeme value = softLookup(target);
        if (value != null) return value;

        Speaking.error(target, "variable " + target.toString() + " is undefined");
        return null;
    }

    //helper methods
    public Lexeme softLookup(Lexeme target) {
        for (int i = 0; i < identifiers.size(); i++) {
            if (identifiers.get(i).equals(target)) {
                return values.get(i);
            }
        }
        if (parent != null) return parent.softLookup(target);
        return null;
    }

    //toString
    public String toString() {
        String result = "";
        result += "Environment " + this.hashCode() + "\n" + "Parent ID: " + parent;
        for (int i = 0; i < identifiers.size(); i++) {
            result += "\n";
            result += identifiers.get(i).toString() + ": ";
            result += values.get(i).toString();
        }
        return result;
    }
}
