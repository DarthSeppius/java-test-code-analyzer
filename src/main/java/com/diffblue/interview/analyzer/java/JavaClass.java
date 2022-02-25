package com.diffblue.interview.analyzer.java;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.diffblue.interview.CodeClass;
import com.diffblue.interview.CodeLine;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

public class JavaClass implements CodeClass {

    private static final String JAVA_SUFFIX = ".java";

    private final File file;
    private final List<CodeLine> codeLines;

    public JavaClass(File file) {
        Preconditions.checkArgument(file != null, "Java source file cannot be null");

        ClassContentBuilder builder = new ClassContentBuilder();

        Path path = FileSystems.getDefault().getPath(file.getAbsolutePath());
        Preconditions.checkArgument(
                path.toString().endsWith(JAVA_SUFFIX),
                String.format("Java file needs to end with the '%s' extension", JAVA_SUFFIX)
        );

        try (Stream<String> lines = Files.lines(path)) {
            lines.forEach(builder::addLine);
        } catch (IOException ex) {
            throw new IllegalArgumentException("File needs to be present on the system to be analyzed", ex);
        }

        this.file = file;
        this.codeLines = builder.build();
    }

    @Override
    public List<CodeLine> getLinesOfCode() {
        return ImmutableList.copyOf(codeLines);
    }

    @Override
    public File getFile() {
        return file;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JavaClass javaClass = (JavaClass) o;
        return Objects.equal(file, javaClass.file);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(file);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        List<String> collect = codeLines.stream()
                .map(CodeLine::getContents)
                .map(codeLine -> codeLine + "\n")
                .collect(Collectors.toList());

        return builder.append(collect).toString();
    }

    private static class ClassContentBuilder {

        private int lineNumber = 1;
        private final List<CodeLine> allCodeLines = new ArrayList<>();

        public void addLine(String lineContent) {
            if (!lineContent.isEmpty()) {
                allCodeLines.add(new JavaCodeLine(lineNumber, lineContent));
            }

            lineNumber = lineNumber + 1;
        }

        public List<CodeLine> build() {
            return allCodeLines;
        }

    }

}
