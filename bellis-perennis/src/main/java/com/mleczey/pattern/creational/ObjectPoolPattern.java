package com.mleczey.pattern.creational;

import java.util.ArrayList;
import java.util.List;

/**
 * Object pool offers a performance boost, when cost of class instance initialization
 * is high and number of instantiations in use at any one time is low and when you
 * need to frequently create and destroy objects. Object pool
 * manages the object caching, it means that program can avoid creating new objects
 * by simply asking the pool for one that has already been instantiated.
 * 
 * It is recommended to store all not used and reusable objects in the same object
 * pool. To achieve this, object pool is designed to be a singleton class.
 * - If there are any reusable objects in the pool, then object pool removes that
 * reusable object from the pool and returns it. 
 * - If the pool is empty, then object pool creates new reusable object if it can.
 * If not, it waits until reusable object is returned to pool.
 * 
 * There might be some reasons to limit maximum number of reusable objects in pool.
 */
public class ObjectPoolPattern {
  private static final int NUMBER_OF_FARMERS = 4;
  
  public static void main(String[] args) {
    ObjectPoolPattern o = new ObjectPoolPattern();
    o.run();
  }
  
  private void run() {
    Shed shed = Shed.getInstance();
    Field field = new Field();
    
    List<Farmer> farmers = new ArrayList<>(10);
    for (int i = 0; i < NUMBER_OF_FARMERS; i++) {
      Farmer farmer = new Farmer(shed, field);
      farmer.start();
      farmers.add(farmer);
    }
  }
}