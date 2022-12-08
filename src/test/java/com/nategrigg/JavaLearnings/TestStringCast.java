package com.nategrigg.JavaLearnings;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

@SuppressWarnings("UnnecessaryLocalVariable")
public class TestStringCast {

    static final String NOT_NULL_OR_EMPTY = "Not null or empty";

    @Test
    void testStringInObjectVariable() {
        Object stringAsObject = NOT_NULL_OR_EMPTY;

        assertThat(stringAsObject).isEqualTo(NOT_NULL_OR_EMPTY);
        assertThat(stringAsObject).isSameAs(NOT_NULL_OR_EMPTY);
    }

    @Test
    void testStringInVariableOfTypeObject() {
        Object stringAsObject = NOT_NULL_OR_EMPTY;

        Object boo = new Object();
        assertThat(boo.toString()).startsWith("java.lang.Object");

        assertThat(stringAsObject.toString()).doesNotStartWith("java.lang.Object");
        assertThat(stringAsObject.toString()).isEqualTo(NOT_NULL_OR_EMPTY);
        assertThat(stringAsObject).isSameAs(NOT_NULL_OR_EMPTY);
    }

    @Test
    void testStringCastBackToString() {
        Object stringAsObject = NOT_NULL_OR_EMPTY;

        String castToString = ((Object)stringAsObject).toString();
        assertThat(castToString).doesNotStartWith("java.lang.Object");
        assertThat(castToString).doesNotStartWith("java.lang.String");

        assertThat(castToString).isEqualTo(NOT_NULL_OR_EMPTY);
    }
}
