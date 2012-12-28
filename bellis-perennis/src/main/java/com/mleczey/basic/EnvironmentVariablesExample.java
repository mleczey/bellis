package com.mleczey.basic;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EnvironmentVariablesExample {
  private static final Logger logger = Logger.getLogger(EnvironmentVariablesExample.class.getName());
  
  public static void main(String[] args) {
    EnvironmentVariablesExample e = new EnvironmentVariablesExample();
    e.run();
  }
  
  private void run() {
    logger.log(Level.INFO, "User name system property: {0}", System.getProperty("user.name"));
    logger.log(Level.INFO, "Current directory: {0}", System.getProperty("user.dir"));
    logger.log(Level.INFO, "User name environment variable: {0}", System.getenv("USERNAME"));
    
    StringBuilder environmentVairables = new StringBuilder("\n");
    Map<String, String> variables = System.getenv();
    for (Map.Entry<String, String> entry : variables.entrySet()) {
      environmentVairables.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
    }
    logger.log(Level.INFO, "{0}", environmentVairables);
  }
}
