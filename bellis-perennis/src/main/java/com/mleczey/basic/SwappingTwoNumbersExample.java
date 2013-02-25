package com.mleczey.basic;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SwappingTwoNumbersExample {
  private static final Logger logger = Logger.getLogger(SwappingTwoNumbersExample.class.getName());
  
  public static void main(String[] args) {
    SwappingTwoNumbersExample s = new SwappingTwoNumbersExample();
    s.run();
  }
  
  private void run() {
    this.swappingWithoutTempWithAdditionAndSubstraction();
    this.swappingWithBitwiseOperator();
    this.swappingWithouTempWithDivisionAndMultiplication();
  }

  private void swappingWithoutTempWithAdditionAndSubstraction() {
    int a = 5;
    int b = 3;
    
    logger.log(Level.INFO, "Before swapping {0} {1}", new Object[]{a, b});
    
    a = a + b;
    b = a - b;
    a = a - b;
    
    logger.log(Level.INFO, "After swapping {0} {1}", new Object[]{a, b});
  }
  
  /**
   * Making XOR on operands.
   */
  private void swappingWithBitwiseOperator() {
    int a = 3;
    int b = 7;
    
    logger.log(Level.INFO, "Before swapping {0} {1}", new Object[]{a, b});
    
    a = a ^ b;
    b = a ^ b;
    a = a ^ b;
    
    logger.log(Level.INFO, "After swapping {0} {1}", new Object[]{a, b});
  }
  
  private void swappingWithouTempWithDivisionAndMultiplication() {
    int a = 8;
    int b = 4;
    
    logger.log(Level.INFO, "Before swapping {0} {1}", new Object[]{a, b});
    
    a = a * b;
    b = a / b;
    a = a / b;
    
    logger.log(Level.INFO, "After swapping {0} {1}", new Object[]{a, b});
  }
}
