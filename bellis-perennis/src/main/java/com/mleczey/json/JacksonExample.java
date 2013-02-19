package com.mleczey.json;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JacksonExample {
  private static final Logger logger = Logger.getLogger(JacksonExample.class.getName());
  
  public static void main(String[] args) {
    try {
      JacksonExample j = new JacksonExample();
      j.run();
    } catch (URISyntaxException | IOException x) {
      logger.log(Level.INFO, "Error", x);
    }
  }
  
  private void run() throws URISyntaxException, IOException {
    StringBuilder sb = new StringBuilder("");
    JsonFactory factory = new JsonFactory();
    try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("resource.json");
         JsonParser parser = factory.createJsonParser(is)) {
      JsonToken token = parser.nextToken();
      while (null != token) {
        if (token.isScalarValue()) {
          sb.append(parser.getCurrentName()).append(" => ").append(parser.getText()).append("\n");
        }
        token = parser.nextToken();
      }
    }
    logger.log(Level.INFO, "{0}", sb);
    
    try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("resource.json")) {
      ObjectMapper mapper = new ObjectMapper();
      Widget widget = mapper.readValue(is, Widget.class);
      logger.log(Level.INFO, "{0}", widget);
    }
  }
  
  private static class Widget {
    private String debug;
    private Window window;

    public String getDebug() {
      return this.debug;
    }

    public void setDebug(String debug) {
      this.debug = debug;
    }

    public Window getWindow() {
      return this.window;
    }

    public void setWindow(Window window) {
      this.window = window;
    }

    @Override
    public String toString() {
      return String.format("Widget[debug=%s, window=%s]", this.debug, this.window);
    }
  }
  
  public static class Window {
    private String title;
    private String name;
    private int width;
    private int height;

    public String getTitle() {
      return this.title;
    }
    
    public void setTitle(String title) {
      this.title = title;
    }

    public String getName() {
      return this.name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public int getWidth() {
      return this.width;
    }

    public void setWidth(int width) {
      this.width = width;
    }

    public int getHeight() {
      return this.height;
    }

    public void setHeight(int height) {
      this.height = height;
    }

    @Override
    public String toString() {
      return String.format("Window[title=%s, name=%s, width=%d, height=%d]", this.title, this.name, this.width, this.height);
    }
  }
}
