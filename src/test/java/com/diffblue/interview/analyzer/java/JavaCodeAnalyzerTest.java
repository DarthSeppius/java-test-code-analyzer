package com.diffblue.interview.analyzer.java;

import static org.junit.Assert.*;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.diffblue.interview.CodeLine;
import com.diffblue.interview.CodeTest;
import org.junit.*;

public class JavaCodeAnalyzerTest {

    public static final String PATH_TO_TEST_CLASS = "src/test/resources/JavaClassTest.java";
    public static final String PATH_TO_JAVA_CLASS = "src/main/java/com/diffblue/interview/analyzer/java/JavaCodeLine.java";

    private static File javaSourceClassToTest;
    private static File javaTestClass;

    private static JavaCodeAnalyzer codeAnalyzer;

    @BeforeClass
    public static void setUpTestClass() {
        javaTestClass = new File(PATH_TO_TEST_CLASS);
        javaSourceClassToTest = new File(PATH_TO_JAVA_CLASS);
        codeAnalyzer = new JavaCodeAnalyzer(javaSourceClassToTest, javaTestClass);
    }

    @AfterClass
    public static void cleanUpTestClass() {
        javaTestClass = null;
        javaSourceClassToTest = null;
    }

    @Test
    public void runTest_firstTestOnTheList_returnsCoveredLines() {
        List<CodeTest> testsToExecute = codeAnalyzer.getTestsToExecute();

        assertFalse(testsToExecute.isEmpty());

        CodeTest firstTest = testsToExecute.get(0);

        Set<CodeLine> codeLines = codeAnalyzer.runTest(firstTest);

        assertFalse(codeLines.isEmpty());

        int expectedSize = 4;
        assertEquals(codeLines.size(), expectedSize);
    }

    @Test
    public void runTestSuite_allTestsInTestClass_returnsUniqueCoveredLines() {
        List<CodeTest> testsToExecute = codeAnalyzer.getTestsToExecute();
        Set<CodeLine> codeLines = codeAnalyzer.runTestSuite(new HashSet<>(testsToExecute));

        assertFalse(codeLines.isEmpty());

        int expectedSize = 20;
        assertEquals(codeLines.size(), expectedSize);
    }

    @Test
    public void uniqueTests_allTestsInTestsClass_returnsUniqueCodeTest() {
        List<CodeTest> testsToExecute = codeAnalyzer.getTestsToExecute();
        Set<CodeTest> codeTests = codeAnalyzer.uniqueTests(new HashSet<>(testsToExecute));

        assertFalse(codeTests.isEmpty());

        int expectedSize = 5;
        assertEquals(codeTests.size(), expectedSize);
    }

}
