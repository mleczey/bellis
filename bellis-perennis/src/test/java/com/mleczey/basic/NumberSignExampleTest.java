package com.mleczey.basic;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;

@RunWith(JUnitParamsRunner.class)
public class NumberSignExampleTest {
  private NumberSignExample example;
  
  @Before
  public void setUpBeforeClass() {
    this.example = new NumberSignExample();
  }
  
  private Object[] integerNumbers() {
    return new Object[] {
      new Object[] {Integer.MAX_VALUE, true},
      new Object[] {1, true},
      new Object[] {0, true},
      new Object[] {-1, false},
      new Object[] {Integer.MIN_VALUE, false},
    };
  }
  
  @Test
  @Parameters(method = "integerNumbers")
  public void signForIntegersShouldBeRecognized(int number, boolean expected) {
    //when
    boolean actual = this.example.isPositive(number);
    //then
    assertEquals(actual, expected);
  }
  
  private Object[] doubleNumbers() {
    return new Object[] {
      new Object[] {Double.POSITIVE_INFINITY, true},
      new Object[] {Double.MAX_VALUE, true},
      new Object[] {1.0, true},
      new Object[] {0.1, true},
      new Object[] {Double.MIN_VALUE, true},
      new Object[] {0.0, true},
      new Object[] {-0.1, false},
      new Object[] {-1.0, false},
      new Object[] {-Double.MAX_VALUE, false},
      new Object[] {Double.NEGATIVE_INFINITY, false},
    };
  }
  
  @Test
  @Parameters(method = "doubleNumbers")
  public void signForDoublesShouldBeRecognized(double number, boolean expected) {
    //when
    boolean actual = this.example.isPositive(number);
    //then
    assertEquals(actual, expected);
  }
  
  private Object[] longNumbers() {
    return new Object[] {
      new Object[] {Long.MAX_VALUE, true},
      new Object[] {1L, true},
      new Object[] {0L, true},
      new Object[] {-1L, false},
      new Object[] {Long.MIN_VALUE, false},
    };
  }
  
  @Test
  @Parameters(method = "longNumbers")
  public void signForLongsShouldBeRecognized(long number, boolean expected) {
    //when
    boolean actual = this.example.isPositive(number);
    //then
    assertEquals(actual, expected);
  }
  
  private Object[] floatNumbers() {
    return new Object[] {
      new Object[] {Float.POSITIVE_INFINITY, true},
      new Object[] {Float.MAX_VALUE, true},
      new Object[] {1.0f, true},
      new Object[] {0.1f, true},
      new Object[] {Float.MIN_VALUE, true},
      new Object[] {0.0f, true},
      new Object[] {-0.1f, false},
      new Object[] {-1.0f, false},
      new Object[] {-Float.MAX_VALUE, false},
      new Object[] {Float.NEGATIVE_INFINITY, false},
    };
  }
  
  @Test
  @Parameters(method = "floatNumbers")
  public void signForFloatsShouldBeRecognized(float number, boolean expected) {
    //when
    boolean actual = this.example.isPositive(number);
    //then
    assertEquals(actual, expected);
  }
}
