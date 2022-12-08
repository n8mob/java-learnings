package com.nategrigg.JavaLearnings;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestIntellijObsolete {

    static final String DONT_SET_THAT = "DON'T SET THAT!";
    static final String SET_THAT = "YOU MAY SET THAT.";
    private HasObsoleteMethod unitUnderTest;

    static class HasObsoleteMethod {
        @Deprecated
        private String toBeDeprecated;
        private String newValue;

        @Deprecated
        public String getToBeDeprecated() {
            return toBeDeprecated;
        }

        @Deprecated
        public void setToBeDeprecated(String toBeDeprecated) {
            this.toBeDeprecated = toBeDeprecated;
        }

        public String getNewValue() {
            return newValue;
        }

        public void setNewValue(String newValue) {
            this.newValue = newValue;
        }
    }

    @BeforeEach
    void setUp() {
        this.unitUnderTest = new HasObsoleteMethod();
    }

    @Test
    void testNonDeprecated() {
        unitUnderTest.setNewValue(SET_THAT);

        assertThat(unitUnderTest.getNewValue()).isEqualTo(SET_THAT);
    }

    @Test
    void testDeprecated() {
        unitUnderTest.setToBeDeprecated(DONT_SET_THAT);
        assertThat(unitUnderTest.getToBeDeprecated()).isEqualTo(DONT_SET_THAT);
    }
}
