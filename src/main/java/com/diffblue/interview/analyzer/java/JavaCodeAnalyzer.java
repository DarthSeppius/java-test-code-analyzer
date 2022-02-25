package com.diffblue.interview.analyzer.java;

import java.io.File;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.diffblue.interview.CodeAnalyzer;
import com.diffblue.interview.CodeLine;
import com.diffblue.interview.CodeTest;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

public class JavaCodeAnalyzer implements CodeAnalyzer {

    private final JavaClass classToAnalyze;

    private List<CodeTest> testsToExecute;

    public JavaCodeAnalyzer(File fileToAnalyse, File sourceFileForTests) {
        Preconditions.checkArgument(fileToAnalyse != null, "File to analyze cannot be null");

        classToAnalyze = new JavaClass(fileToAnalyse);
        initTests(sourceFileForTests, classToAnalyze);
    }

    private void initTests(File sourceFileForTests, JavaClass classToAnalyze) {
        testsToExecute = new JavaTestClass(sourceFileForTests, JUnitTestMarker.TEST_METHOD_ANNOTATION).getTests();

        testsToExecute.stream()
                .map(JavaTest.class::cast)
                .forEach(javaTest -> javaTest.setCoveredLines(calculateCoveredLines(
                        classToAnalyze.getLinesOfCode(),
                        javaTest.getTestLines())
                ));
    }

    public List<CodeTest> getTestsToExecute() {
        return ImmutableList.copyOf(testsToExecute);
    }

    public void changeTestClass(File sourceFileForTests) {
        Preconditions.checkArgument(sourceFileForTests != null, "File to analyze cannot be null");
        initTests(sourceFileForTests, classToAnalyze);
    }

    @Override
    public Set<CodeLine> runTest(CodeTest test) {
        return test.getCoveredLines();
    }

    @Override
    public Set<CodeLine> runTestSuite(Set<CodeTest> tests) {
        Optional<Set<CodeLine>> sets = tests.stream()
                .map(CodeTest::getCoveredLines)
                .reduce((codeLines, codeLines2) -> Stream.concat(codeLines.stream(), codeLines2.stream())
                        .collect(Collectors.toSet())
                );

        return sets.orElse(Collections.emptySet());

    }

    @Override
    public Set<CodeTest> uniqueTests(Set<CodeTest> tests) {
        Set<CodeTest> finalResult = new HashSet<>();

        tests.forEach(testCode -> {
            Set<CodeLine> coveredLines = new HashSet<>(testCode.getCoveredLines());

            // Get all other tests in order to execute the asymmetric difference one by one
            Set<CodeTest> allOtherTests = tests.stream()
                    .filter(test -> !test.getName().equals(testCode.getName()))
                    .collect(Collectors.toSet());

            Set<CodeLine> uniqueCodeLines = allOtherTests.stream()
                    .map(CodeTest::getCoveredLines)
                    .map(set -> asymmetricDifference(coveredLines,
                            set)) // this operation modifies the collection itself
                    .findFirst()
                    .orElse(coveredLines);

            if (!uniqueCodeLines.isEmpty()) {
                finalResult.add(testCode);
            }

        });

        return finalResult;
    }

    private Set<CodeLine> asymmetricDifference(Set<CodeLine> setOne, Set<CodeLine> setTwo) {
        setOne.removeAll(setTwo);
        return setOne;
    }

    /**
     * <p>
     * Method used for calculating which are the lines covered by the test.
     * <p>
     * !!WARNING!! - For simplicity and following the README.md we will just use an arbitrary criteria
     * for setting the lines that are covered by the {@link JavaTest} class:
     * - If the test code line is contained inside the tested class code line, the line is covered
     * - If the test code line is on the same line as the tested class code line, the line is covered
     * <p>
     * For doing this properly it, we should decompose each class into the minimum elements like Methods, Parameters,
     * Directives, etc. The decompositions should result into a Graph-like structure that we can use as a reference to
     * navigate the class and check if the code inside it can be considered tested.
     *
     * </p>
     *
     * @param lines the lines to be tested for coverage
     */

    public Set<CodeLine> calculateCoveredLines(List<CodeLine> lines, List<CodeLine> testLines) {
        return lines.stream()
                .filter(codeLine -> !codeLine.getContents().isEmpty())
                .filter(line -> randomCriteriaMatching(testLines, line))
                .collect(Collectors.toSet());
    }

    private boolean randomCriteriaMatching(List<CodeLine> testLines, CodeLine line) {
        return testLines.stream()
                .anyMatch(testLine -> line.getContents().contains(testLine.getContents())
                        || line.getLineNumber() == testLine.getLineNumber());
    }

}
