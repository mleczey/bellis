package com.mleczey.util;

import java.util.IdentityHashMap;
import java.util.logging.Logger;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Difference between IdentityHashMap and HashMap
 * - IdentityHashMap uses equality operator '==' for comparing keys and values, HashMap uses equals method for comparing keys and values
 * - IdentityHashMap uses System.identityHashCode(object), HasMap uses object's hashCode method
 * - IdentityHashMap is faster than HashMap, because it doesn't use object's equals() or hashCOde()
 * - IdentityHashMap doesn't require keys to be immutable
 */
public class IdentityHashMapExampleTest {
  private static final Logger logger = Logger.getLogger(IdentityHashMapExampleTest.class.getName());
  
  private IdentityHashMap<String, String> map;
  
  @Before
  public void setUp() {
     this.map = new IdentityHashMap<>(10);
  }
  
  @Test
  public void testIdentityHashMap() {
    //given
    this.map.put("rabbit", "carrot");
    this.map.put("parrot", "seed");
    this.map.put("snake", "meat");
    int expected = this.map.size();
    //when
    this.map.put("snake", "grass");
    //then
    assertEquals(expected, this.map.size());
  }
}
