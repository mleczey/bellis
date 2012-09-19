package com.mleczey.io;

import com.google.common.io.CharStreams;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

public class InputStreamToStringExamples {
  private static final Logger logger = Logger.getLogger(InputStreamToStringExamples.class.getName());
  private static final String PATH_TO_RESOURCE_TEXT = "./src/main/resources/resource.txt";
  private static final int LOOPS = 100;
  
  private Map<String, Long> benchmark;
  
  public InputStreamToStringExamples() {
    this.benchmark = new HashMap<>(10);
  }
  
  public static void main(String[] args) {
    InputStreamToStringExamples e = new InputStreamToStringExamples();
    e.run();
  }
  
  private void run() {
    try {
      this.scannerExample();
      this.bufferedReaderExample();
      this.commonsIOExample();
      this.guavaExample();
      this.commonsIOWithStreamWriterExample();
      this.showBenchmark();
    } catch (IOException x) {
      logger.log(Level.SEVERE, "Error", x);
    }
  }
  
  private void scannerExample() throws IOException {
    long time = 0;
    for (int i = 0; i < LOOPS; i++) {
      long start = System.nanoTime();
      try (InputStream is = this.getInputStream();
          Scanner scanner = new Scanner(is, "UTF-8")) {
        String input = scanner.useDelimiter("\\A").next();
        long length = System.nanoTime() - start;
        time += length;
        logger.log(Level.INFO, "Scanner size: {0}, time: {1}", new Object[] {input.length(), length}); 
      }
    }
    this.benchmark.put("Scanner", new Long(time / LOOPS));
  }
  
  private InputStream getInputStream() throws IOException {
    return FileUtils.openInputStream(new File(PATH_TO_RESOURCE_TEXT));
  }
  
  private void bufferedReaderExample() throws IOException {
    long time = 0;
    for (int i = 0; i < LOOPS; i++) {
      long start = System.nanoTime();
      try (InputStream is = this.getInputStream();
          InputStreamReader isr = new InputStreamReader(is, Charset.forName("UTF-8"));
          BufferedReader br = new BufferedReader(isr)) {
        StringBuilder sb = new StringBuilder("");
        String line = br.readLine();
        while (null != line) {
          sb.append(line).append("\n");
          line = br.readLine();
        }
        long length = System.nanoTime() - start;
        time += length;
        logger.log(Level.INFO, "Buffered reader size: {0}, time: {1}", new Object[] {sb.length(), time}); 
      }
    }
    this.benchmark.put("Buffered reader", new Long(time / LOOPS));
  }
  
  private void commonsIOExample() throws IOException {
    long time = 0;
    for (int i = 0; i < LOOPS; i++) {
      long start = System.nanoTime();
      try (InputStream is = this.getInputStream()) {
        String input = IOUtils.toString(is, Charset.forName("UTF-8"));
        long length = System.nanoTime() - start;
        time += length;
        logger.log(Level.INFO, "Commons IO size: {0}, time: {1}", new Object[] {input.length(), time}); 
      }
    }
    this.benchmark.put("Commons IO", new Long(time / LOOPS));
  }
  
  private void guavaExample() throws IOException {
    long time = 0;
    for (int i = 0; i < LOOPS; i++) {
      long start = System.nanoTime();
      try (InputStream is = this.getInputStream();
          InputStreamReader isr = new InputStreamReader(is, Charset.forName("UTF-8"))) {
        String input = CharStreams.toString(isr);
        long length = System.nanoTime() - start;
        time += length;
        logger.log(Level.INFO, "Commons IO size: {0}, time: {1}", new Object[] {input.length(), time}); 
      }
    }
    this.benchmark.put("Guava", new Long(time / LOOPS));
  }
  
  private void commonsIOWithStreamWriterExample() throws IOException {
    long time = 0;
    for (int i = 0; i < LOOPS; i++) {
      long start = System.nanoTime();
      try (InputStream is = this.getInputStream()) {
        StringWriter sw = new StringWriter();
        IOUtils.copy(is, sw, Charset.forName("UTF-8"));
        String input = sw.toString();
        long length = System.nanoTime() - start;
        time += length;
        logger.log(Level.INFO, "Commons IO size: {0}, time: {1}", new Object[] {input.length(), time}); 
      }
    }
    this.benchmark.put("Commons IO with Stream Writer", new Long(time / LOOPS));
  }
  
  private void showBenchmark() {
    StringBuilder sb = new StringBuilder("\n===================\n");
    for (Map.Entry<String, Long> entry : this.benchmark.entrySet()) {
      sb.append(entry.getKey()).append(" = ").append(entry.getValue()).append("\n");
    }
    logger.log(Level.INFO, "{0}", sb);
  }
}
