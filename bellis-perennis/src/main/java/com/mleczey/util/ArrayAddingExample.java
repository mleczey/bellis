package com.mleczey.util;

import com.google.common.collect.ObjectArrays;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.ArrayUtils;

/**
 * System.arrayCopy() is the best way to copy one array to another array. Here no merging is done.
 */
public class ArrayAddingExample {
  private static final Logger logger = Logger.getLogger(ArrayAddingExample.class.getName());
  
  public static void main(String[] args) {
    ArrayAddingExample a = new ArrayAddingExample();
    a.run();
  }
  
  private void run() {
    int[] a1 = {1, 2, 3};
    int[] a2 = {4, 5, 6};
    
    long time = System.nanoTime();
    logger.log(Level.INFO, "Apache Commons {0}, time {1}.", new Object[] {Arrays.toString(ArrayUtils.addAll(a1, a2)), System.nanoTime() - time});
    
    time = System.nanoTime();
    logger.log(Level.INFO, "Own Function {0}, time {1}.", new Object[] {Arrays.toString(this.addAll(a1, a2)), System.nanoTime() - time});
    
    String[] s1 = {"a", "b", "c"};
    String[] s2 = {"d", "e", "f"};
    
    time = System.nanoTime();
    logger.log(Level.INFO, "Guava {0}, time {1}.", new Object[]{Arrays.toString(ObjectArrays.concat(s1, s2, String.class)), System.nanoTime() - time});
  }
  
  private int[] addAll(int[] a, int[] b) {
    int length = a.length + b.length;
    int[] result = new int[length];
    System.arraycopy(a, 0, result, 0, a.length);
    System.arraycopy(b, 0, result, a.length, b.length);
    return result;
  }
}
