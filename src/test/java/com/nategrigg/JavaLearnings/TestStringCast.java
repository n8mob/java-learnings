package com.nategrigg.JavaLearnings;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.Assertions;
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
    void testStringCastToObject() {
        Object stringAsObject = NOT_NULL_OR_EMPTY;
        String actual = ((Object)stringAsObject).toString();

        Object boo = new Object();
        assertThat(boo.toString()).startsWith("java.lang.Object");

        assertThat(actual).doesNotStartWith("java.lang.Object");
        assertThat(actual).doesNotStartWith("java.lang.String");

        assertThat(actual).isEqualTo(NOT_NULL_OR_EMPTY);
    }
}
