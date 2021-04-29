# Welcome to the Speaking Language!

This project was created by Eric Wang as part of the Honors Programming Languages curriculum of Spring 2021

## Introduction

The speaking language is primarily designed to resemble normal speaking compared to traditional coding languages such as
Java. Many of the rules in Speaking are self-explanatory and inherently easy to remember.

## The Basics

### General Syntax

Similar to the semicolon `;` used in Java, Speaking uses the bang `!` to close off certain statements. Braces `{ }` used
in Java will also be used in Speaking.

**Important: Please read carefully about usage of the bang `!`, only certain statements and situations require the
bang `!`**

`show` is the Speaking equivalent of `return` in Java. Every `show` statement must be followed by a bang `!`.

`nothing` is the Speaking equivalent of `null` in Java.

Additionally, `and` will be used instead of commas to separate elements.

To comment, begin and end with `btw`

Example:

```
btw I am commenting right now btw

```

### Variables

Speaking supports integers, doubles, booleans, and Strings, but they are called "numbers", "decimals", "facts", and "
Words", respectively.

"numbers", "decimals", "facts", and "Words" can be singular or plural, depending on which fits the context. Both work.

When using "facts" (booleans), "true" and "false" are replaced with "yep" and "nope"

When declaring variables, use "my" + [variable type] + [variable name] + "!"

To initialize a variable, simply use "my" + [variable type] + [variable name] + "has" [definition] "!"

To assign a value to a previously declared variable, use [variable name] + "has" [definition] "!"

Definitions are not limited to primaries, they can also be expression. Just make sure that the definition matches the
variable type!

Example:

```
my word testVariable!
testVariable is "Hello"!

the Word testVariable2 is "Hello"!
```

To call a variable, simply use the variable name:

`show testVariable!`

### Functions

#### Defining Functions

Defining a function follows the form "my function" + [function name] + "accepts" + "(" + [function parameter(s)] + ")"
+ [block]

For sake of simplicity, return types of functions in Speaking do not need to be specified.

##### How to Enumerate Function Parameters

Function parameters work a bit differently than Java conventions.

Every parameter uses the standard [parameterType] + [IDENTIFIER], such as `number inputInteger` or `word inputString`

To have more than one parameter, add `and` after each subsequent parameter.

Example:

```
my function exampleFunction accepts (fact x and fact y and decimal z){

}
```

*This is very important:* Even if your desired function takes in nothing, you still must include "nothing" + (a random
identifier) as a parameter

The "nothing" + (a random identifier) won't ever be used or evaluated in any meaningful way, and only serves as a
placeholder.

Here's an example of a function that doesn't take in anything:

```
my function functionThatDoesntTakeInAnything accepts (nothing x){

}
```

Or another example:

```
my function functionThatDoesntTakeInAnything accepts (nothing literallyAnyIdentiferName){

}
```

Function Definition Example:

```
my function testFunction accepts (number inputInteger){
    show inputInteger times 2!
} 
```

The console would return `inputInteger * 2`

#### Calling Functions

For functions that don't return anything (Java's `void`), use `nothing`. Similarly, for functions that don't require any
input, also use `nothing`.

Adding on to the above example, this is what defining a function would ultimately look like:

```
my function testFunction accepts (number inputInteger){
    show inputInteger times 2!
}
my number x has 2!

```

The console would return `4`.

### Control Flow

The Speaking Language mainly uses 2 loops: `while` and`until`.
`while` works like the while loop in Java, and `until` runs until the conditions are met.

Use [loop type] + "(" + [conditions] + ")" + [block]

Example of while loop:

```
my number x is 5!
while x is greater than 2 {
    announce x!
    x is x minus 1!
}
```

The console would print `543`

Example of until loop:

```
number x is 5!
until x is equal to 2 {
    show x!
    x is x minus 1!
}
```

The console would print `543`

### The Block

So far, the block has been shown in a few examples. But what is it?

It's very simple. A block is a group of statements wrapped up by a pair of braces. So, it looks like this:

"{" + [statementList] + "}"

Just like in Java, blocks in Speaking represent the portion of code that should be executed when called for.

In this language, blocks are used after function definitions and while/until loops.

### Operators

Operators in Speaking serve the same purpose as Java's operators, but with different names:

| Java Operator | Speaking Operator |
|------|------|
|`+`|`plus`|
|`-`|`minus`|
|`*`|`times`|
|`/`|`over`|
|++|`increase`|
|--| `decrease`|

Note that *Speaking does not have Java equivalents of `+=`, `-=`, and etc.*

#### Operations between different variable types

As a general rule of thumb, when doing binary operations on two elements, Speaking will almost always return something
with the variable type of the left element.

The only exception to this rule is the boolean. Placing booleans to the left of an operation does not guarantee that a
boolean will be returned.

Here is the link to the Google Spreadsheet that covers all cross-type
operations: https://docs.google.com/spreadsheets/d/1t3mERXUX27_Jo9dQh8x9fen9fFuNX5TnvXoyhh5_0qk/edit#gid=756640360

### Comparators

Comparators in Speaking are also closely similar to Java comparators:

| Java Operator | Speaking Operator |
|------|------|
|`>`|`is greater than`|
|`<`|`is less than`|
|`==`|`is equal to`|
|`!`|`not`|

### Built-in functions

The table below lists some additional built-in functions.

| Function | Description |
|-----|-----|
|`announce STRING`| Equivalent to Java's `System.out.print`|
|`announce loudly STRING`|Equivalent to Java's `System.out.println`|
|`cut(STRING and LOWER_INDEX to UPPER_INDEX)`|Equivalent to Java's `String.substring()`|
|`length(STRING)!`|Equivalent to Java's `String.length()`|
|`I want to have fun`|Returns link https://www.boredbutton.com/|