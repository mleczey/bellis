package com.mleczey.collections;

import java.util.EnumMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * EnumMap is specialized map implementation for use with enum type keys.
 * Notes:
 * - all keys used in EnumMap must be from same enum
 * - EnumMap is ordered collection, order on which enum constant are declared inside enum type
 * - iterators are weakly consistent, they will never throw ConcurrentModificationException
 *   and they may or may not show effects of any modifications to the map, that occur while
 *   the iteration is in progress.
 * - null keys are not permitted, null values are permitted
 * - EnumMap is not synchronized
 * - all basic operations execute in constant time, they should be faster that their HashMap counterparts
 */
public class EnumMapExample {
  private static final Logger logger = Logger.getLogger(EnumMapExample.class.getName());
  
  private enum STATE {
    LOADED, PLAYING, PAUSED, STALLED, ERROR, ENDED
  }
  
  public static void main(String[] args) {
    EnumMapExample e = new EnumMapExample();
    e.run();
  }
  
  private void run() {
    Map<STATE, String> map = new EnumMap<>(STATE.class);
    map.put(STATE.LOADED, "Player has loaded.");
    map.put(STATE.PLAYING, "Player is playing.");
    map.put(STATE.PAUSED, "Player is paused.");
    map.put(STATE.STALLED, "Player is stalled.");
    map.put(STATE.ERROR, "Player has error.");
    map.put(STATE.ENDED, "Player has ended.");
    
    logger.log(Level.INFO, "Map size: {0}", map.size());
    logger.log(Level.INFO, "Map {0}", map);
    logger.log(Level.INFO, "Position of {0}: {1}", new Object[] {STATE.STALLED, map.get(STATE.STALLED)});
    
    Iterator<STATE> i = map.keySet().iterator();
    while (i.hasNext()) {
      STATE state = i.next();
      logger.log(Level.INFO, "Key: {0} Value: {1}", new Object[] {state, map.get(state)});
    }
  }
}
