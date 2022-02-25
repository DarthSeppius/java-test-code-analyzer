Test Coverage Analysis Challenge
============

This project contains an interview question designed to assess your Java
coding ability.

Please note that we will look as much at readability, structure, documentation
and testing as we will at accuracy and correctness. Show us how good you are!

This project contains some basic interfaces which represent Java source code and
coverage of that source code by tests. The goal of the program will be 
to understand which tests cover which lines of the source code.

- `CodeLine` represents a line of Java code in a file
- `CodeClass` represents a Java class or interface file
- `CodeAnalyzer` represents the coverage analysis engine
- `CodeTest` represents a Java test

Question 1
------
Write a runnable Java program which takes a path to a Java source file and prints out all lines
of the file with an indication of whether each line is covered by tests. Your program should
make use of implementations of the above-mentioned interfaces.

We do not expect you to actually execute the Java source file that is analyzed by your program
to determine the test coverage - instead allow your "analysis" to say that a random set of lines
is covered, but ensure that this random set of lines is consistent between program invocations.

Question 2
------
Ensure that `CodeAnalyzer.runTestSuite()` only produces _unique_ CodeLine elements.

For example, if `TestA` covers lines 1 and 2 of a file, and `TestB` covers lines 2 and 3 of that file,
running `CodeAnalyzer.runTestSuite()` on these two tests should result in an output that contains
precisely one `CodeLine` each for lines 1, 2 and 3.

Question 3
------
Add a function to the analyzer called `uniqueTests()` that returns all tests
that _cover something not covered by other tests_.

For example, if `TestA` covers lines 1-3 of a file, `TestB` covers lines 2-4 of that file and `TestC`
covers lines 2-3 of that file, then `uniqueTests()` should return `TestA` (only test that covers
line 1) and `TestB` (only test that covers line 4), but not `TestC` (covers lines 2 and 3, which are
covered by other tests).

Question 4 
------
If you have not done so already, write JUnit tests for the code that you have written.
