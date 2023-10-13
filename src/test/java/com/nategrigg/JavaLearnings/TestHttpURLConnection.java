package com.nategrigg.JavaLearnings;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class TestHttpURLConnection {
  String legalChars = "ABCDEFGHIJKLMNOPQRSTUVWXYabcdefghijklmnopqrstuvwxy0123456789";
  byte[] legalBytes = legalChars.getBytes();
  private URL url;
  private byte[] data1024;
  private String string1024;

  @BeforeEach
  void setUp() throws IOException {
    url = new URL("https://postman-echo.com/post?queryParam=foobar");

    var r = new Random();
    data1024 = new byte[1024];
    for (int i = 0; i < data1024.length; i++) {
      data1024[i] = legalBytes[r.nextInt(legalBytes.length)];
    }
    string1024 = new String(data1024);
  }

  @Test
  void testSendFewerBytesThanPromised() throws IOException {
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setRequestMethod("POST");
    connection.setRequestProperty("Content-Type", "application/json");
    connection.setDoOutput(true);
    OutputStream outputStream = connection.getOutputStream();

    try {
      outputStream.write(data1024, 0, data1024.length + 1);
      fail("Expecting an index exception when it tries to write the 1,025th byte");
    } catch (IndexOutOfBoundsException outOfBounds) {
      assertThat(outOfBounds.getMessage()).contains("1025");
    }
  }

  @Test
  void testSendPartOfBufferThenWholeBuffer() throws IOException {
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setRequestMethod("POST");
    connection.setRequestProperty("Content-Type", "application/json");
    connection.setDoOutput(true);
    OutputStream outputStream = connection.getOutputStream();

    outputStream.write(data1024, 0, data1024.length - 1);
    outputStream.write(data1024, 0, data1024.length);

    InputStream inputStream = connection.getInputStream();
    var actual = new String(inputStream.readAllBytes());

    assertThat(actual).contains("data");
    assertThat(actual).contains("args");
    assertThat(actual).contains("query");
    assertThat(actual).contains("foobar");
    assertThat(actual).contains(string1024.substring(0, 1023) + string1024);
  }

  @Test
  void testSendPartOfBuffer() throws IOException {
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setRequestMethod("POST");
    connection.setRequestProperty("Content-Type", "application/json");
    connection.setDoOutput(true);
    OutputStream outputStream = connection.getOutputStream();

    outputStream.write(data1024, 0, data1024.length - 1);

    InputStream inputStream = connection.getInputStream();
    var actual = new String(inputStream.readAllBytes());

    assertThat(actual).contains("data");
    assertThat(actual).contains("args");
    assertThat(actual).contains("query");
    assertThat(actual).contains("foobar");
    assertThat(actual).contains(string1024.substring(0, 1023));
  }

  @Test
  void testSendAFewK() throws IOException {
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setRequestMethod("POST");
    connection.setRequestProperty("Content-Type", "application/json");
    connection.setDoOutput(true);
    OutputStream outputStream = connection.getOutputStream();

    outputStream.write(data1024, 0, data1024.length);
    outputStream.write(data1024, 0, data1024.length);
    outputStream.write(data1024, 0, data1024.length);
    outputStream.write(data1024, 0, data1024.length);

    InputStream inputStream = connection.getInputStream();
    var actual = new String(inputStream.readAllBytes());

    assertThat(actual).contains("data");
    assertThat(actual).contains("args");
    assertThat(actual).contains("query");
    assertThat(actual).contains("foobar");
    assertThat(actual).contains(string1024);
  }

  @Test
  void testExploreHttpUrlConnection() throws IOException {
    SimpleHttpClient client = new SimpleHttpClient();
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setRequestMethod("POST");
    connection.setDoOutput(true);
    var inputA = connection.getInputStream();

    var readA = new String(inputA.readAllBytes());
    assertThat(readA).contains("queryParam");
    assertThat(readA).contains("foobar");

    inputA.close();

    var inputB = connection.getInputStream();
    try {
      var readB = new String(inputB.readAllBytes());
      assertThat(readB).isEqualTo("");
      var outputStream = connection.getOutputStream();
      outputStream.write("{\"postParam\":\"bazquux\"}".getBytes());
    } catch (IOException ioex) {
      assertThat(ioex.getMessage()).containsIgnoringCase("stream is closed");
    }
  }

  @Test
  void testNormalRequest() throws IOException {
    SimpleHttpClient c = new SimpleHttpClient();
    String actual = c.get(url);
    assertThat(actual).contains("hello");
    assertThat(actual).contains("this is a test");
  }

  @Test
  void testTwoCalls() throws IOException {
    SimpleHttpClient client = new SimpleHttpClient();
    String actual = client.get(url);
    assertThat(actual).contains("hello");
    assertThat(actual).contains("this is a test");

    actual = client.get(url);
    assertThat(actual).contains("hello");
    assertThat(actual).contains("this is a test");
  }

}
