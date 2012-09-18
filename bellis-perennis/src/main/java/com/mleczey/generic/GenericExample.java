package com.mleczey.generic;

import java.util.Arrays;
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
 * - elimination of casts
 * - do not throw ClassCastException
 * - enable to implement generic algorithms
 * 
 * Notes:
 * - compared to C++ templates, parameterized class is compiled only once, single class file
 * - bounded type parameters in generics - limiting types parameters, for example <T extends Number>,
 *     allows to call method of Number without casting type parameter into Number
 * 
 * Restrictions:
 * - cannot be applied to primitive type
 * - cannot create instance of type parameter, workaround:
 *   public static <E> void foo(List<E> list, Class<E> clss) throws Exception {
 *     E e = clss.newInstance();
 *     list.add(e);
 *   }
 * - cannot declare static fields whose types are type parameters
 * - cannot use casts or instanceof with parameterized types
 * - cannot create array of parameterized types:
 *   List<Integer>[] list = new List<Integer>[2];
 * - cannot create, catch or throw objects with parameterized types
 * - cannot overload a method where the formal parameter types of each overload
 *   erase to the same raw type:
 *   - public void foo(Set<String> set) { ...
 *   - public void foo(Set<Integer> set) { ...
 * 
 * Good practices:
 * - use generics instead of raw objects
 * - bound type parameter (? extends/super)
 * - use @SuppressedWarning("unchecked")
 * - use diamond operator primarily to initialize a variable where it is declared
 * 
 * Naming conventions:
 * - E - element
 * - K - key
 * - N - number
 * - T - type
 * - V - value
 * - S, U, V - next types...
 * 
 * Type erasure:
 * - bridge methods help preserve polymorphism of generic types after type erasure
 */
public class GenericExample {
  private static final Logger logger = Logger.getLogger(GenericExample.class.getName());
  
  public static void main(String[] args) {
    GenericExample e = new GenericExample();
    e.run();
  }
  
  private void run() {
    Cave<Integer> cave = new Cave<>(100);
    Cave<String> lair = Cave.create("Cookie");
    
    logger.log(Level.INFO, "Basket {0}\nContainer {1}", new Object[] {cave, lair});
    
    cave.setBugs(new Integer[] {1, 2, 3, 4, 5});
    cave.setBugs(new Integer[] {7, 8});
    
    logger.log(Level.INFO, "Bugs length {0} {1} {2} {3} {4}", cave.getBugs());
    
    { // Set<T> is subtype of raw type Set
      Set set = new HashSet<String>();
      set = new HashSet<Integer>();
    }
    
    { // Set<Object> can store anything
      Set<Object> set = new HashSet<Object>();
      set.add("abc");
      set.add(0.5);
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
    
    { // multiple bounds, class must be specified first
      //class C<T extends A & B>  {
    }
    
    { // not allowed
      Set<Integer> set = new HashSet(); // warning assigning raw type to a parameterized type
    }
    
    {
      // not allowed (wildcard capture)
      /* Compiler is not able to confirm the type of object that is being inserted
       * int the list. (Error message contains the phrase "capture of".)
       * void foo (List<?> list) {
       *   list.set(0, list.get(0));
       * }
       * 
       * Solution:
       * 
       * void foo (List<?> list) {
       *   fooHelper(i);
       * }
       * 
       * private <T> void fooHelper(List<T> list) {
       *   list.set(0, list.get(0));
       * }
       */
    }
    
    {
      //Set.class; allowed
      //Set<String>.class; not allowed
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
  
  public T[] getBugs() {
    return Arrays.copyOf(this.bugs, CAPACITY);
  }
  
  public void setBugs(T[] bugs) {
    System.arraycopy(bugs, 0, this.bugs, 0, Math.min(CAPACITY, bugs.length));    
  }
  
  public static <T> Cave<T> create(T t) {
    return new Cave<>(t);
  }
  
  @Override
  public String toString() {
    return "Cave[animal=" + this.animal + "]";
  }
}
