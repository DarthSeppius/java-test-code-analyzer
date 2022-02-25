package com.diffblue.interview.analyzer.java;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.*;

public class JavaClassTest {

    @Test
    public void createJavaClass_nullFile_throwsIllegalArgumentException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new JavaClass(null)
        );
    }

    @Test
    public void createJavaClass_invalidFileExtension_throwsIllegalArgumentException() {
        File invalidFile = new File("/path/to/file.txt");
        assertThrows(
                IllegalArgumentException.class,
                () -> new JavaClass(invalidFile)
        );
    }

}
