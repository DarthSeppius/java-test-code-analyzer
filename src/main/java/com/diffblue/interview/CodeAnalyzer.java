package com.diffblue.interview;

import java.util.Set;

/**
 * An interface representing code analysis functions
 */
public interface CodeAnalyzer {
    /**
     * Runs the given test and returns the covered lines of code
     * @param test to run
     * @return the covered lines of code
     */
    Set<CodeLine> runTest(CodeTest test);

    /**
     * Run all tests and returns the covered lines of code
     * @param tests to run
     * @return the covered lines of code
     */
    Set<CodeLine> runTestSuite(Set<CodeTest> tests);

    /**
     * Run all tests and returns the names of all tests that cover something not
     * covered by other tests
     * @param tests to run
     * @return the set of unique tests
     */
    Set<CodeTest> uniqueTests(Set<CodeTest> tests);
}
