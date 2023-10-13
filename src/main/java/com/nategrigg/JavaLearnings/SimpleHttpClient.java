package com.nategrigg.JavaLearnings;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class SimpleHttpClient {
  public String get(URL url) throws IOException {
    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
    return get(urlConnection);
  }

  public String get(HttpURLConnection urlConnection) throws IOException {
    urlConnection.setDoOutput(true);
    urlConnection.setRequestProperty("Content-Type", "application/json");
    byte[] content = "{\"hello\": \"this is a test\"}".getBytes(StandardCharsets.UTF_8);
    urlConnection.setRequestProperty("Content-Length", Long.toString(content.length));
    urlConnection.setFixedLengthStreamingMode(content.length); // Stream the data so we don't run out of memory
    OutputStream outputToConnection = urlConnection.getOutputStream();
    outputToConnection.write(content);

    InputStream inputFromConnection = urlConnection.getInputStream();

    byte[] buffer = new byte[1024];
    StringBuilder sb = new StringBuilder();
    while (inputFromConnection.read(buffer, 0, buffer.length) != -1) {
      sb.append(new String(buffer));
    }

    return sb.toString();
  }
}
