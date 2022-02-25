package com.diffblue.interview;

/**
 * An interface used for identify a test inside the code
 */

public interface TestMarker {

    String getMarker();

    default boolean isStartOfTest(CodeLine line) {
        return line.getContents().contains(getMarker());
    }

}
