package com.mleczey.concurrent;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Counting semaphore maintains specified number of pass or permits. In order to
 * access shared resource Thread acquire() permit. It permit is not available, 
 * Thread waits for other Thread, that is inside shared resource, to release()
 * semaphore.
 * Semaphore can be used to:
 * - database connection pool (block if there are no connections available, without
 *   failing)
 * - bounding collection classes
 * Counting semaphore with one permit is known as binary semaphore.
 * Binary semaphore can be used to implement:
 * - mutual exclusion
 * - critical section.
 * Other "aquire" methods
 * => tryAquire() - returns immediately with true, if aquire permit, false if not
 * => aquireUninterruptibly() - does not fail when interrupted by other thread
 */
public class SemaphoreExample {
  private static final Logger logger = Logger.getLogger(SemaphoreExample.class.getName());
  
  public static final int MAX_NUMBER_OF_EATING_RABBITS = 1;
  public static final int NUMBER_OF_RABBITS = 5;
  
  private Semaphore semaphore;
  private Rabbit[] rabbits;  
  
  public SemaphoreExample() {
     this.semaphore = new Semaphore(MAX_NUMBER_OF_EATING_RABBITS);
  }
  
  public static void main(String[] args) {
    SemaphoreExample example = new SemaphoreExample();
    example.run();
  }
  
  private void run() {
    this.createRabbits();
    this.feedRabbits();
  }
  
  private void createRabbits() {
    this.rabbits = new Rabbit[NUMBER_OF_RABBITS];
    for (int i = 0; i < NUMBER_OF_RABBITS; i++) {
      this.rabbits[i] = new Rabbit(i, semaphore);
    }
  }
  
  private void feedRabbits() {
    for (int i = 0; i < NUMBER_OF_RABBITS; i++) {
      new Thread(this.rabbits[i]).start();
    }
  }
  
  private class Rabbit implements Runnable {
    private int id;
    private Semaphore semaphore;
    
    private Rabbit(int id, Semaphore semaphore) {
      this.id = id;
      this.semaphore = semaphore;
    }
    
    @Override
    public void run() {
      try {
        logger.log(Level.INFO, "Rabbit {0} wants to eat.", this.id);
        this.semaphore.acquire();
        logger.log(Level.INFO, "Rabbit {0} is eating...", this.id);
        TimeUnit.SECONDS.sleep(2);
      } catch (InterruptedException x) {
        logger.log(Level.SEVERE, "Rabbit got moldy carrot.", x);
      } finally {
        logger.log(Level.INFO, "Rabbit {0} stopped eating...", this.id);
        this.semaphore.release();
      }
    }
  }
}
