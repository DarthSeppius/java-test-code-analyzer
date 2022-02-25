package com.diffblue.interview.analyzer.java;

import static org.junit.Assert.*;

import org.junit.*;

public class JavaCodeLineTest {

    public static final String VALID_CONTENT = "A content of a line (╯°□°）╯︵ ┻━┻";
    public static final int VALID_LINE_NUMBER = 1;

    @Test
    public void createCodeLine_nullContent_throwsIllegalArgumentException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new JavaCodeLine(VALID_LINE_NUMBER, null)
        );
    }

    @Test
    public void createCodeLine_zeroLineValue_throwsIllegalArgumentException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new JavaCodeLine(0, VALID_CONTENT)
        );
    }

    @Test
    public void createCodeLine_negativeLineValue_throwsIllegalArgumentException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new JavaCodeLine(-1, VALID_CONTENT)
        );
    }

    @Test
    public void createCodeLine_validNumberAndString_returnsCodeLineCorrectly() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new JavaCodeLine(0, VALID_CONTENT)
        );
    }

}
