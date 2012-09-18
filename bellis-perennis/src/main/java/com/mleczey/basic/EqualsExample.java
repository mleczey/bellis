package com.mleczey.basic;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Equals method should be:
 * - reflexive - Rabbit A == Rabbit A
 * - symmetric - if Rabbit A == Rabbit B then Rabbit B == Rabbit A
 * - transitive - if Rabbit A == Rabbit B and Rabbit B == Rabbit C then Rabbit A == Rabbit C
 * - consistent - return same value, until any of properties is modified
 * - null comparison - no NullPointerException, comparison to null, should return false
 * 
 * Equals and hash code dependencies
 * - if Rabbit A == Rabbit B then hash codes must be same
 * - if Rabbit A != Rabbit B then hash codes could be same or different
 * 
 * Errors:
 * - overloading instead of overriding, ie. public boolean equals(Rabbit o) {...
 * - no null checks for member variables, before equals invoking
 * - override equals with hashCode, otherwise value object will not be able to use
 *   as key object in HashMap
 * - not keeping equals and compareTo methods consistent
 */
public class EqualsExample {
  public static void main(String[] args) {
    EqualsExample e = new EqualsExample();
    e.run();
  }
  
  private void run() {
    Rabbit green = new Rabbit("green");
    Rabbit forest = new Rabbit("green");
    Rabbit savanna = new Rabbit("green");
    Rabbit lava = new Rabbit("red");
    
    Logger.getAnonymousLogger().log(Level.INFO, 
          "\nIs green rabbit same as green rabbit? {0}"
        + "\nIs green rabbit same as forest rabbit? {1}"
        + "\nIs forest rabbit same as green rabbit? {2}"
        + "\nIs forest rabbit same as savanva rabbit? {3}"
        + "\nIs green rabbit same as savanna rabbit? {4}"
        + "\nIs lava rabbit same as forest rabbit? {5}",
        new Object[] {
          green.equals(green),
          green.equals(forest),
          forest.equals(green),
          forest.equals(savanna),
          green.equals(savanna),
          lava.equals(forest)
        });
  }
  
  class Rabbit {
    private String name;
    
    public Rabbit(String name) {
      this.name = name;
    }

    public String getName() {
      return this.name;      
    }

    @Override
    public int hashCode() {
      return (null == this.name) ? 0 : 3 * this.name.hashCode();
    }
    
    @Override
    public boolean equals(Object o) {
      boolean result = false;
      if (this == o) {
        result = true;
      } else if (null != o && this.getClass() == o.getClass()) {
        Rabbit rabbit = (Rabbit)o;
        if ((null == this.name && null == rabbit.getName()) || this.name.equals(rabbit.getName())) {
          result = true;
        }
      }
      return result;
    }
  }
}
