package com.nategrigg.JavaLearnings;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TestRecordsWithMethods {
  record Point(int x, int y) {
  }

  record Circle(Point center, double radius) {
    double area() {
      return Math.PI * radius * radius;
    }
  }

  @Test
  void testRecordWithMethod() {

    Point center = new Point(0, 0);
    Circle circle = new Circle(center, 5.0);

    assertThat(circle.center).isEqualTo(center);
    assertThat(circle.radius).isEqualTo(5.0);
    assertThat(circle.area()).isEqualTo(Math.PI * 25.0);
  }
}
