package com.nategrigg.JavaLearnings;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TestSimpleRecords {
  record Point(int x, int y) {
  }

  record Circle(Point center, double radius) {
    double area() {
      return Math.PI * radius * radius;
    }
  }

  @Test
  void testSimpleRecord() {

    Point p1 = new Point(3, 4);

    assertThat(p1.x).isEqualTo(3);
    assertThat(p1.y).isEqualTo(4);

    // Records have built-in equals, hashCode, and toString methods
    Point p2 = new Point(3, 4);
    assert p1.equals(p2);
    assert p1.hashCode() == p2.hashCode();
    assert p1.toString().equals("Point[x=3, y=4]");
  }

  @Test
  void testUnequalRecords() {
    Point p1 = new Point(3, 4);
    Point p2 = new Point(5, 6);

    assert !p1.equals(p2);
    assert p1.hashCode() != p2.hashCode();
    assert !p1.toString().equals(p2.toString());
  }

  @Test
  void testRecordInCollection() {
    Point p1 = new Point(1, 2);
    Point p2 = new Point(3, 4);
    Point p3 = new Point(1, 2); // Same as p1

    java.util.Set<Point> pointSet = new java.util.HashSet<>();
    pointSet.add(p1);
    pointSet.add(p2);

    // p3 should be considered equal to p1 and not added again
    pointSet.add(p3);

    assertThat(pointSet.size()).isEqualTo(2);
    assertThat(pointSet).contains(p1, p2);
  }

  @Test
  void testRecordWithDerivedField() {
    Circle c = new Circle(new Point(0, 0), 1.0);
    Circle d = new Circle(new Point(0, 0), 1.0 + (Float.MIN_NORMAL * 10.0));
    assertThat(c.area()).isEqualTo(Math.PI);
    assert c.equals(d);
  }

  @Test
  void testNestedRecords() {
    Point center = new Point(0, 0);
    Circle circle = new Circle(center, 5.0);

    assertThat(circle.center).isEqualTo(center);
    assertThat(circle.radius).isEqualTo(5.0);
    assertThat(circle.area()).isEqualTo(Math.PI * 25.0);
    assertThat(circle.toString()).isEqualTo("Circle[center=Point[x=0, y=0], radius=5.0]");
  }
}
