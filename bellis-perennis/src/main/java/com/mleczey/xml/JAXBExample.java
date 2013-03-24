package com.mleczey.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlRootElement;

public class JAXBExample {
  private static final Logger logger = Logger.getLogger(JAXBExample.class.getName());
  
  public static void main(String[] args) {
    JAXBExample j = new JAXBExample();
    j.run();
  }
  
  private void run() {
    try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("resource.xml")) {
      JAXBContext context = JAXBContext.newInstance(Widget.class, Window.class);
      Widget widget = (Widget)context.createUnmarshaller().unmarshal(is);
      logger.log(Level.INFO, "{0}", widget);
    } catch (JAXBException | IOException x) {
      logger.log(Level.SEVERE, "Error", x);
    }
  }

  @XmlRootElement
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
  
  private static class Window {
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
