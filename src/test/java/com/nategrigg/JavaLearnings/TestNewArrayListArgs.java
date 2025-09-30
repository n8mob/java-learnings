package com.nategrigg.JavaLearnings;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class TestNewArrayListArgs {
  // null-handling is the point of the test
  @SuppressWarnings("DataFlowIssue")
  @Test
  void givenANullArgument_whenNewArrayList_shouldThrowNullPointer() {
    try {
      new java.util.ArrayList<String>(null);
      fail("Expected NullPointerException");
    } catch (NullPointerException e) {
      assertThat(e).isNotNull();
      assertThat(e).hasMessageContaining("toArray");
    }
  }
}
