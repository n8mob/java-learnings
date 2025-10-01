package com.nategrigg.JavaLearnings;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TestScannerVsReader {

  public static final String TWENTY_FIVE_WORDS = """
    This is line one, yes.
    This is line two, okay?
    This is line three. Whatever.
    This is line four; so?
    This is line five. Great!
    """;
  public static final String TWO_HUNDRED_FIFTY_THOUSAND_WORDS = TWENTY_FIVE_WORDS.repeat(10000);

  @Test
  void given25WordsOver5Lines_whenReadWithScanner_then25Words() {
    var scanner = new java.util.Scanner(TWENTY_FIVE_WORDS);
    int wordCount = 0;
    while (scanner.hasNext()) {
      scanner.next();
      wordCount++;
    }

    assertThat(wordCount).isEqualTo(25);
  }

  @Test
  void given25WordsOver5Lines_whenReadWithReader_then5Lines() throws Exception {
    var input = TWENTY_FIVE_WORDS;
    var reader = new java.io.BufferedReader(new java.io.StringReader(input));
    int lineCount = 0;
    while (reader.readLine() != null) {
      lineCount++;
    }

    assertThat(lineCount).isEqualTo(5);
  }

  @Test
  void given250000WordsOver50000Lines_readerTimeShouldBeFasterThanScannerTime() throws IOException {
    var input = TWO_HUNDRED_FIFTY_THOUSAND_WORDS; // Repeat to make the test more meaningful

    // Measure Scanner time
    var scannerStart = System.nanoTime();
    var scanner = new java.util.Scanner(input);
    int wordCount = 0;
    while (scanner.hasNext()) {
      scanner.next();
      wordCount++;
    }
    var scannerEnd = System.nanoTime();
    long scannerDuration = scannerEnd - scannerStart;

    // Measure Reader time
    var readerStart = System.nanoTime();
    var reader = new java.io.BufferedReader(new java.io.StringReader(input));
    int lineCount = 0;
    while (reader.readLine() != null) {
      lineCount++;
    }
    var readerEnd = System.nanoTime();
    long readerDuration = readerEnd - readerStart;

    System.out.println("Scanner duration (ns): " + scannerDuration);
    System.out.println("Reader duration (ns): " + readerDuration);

    assertThat(wordCount).isEqualTo(250_000); // 25 words * 10000 repetitions
    assertThat(lineCount).isEqualTo(50_000);   // 5 lines * 1000 repetitions
    assertThat(readerDuration).isLessThan(scannerDuration);
  }

  @Test
  void given250000WordsOver50000Lines_whenScannerByLine_then50kLines() {
    var scanner = new java.util.Scanner(TWO_HUNDRED_FIFTY_THOUSAND_WORDS);
    int lineCount = 0;
    while (scanner.hasNextLine()) {
      scanner.nextLine();
      lineCount++;
    }

    assertThat(lineCount).isEqualTo(50_000);
  }

  @Test
  void given250000WordsOver50000Lines_whenScannedByLine_thenReaderShouldStillBeFaster() throws IOException {
    // Measure Scanner time by line
    var scannerStart = System.nanoTime();
    var scanner = new java.util.Scanner(TWO_HUNDRED_FIFTY_THOUSAND_WORDS);
    int lineCount = 0;
    while (scanner.hasNextLine()) {
      scanner.nextLine();
      lineCount++;
    }
    var scannerEnd = System.nanoTime();
    long scannerDuration = scannerEnd - scannerStart;

    // Measure Reader time
    var readerStart = System.nanoTime();
    var reader = new java.io.BufferedReader(new java.io.StringReader(TWO_HUNDRED_FIFTY_THOUSAND_WORDS));
    int readerLineCount = 0;
    while (reader.readLine() != null) {
      readerLineCount++;
    }
    var readerEnd = System.nanoTime();
    long readerDuration = readerEnd - readerStart;

    System.out.println("Scanner by line duration (ns): " + scannerDuration);
    System.out.println("Reader duration (ns): " + readerDuration);

    assertThat(lineCount).isEqualTo(50_000);   // 5 lines * 10000 repetitions
    assertThat(readerLineCount).isEqualTo(50_000);   // 5 lines * 10000 repetitions
    assertThat(readerDuration).isLessThan(scannerDuration);
  }
}
