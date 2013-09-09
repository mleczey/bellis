package com.mleczey.basic;

import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.DatatypeConverter;

public class Base64Example {
  private static final Logger logger = Logger.getLogger(Base64Example.class.getName());
  private static final String EXAMPLE_STRING = "This is example string.";
  
  public static void main(String[] args) {
    Base64Example b = new Base64Example();
    b.run();
  }
  
  private void run() {
    String encoded = DatatypeConverter.printBase64Binary(EXAMPLE_STRING.getBytes(Charset.defaultCharset()));
    logger.log(Level.INFO, "Encoded string {0}", encoded);
  }
}
