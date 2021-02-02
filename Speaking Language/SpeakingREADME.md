# Welcome to the Speaking Language!

This project was created by Eric Wang as part of the Honors Programming Languages curriculum of Spring 2021

## Introduction
The speaking language is primarily designed to more resemble normal speaking compared to traditional coding languages such as Java

## The Basics

### General Syntax
Similar to the semicolon `;` used in Java, Speaking uses the period `.`
Braces `{ }` used in Java will also be used in Speaking.

Additionally, "and" will be used instead of commas to separate elements.

To comment, begin with "btw, ".
For multiple lines of comments, begin with "psst, " and end with "alright?"
Example:

```
btw, I am commenting right now.

psst, 
This is another way to comment.
alright?
```
### Variables
Speaking supports integers, doubles, booleans, and Strings, but they are called "numbers", "decimals", "facts", and "Words", respectively.\

When using "facts" (booleans), "true" and "false" are replaced with "yep" and "nope"

"numbers", "decimals", "facts", and "Words" can be singular or plural, depending on which fits the context. Both work.

When declaring variables, use "The" + [variable type] + [variable name]. To set the variable, simply use "is" + [definition]


Example:
```
the Word testVariable.
testVariable is "Hello".

The Word testVariable2 is "Hello".
```
To call a variable, simply use the variable name:

`announce testVariable.`


### Functions
In general, functions follow the form "My function" + [function name] + "accepts" + ([function parameter(s)]) + "and distributes" + [return type(s)]

To `return`, simply type `I'll end with`

Example:
```
My function testFunction accepts (number inputInteger) and distributes (numbers){
    I'll end with inputInteger times 2.
} 
```
The console would return `inputInteger * 2`
#### Functions with no return type
For functions that don't return anything (Java's `void`), use `nothing`.
Similarly, for functions that don't require any input, also use `nothing`
Example:
```
My function testFunction2 accepts (nothing) and distributes (nothing){
    announce "This function doesn't return anything!".
} 
```
The console would print `This function doesn't return anything!`

#### Functions with multiple return types
Although functions can only return one element at a time, they can have multiple return types (**subject to change if not possible**)

Example:
```
My function testFunction accepts (nothing) and distributes (numbers and Words){
    I'll end with 5 times 5.
} 
```
In the above example, Speaking automatically detects that the return type is a `number` and returns a `number`.

The console would return `25`

### Control Flow
The Speaking Language mainly uses 2 loops: `while` and`until`.
`while` works like the while loop in Java, and `until` runs until the conditions are met.

Use [loop type] + [conditions].

Example of while loop: 
```
number x is 5.
while x is greater than 2 {
    announce x.
    x is x minus 1.
}
```
The console would print `543`

Example of until loop:
```
number x is 5.
until x is equal to 2 {
    announce x.
    x is x minus 1.
}
```
The console would print `543`

### Operators
Operators in Speaking serve the same purpose as Java's operators, but with different names:

| Java Operator | Speaking Operator |
|------|------|
|`+`|`plus`|
|`-`|`minus`|
|`*`|`times`|
|`/`|`divided by`|
|`%`|`modulus`|
|++|`increase` or `increased`|
|--| `decrease` or `decreased`|


Note that *Speaking does not have Java equivalents of `+=`, `-=`, and etc*

#### Operations between different variable types

This will be further explored later on.

### Comparators
Comparators in Speaking are also closely similar to Java comparators:

| Java Operator | Speaking Operator |
|------|------|
|`>`|`is greater than`|
|`<`|`is less than`|
|`==`|`is equal to`|
|`>=`|`is greater than or equal to`|
|`<=`|`is less than or equal to`|
|`!>`|`is not greater than`|
|`!<`|`is not less than`|
|`!>=`|`is not greater than or equal to`|
|`!<=`|`is not less than or equal to`|
|`!=`|`is not equal to`|

### Collections
Speaking Language mainly uses ArrayLists as the way to store elements

### Built-in functions
The table below lists some additional useful functions. More functions will be introduced over time when necessary.

| Function | Description |
|-----|-----|
|`announce`| Equivalent to Java's `System.out.print`|
|`announce loudly`|Equivalent to Java's `System.out.println`|
|`cut my word [Word] from [lower index] to [upper index]`|Equivalent to Java's `String.substring()`|
|`word length of (Word)`|Equivalent to Java's `String.length()`|
|`convert()`|Takes in Java code and turns returns it in Speaking language|
|`I want to have fun`|Returns link https://www.boredbutton.com/|