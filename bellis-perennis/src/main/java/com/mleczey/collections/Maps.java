package com.mleczey.collections;

import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Difference between HashMap and HashTable
 * 
 * HashMap:
 * - not synchronized
 * - permits nulls as key and value
 * - does not guarantee that the order of the map will remain constant over time
 * - fail-fast enumerator
 * HashTable:
 * - synchronized
 * - doesn't allow nulls
 * - throws ConcurrentModificationException if any other Thread modifies the map
 *   structurally by adding or removing any element expect iterator's own remove()
 * 
 * Synchronized - only one thread can modify a hash table at one point of time.
 * Fail-fast - if an iterator has been created on a collection object and some
 * other thread tries to modify the collection object structurally, a concurrent
 * modification exception will be thrown. It is possible for other threads though
 * to invoke "set" method since it doesn't modify the collection "structurally".
 * However, if prior to calling "set", the collection has been modified structurally,
 * "IllegalArgumentExecption" will be thrown.
 * Structural modification - deleting or inserting element which could effectively
 * change the structure of map.
 * 
 * ConcurrentHashMap, CopyOnWriteArrayList, Collections.synchronizedMap, 
 * Collections.synchronizedList, Hashtable, Vector
 * 
 * Difference between ConcurrentHashMap and Hashtable
 * - both are used in multithreaded environments, concurrent hash map introduces
 *   concept of segmentation, concurrent hash map only locks certain portion of map,
 *   while hash table locks full map.
 * 
 * Difference between ConcurrentHashMap and Collections.synchronizedMap
 * - ConcurrentHashMap does not allow null keys or null values.
 */
public class Maps {
  private static final Logger logger = Logger.getLogger(Maps.class.getName());
  
  public static void main(String[] args) {
    Maps maps = new Maps();
    maps.run();
  }
  
  private void run() {
    Map<String, String> map = new HashMap<>(10);
    Map<String, String> table = new Hashtable<>(10);
    
    map.put(null, null);
    try {
      table.put(null, null);
    } catch (NullPointerException x) {
      logger.log(Level.SEVERE, "You cannot put null object to hash table.");
    }
    
    Map<String, String> synchronizedMap = Collections.synchronizedMap(map);
  }
}
