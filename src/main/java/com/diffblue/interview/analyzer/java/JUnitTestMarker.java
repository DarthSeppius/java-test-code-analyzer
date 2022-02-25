package com.diffblue.interview.analyzer.java;

import com.diffblue.interview.TestMarker;

/**
 * A simple version for the Junit Marker
 * (only for JUnit4 since Junit5 we have also other keyword for identifying a test like @ParametrizedTest, @DynamicTest, etc)
 */

public enum JUnitTestMarker implements TestMarker {

    TEST_METHOD_ANNOTATION("@Test");

    private final String marker;

    JUnitTestMarker(String marker) {
        this.marker = marker;
    }

    @Override
    public String getMarker() {
        return marker;
    }
}
