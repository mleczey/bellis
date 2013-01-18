package com.mleczey.basic;

/**
 * Methods of checking if a number is positive or negative in Java.
 */
public class NumberSignExample {
  private static final char MINUS_SIGN = '-';
  
  public boolean isNegative(int i) {
    String s = Integer.toString(i);
    return MINUS_SIGN == s.charAt(0);
  }
  
  public boolean isPositive(int i) {
    return !this.isNegative(i);
  }

  public boolean isNegative(double d) {
    return 0 > d;
  }
  
  public boolean isPositive(double d) {
    return !this.isNegative(d);
  }
  
  public boolean isNegative(long l) {
    return !this.isPositive(l);
  }
  
  public boolean isPositive(long l) {
    boolean result = false;
    if (0 == l || (l >> 63) == 0) {
      result = true;
    }
    return result;
  }
  
  public boolean isNegative(float f) {
    return !this.isPositive(f);
  }
  
  public boolean isPositive(float f) {
    return 0.0f == f || 1.0f == Math.signum(f);
  }
}
