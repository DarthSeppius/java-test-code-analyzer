package com.diffblue.interview.analyzer.java;

import static org.junit.Assert.*;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.diffblue.interview.CodeTest;
import org.junit.*;

public class JavaTestClassTest {

    private static final String PATH_TO_TEST_CLASS = "src/test/resources/JavaClassTest.java";
    private static final String PATH_TO_NO_TEST_CLASS = "src/main/java/com/diffblue/interview/analyzer/java/JavaCodeLine.java";

    private static File testFile;
    private static File notTestFile;

    @BeforeClass
    public static void setUpTestClass() {
        testFile = new File(PATH_TO_TEST_CLASS);
        notTestFile = new File(PATH_TO_NO_TEST_CLASS);
    }

    @AfterClass
    public static void cleanUpTestClass() {
        testFile = null;
        notTestFile = null;
    }

    @Test
    public void createJavaTestClass_nullMarkers_throwsIllegalArgumentException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new JavaTestClass(testFile, null)
        );
    }

    @Test
    public void createJavaTestClass_emptyFile_throwsIllegalArgumentException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new JavaTestClass(null, JUnitTestMarker.TEST_METHOD_ANNOTATION)
        );
    }

    @Test
    public void createJavaTestClass_noTestMethods_returnsEmptySet() {
        JavaTestClass emptyTestClass = new JavaTestClass(notTestFile, JUnitTestMarker.TEST_METHOD_ANNOTATION);

        assertTrue(emptyTestClass.getTests().isEmpty());
    }

    @Test
    public void createJavaTestClass_validTestClass_returnsSetOfTests() {
        JavaTestClass emptyTestClass = new JavaTestClass(testFile, JUnitTestMarker.TEST_METHOD_ANNOTATION);

        int expectedNumberOfTests = 3;
        List<String> expectedTestMethodNames =
                Stream.of(
                        "public void operationOne_input_resultOne() {",
                        "public void operationTwo_input_resultOne() {",
                        "public void operationThree_input_resultOne() {"
                ).sorted().collect(Collectors.toList());

        List<CodeTest> tests = emptyTestClass.getTests();
        assertEquals(expectedNumberOfTests, tests.size());

        List<String> testNames = tests.stream().map(CodeTest::getName).sorted().collect(Collectors.toList());
        assertEquals(expectedTestMethodNames, testNames);
    }

}
