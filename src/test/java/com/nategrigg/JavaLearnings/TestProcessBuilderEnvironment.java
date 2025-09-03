package com.nategrigg.JavaLearnings;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TestProcessBuilderEnvironment {
  @Test
  void testProcessBuilderEnvironment() throws IOException, InterruptedException {
    ProcessBuilder pb = new ProcessBuilder();
    pb.environment().put("MY_VAR", "my_value");
    assert pb.environment().get("MY_VAR").equals("my_value");

    pb.command("printenv", "MY_VAR");

    Process p = pb.start();
    p.waitFor();
    String output = new String(p.getInputStream().readAllBytes(), StandardCharsets.UTF_8).trim();
    assertThat(output).isEqualTo("my_value");
  }

  @Test
  void testProcessBuilderSettingWholeEnvironment() {
    Map<String, String> replacementEnvironment = Map.of(
        "VAR1", "value1",
        "VAR2", "value2"
    );

    ProcessBuilder pb = new ProcessBuilder();
    // pb.setEnvironment(replacementEnvironment); // No such method
    pb.environment().clear();
    pb.environment().putAll(replacementEnvironment);

    assertThat(pb.environment()).isEqualTo(replacementEnvironment);
  }
}
