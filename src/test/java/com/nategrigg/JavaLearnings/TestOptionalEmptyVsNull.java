package com.nategrigg.JavaLearnings;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SuppressWarnings({"ConstantValue", "OptionalOfNullableMisuse"})
public class TestOptionalEmptyVsNull {
  static final String A_STRING = "a string";
  static final String NULL_STRING = null;

  @Test
  public void testOptionalEmptyIsEmpty() {
    Optional<String> opt = Optional.empty();
    assertThat(opt.isEmpty()).isTrue();
  }

  @Test
  public void testOptionalOfNullableWithNullIsEmpty() {
    Optional<String> opt = Optional.ofNullable(NULL_STRING);
    assertThat(opt.isEmpty()).isTrue();
    assertThat(opt.isPresent()).isFalse();
  }

  @Test
  public void testOptionalOfWithNonNullIsPresent() {
    Optional<String> opt = Optional.of(A_STRING);
    assertThat(opt.isPresent()).isTrue();
    assertThat(opt.isEmpty()).isFalse();
  }

  @Test
  public void testOptionalOfNullableWithNonNullIsPresent() {
    Optional<String> opt = Optional.ofNullable(A_STRING);
    assertThat(opt.isPresent()).isTrue();
    assertThat(opt.isEmpty()).isFalse();
  }

  @Test
  public void testOptionalOfStringJoin() {
    Optional<String> opt = Optional.of(String.join("\n"));

    assertThat(opt.isPresent()).isTrue();
    assertThat(opt.get()).isEqualTo("");
  }

  @Test
  public void testOptionalOfStringJoinWithNull() {
    Optional<String> opt = Optional.of(String.join("\n", NULL_STRING));

    assertThat(opt.isPresent()).isTrue();
    assertThat(opt.get()).isEqualTo("null");
  }
}
