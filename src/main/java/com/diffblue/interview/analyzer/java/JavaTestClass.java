package com.diffblue.interview.analyzer.java;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.diffblue.interview.CodeLine;
import com.diffblue.interview.CodeTest;
import com.diffblue.interview.TestMarker;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

public class JavaTestClass extends JavaClass {

    private final List<CodeTest> tests = new ArrayList<>();

    public JavaTestClass(File file, TestMarker marker) {
        super(file);

        Preconditions.checkArgument(marker != null, "Test marker cannot be null");
        toTests(getLinesOfCode(), marker);
    }

    public List<CodeTest> getTests() {
        return tests;
    }

    private void toTests(List<CodeLine> codeBase, TestMarker markers) {
        for (int i = 0; i < codeBase.size(); i++) {
            CodeLine line = codeBase.get(i);
            if (markers.isStartOfTest(line)) {
                tests.add(
                        parseTest(
                                Collections.unmodifiableList(codeBase.subList(i, codeBase.size())),
                                markers
                        ));
            }
        }
    }

    private CodeTest parseTest(List<CodeLine> codeLines, TestMarker marker) {
        String methodName = findMethodName(codeLines);

        List<CodeLine> testLines = new ArrayList<>();

        for (int i = 1; i < codeLines.size() && !marker.isStartOfTest(codeLines.get(i)); i++) {
            testLines.add(codeLines.get(i));
        }

        return new JavaTest(methodName, testLines);
    }

    /**
     *
     * Find and return a string representing the Test method name.
     *
     * For simplicity, we will not remove the visibility modifiers, method params, and final '{'
     *
     * @param lines the code lines (with the test name) of a test
     *
     * @return string representing the Test method name
     */

    private String findMethodName(List<CodeLine> lines) {
        int testMethodNamePosition = 0;
        String methodName = lines.get(testMethodNamePosition).getContents();

        if (!lines.get(testMethodNamePosition).getContents().trim().endsWith("{")) {
            Preconditions.checkArgument(
                    lines.get(++testMethodNamePosition) != null,
                    "Test name cannot be null or empty"
            );

            methodName = lines.get(testMethodNamePosition).getContents();
        }

        return methodName.trim();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        JavaTestClass that = (JavaTestClass) o;
        return Objects.equal(tests, that.tests);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), tests);
    }

}
