package com.mleczey.generic;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Benefits of using generic parameterized class and method:
 * - compile time type verification
 * - you do not need to cast
 * - do not throw ClassCastException, because type verification was already done
 * 
 * Notes:
 * - cannot be applied to primitive type
 * - compared to C++ templates, parameterized class is compiled only once, single class file
 * - bounded type parameters in generics - limiting types parameters, for example <T extends Number>,
 *     allows to call method of Number without casting type parameter into Number
 * 
 * Good practices:
 * - use generics instead of raw objects
 * - bound type parameter (? extends/super)
 * - use @SuppressedWarning("unchecked")
 */
public class GenericExample {
  private static final Logger logger = Logger.getLogger(GenericExample.class.getName());
  
  public static void main(String[] args) {
    GenericExample e = new GenericExample();
    e.run();
  }
  
  private void run() {
    Cave<Integer> basket = new Cave<>(100);
    Cave<String> container = Cave.create("Cookie");
    
    logger.log(Level.INFO, "Basket {0}\nContainer {1}", new Object[] {basket, container});
    
    { // Set<T> is subtype of raw type Set
      Set set = new HashSet<String>();
      set = new HashSet<Integer>();
    }
    
    { // Set<Object> can store anything
      Set<Object> set = new HashSet<Object>();
      set.add("abc");
      set.add(0.5);
      //set = new HashSet<Integer>(); Set of objects can have something other than integers
    }
    
    { // ? - wildcard, Set<?> - set of unknown type
      Set<?> set = new LinkedHashSet<String>();
      set = new LinkedHashSet<Integer>();
    }
    
    { // HashSet<String> and LinkedHashSet<String> are sub types of Set<String> - inhteritance at main type
      Set<String> set = new HashSet<String>();
      set = new LinkedHashSet<String>();
      //Set<Object> set = new HashSet<String>(); inheritance on type parameter
    }
    
    { // Set<? extends Number> will store Number or sub types of Number
      Set<? extends Number> set = new HashSet<Integer>();
      set = new HashSet<Float>();
    }
    
    { // Set<? super TreeMap> will store instances of TreeMap or super classes of TreeMap
      Set<? super TreeMap> set = new LinkedHashSet<TreeMap>();
      set = new HashSet<SortedMap>();
      set = new LinkedHashSet<Map>();
    }
    
    {
      //Set.class; allowed
      //Set<String>.class; no allowed
    }
    
  }
}

class Cave<T> {
  public static final int CAPACITY = 5;
  
  private T animal;
  private T[] bugs;
  
  @SuppressWarnings("unchecked")
  protected Cave(T t) {
    this.animal = t;
    //this.bugs = new T[]; no allowed
    this.bugs = (T[])new Object[CAPACITY];
  }
  
  public T getAnimal() {
    return this.animal;
  }
  
  public void setAnimal(T t) {
    this.animal = t;
  }
  
  public static <T> Cave<T> create(T t) {
    return new Cave<>(t);
  }
  
  @Override
  public String toString() {
    return "Cave[animal=" + this.animal + "]";
  }
}
