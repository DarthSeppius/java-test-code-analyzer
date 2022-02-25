package com.diffblue.interview.analyzer.java;

import static org.junit.Assert.*;

import java.io.File;
import java.nio.file.FileSystems;

import org.junit.*;

public class TestClassToAnalyze {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void anotherTest() {
        FileSystems.getDefault();
    }

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

    @Test
    public void operationTwo_input_resultOne() {
        String lineOfCode = "1";
        String lineOfTwo = "2";
    }

    @Test(expected = Exception.class)
    public void operationThree_input_resultOne() {
        String lineOfCode = "1";
    }

    @Test(expected = Exception.class)
    public void operationThree_input_resultTwo() {
        String lineOfCode = "1";
    }

}
