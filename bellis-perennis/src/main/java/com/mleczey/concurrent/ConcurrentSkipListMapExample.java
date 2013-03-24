package com.mleczey.concurrent;

import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * CouncurrentSkipListMap (ConcurentSkipListSet) provides concurrent alternative to synchronized SortedMap (SortedSet).
 */
public class ConcurrentSkipListMapExample {
  private static final Logger logger = Logger.getLogger(ConcurrentSkipListMapExample.class.getName());
  
  public static void main(String[] args) {
    ConcurrentSkipListMapExample c = new ConcurrentSkipListMapExample();
    c.run();
  }
  
  private void run() {
    ConcurrentNavigableMap<Integer, String> map = new ConcurrentSkipListMap<>();
    map.put(1, "January");
    map.put(2, "February");
    map.put(3, "March");
    map.put(4, "April");
    map.put(5, "May");
    map.put(6, "June");
    map.put(7, "July");
    map.put(8, "August");
    map.put(9, "September");
    map.put(10, "October");
    map.put(11, "November");
    map.put(12, "December");
    
    logger.log(Level.INFO, "{0}", map.descendingMap());
    logger.log(Level.INFO, "{0}", map.firstEntry());
    logger.log(Level.INFO, "{0}", map.lastEntry());
    logger.log(Level.INFO, "{0}", map.floorEntry(5));
    logger.log(Level.INFO, "{0}", map.ceilingEntry(5));
    logger.log(Level.INFO, "{0}", map.lowerEntry(3));
    logger.log(Level.INFO, "{0}", map.higherEntry(5));
    
    logger.log(Level.INFO, "{0}", map.pollFirstEntry());
    logger.log(Level.INFO, "{0}", map.pollLastEntry());
    logger.log(Level.INFO, "{0}", map.descendingMap());
  }
}
