package com.nategrigg.JavaLearnings;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;

public class TestBase64Decode {

  private Base64.Decoder d64;
  private Base64.Encoder e64;

  @BeforeEach
  void setUp() {
    d64 = Base64.getDecoder();
    e64 = Base64.getEncoder();
  }

  @Test
  void testOneDecode() {
    String actual = new String(d64.decode("QQ=="));
    assertThat(actual).isEqualTo("A");
  }

  @Test
  void testOneEncode() {
    String input = "A";
    byte[] inputBytes = input.getBytes();
    assertThat(inputBytes).isEqualTo(new byte[]{0x41});

    String actual = new String(e64.encode(inputBytes));

    assertThat(actual).isEqualTo("QQ==");
  }

  @Test
  void testOneWithSpace() {
    String encoded = "QQ== ";
    String decoded = new String(d64.decode(encoded));

    assertThat(decoded).isEqualTo("A");
  }

  @Test
  void testOneWithComma() {
    String encoded = "QQ==,";
    String decoded = new String(d64.decode(encoded));

    assertThat(decoded).isEqualTo("A");
  }
}
