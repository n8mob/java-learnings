package com.nategrigg.JavaLearnings;

import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class TestMapEquality {
  @Test
  void testEmptyMaps() {
    var map1 = Map.of();
    var map2 = Map.of();

    assertThat(map1 == map2).isTrue().as("oh, empty map is shared");
    assertThat(map1.equals(map2)).isTrue().as("if they are instance-equal, then this doesn't surprise us.");
  }

  @Test
  void testSingleValue() {
    var map1 = Map.of("one", "two");
    var map2 = Map.of("one", "two");

    //noinspection NewObjectEquality
    assertThat(map1 == map2).isFalse();
    assertThat(map1).isEqualTo(map2);
  }

  @Test
  void testTwoThings() {
    var map1 = new HashMap<String, String>();
    var map2 = new HashMap<String, String>();

    String aKey = "aKey";
    String aSimpleValue = "aValue";
    map1.put(aKey, aSimpleValue);

    int[] ints = {97, 86, 97, 108, 117, 101};
    byte[] bytes = new byte[ints.length];
    for (int i = 0; i < ints.length; i++) {
      bytes[i] = (byte)ints[i];
    }
    String aLessSimpleValue = StandardCharsets.US_ASCII.decode(ByteBuffer.wrap(bytes)).toString();
    map2.put(aKey, aLessSimpleValue);

    assertThat(map1).isEqualTo(map2);
    map2.put("anotherKey", "someOtherValue");
    assertThat(map1).isNotEqualTo(map2);

    map2.remove("anotherKey");
    assertThat(map1).isEqualTo(map2);
  }

  @Test
  void testBadData() {
  }
}
