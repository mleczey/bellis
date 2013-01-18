package com.mleczey.basic;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OverloadingExample {
  private static final Logger logger = Logger.getLogger(OverloadingExample.class.getName());
  
  public static void main(String[] args) {
    OverloadingExample o = new OverloadingExample();
    o.run();
  }
  
  private void run() {
    List<Integer> list1 = new ArrayList<>(5);
    this.hasElement(list1, 1);
    
    this.removeExample();
  }

  // Do not overload methods which accept same number of parameter with similar types
  private boolean hasElement(List<Integer> list, Integer i) {
    logger.log(Level.INFO, "- list");
    return list.contains(i);
  }
  
  private boolean hasElement(ArrayList<Integer> list, Integer i) {
    logger.log(Level.INFO, "- array list");
    return list.contains(i);
  }

  private boolean hasElement(LinkedList<Integer> list, Integer i) {
    logger.log(Level.INFO, "- linked list");
    return list.contains(i);
  }
  
  // User radically different types while overloading methods
  private Integer add(Integer i1, Integer i2) {
    return i1 + i2;
  }
  
  private Integer add(Integer i, String s) {
    return i + Integer.parseInt(s);
  }
  
  // Beware of autoboxing while overloading methods
  private void removeExample() {
    List<Integer> numbers = new ArrayList<>(8);
    numbers.add(1);
    numbers.add(2);
    numbers.add(3);
    logger.log(Level.INFO, "Numbers {0}", numbers);
    
    // Compiler choose remove int over remove object.
    // It is best to avoid two overloaded method where one accept Object and
    // another accept primitive type. If you must do that this way, make sure
    // both methods perform identical function.
    numbers.remove(1);
    logger.log(Level.INFO, "Numbers {0}", numbers);
  }

}
