package com.diffblue.interview.analyzer.java;

import com.diffblue.interview.CodeLine;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

public class JavaCodeLine implements CodeLine {

    private final int lineNumber;
    private final String content;

    public JavaCodeLine(int lineNumber, String content) {
        Preconditions.checkArgument(lineNumber > 0, "Line number cannot be negative or 0");
        Preconditions.checkArgument(content != null, "Line content cannot be null");

        this.lineNumber = lineNumber;
        this.content = content.trim();
    }

    @Override
    public int getLineNumber() {
        return lineNumber;
    }

    @Override
    public String getContents() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JavaCodeLine that = (JavaCodeLine) o;
        return Objects.equal(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(content);
    }

    @Override
    public String toString() {
        return "JavaCodeLine{" +
                "lineNumber=" + lineNumber +
                ", content='" + content + '\'' +
                '}';
    }

}
