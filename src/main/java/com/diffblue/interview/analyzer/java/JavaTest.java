package com.diffblue.interview.analyzer.java;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.diffblue.interview.CodeLine;
import com.diffblue.interview.CodeTest;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

public class JavaTest implements CodeTest {

    private final String name;
    private final List<CodeLine> testLines;
    private Set<CodeLine> coveredLines;

    public JavaTest(String name, List<CodeLine> testLines) {
        Preconditions.checkArgument(name != null && !name.isEmpty(), "Name cannot be null or empty");
        Preconditions.checkArgument(testLines != null, "The content of the test cannot be null");

        this.name = name;
        this.testLines = testLines;
        this.coveredLines = new HashSet<>();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Set<CodeLine> getCoveredLines() {
        return ImmutableSet.copyOf(coveredLines);
    }

    public List<CodeLine> getTestLines() {
        return ImmutableList.copyOf(testLines);
    }

    public void setCoveredLines(Set<CodeLine> coveredLines) {
        this.coveredLines = coveredLines;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JavaTest javaTest = (JavaTest) o;
        return Objects.equal(name, javaTest.name) && Objects.equal(testLines, javaTest.testLines);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, testLines);
    }

    @Override
    public String toString() {
        return "JavaTest{" +
                "name='" + name + '\'' +
                ", testLines=" + testLines +
                ", coveredLines=" + coveredLines +
                '}';
    }

}
