package com.mleczey.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * CountDownLatch is a kind of synchronizer which allows one Thread to wait for
 * one or more HTreads before start processing. Any thread, usually main thread
 * of application, which calls await() will wait until count reaches zero or its
 * interrupted by another Thread. All other threads are required to do countDown(),
 * once they are completed or ready to do the job. As soon as count reaches zero
 * thread awaiting starts running. (Disadvantage - it is not reusable, once count
 * reaches to zero, CountDownLatch con not be used any more, use CyclcBarrier instead).
 * 
 * When to use CountDownLatch?
 * - when one thread like main thread, require to wait for one or more thread
 *   to complete, before it starts processing
 * 
 * Notes:
 * - you can use count down latch only once (its not reusable)
 * - waiting thread use .await(), other threads call .countDown()
 */
public class CountDownLatchExample {
  private static final Logger logger = Logger.getLogger(CountDownLatchExample.class.getName());
  
  public static void main(String[] args) {
    CountDownLatchExample example = new CountDownLatchExample();
    example.run();
  }
  
  private void run() {
    try {
      CountDownLatch latch = new CountDownLatch(3);
      new Thread(new LatchTask(2, latch)).start();
      new Thread(new LatchTask(2, latch)).start();
      new Thread(new LatchTask(2, latch)).start();

      latch.await();
      logger.log(Level.INFO, "All threads has ended.");
    } catch (InterruptedException x) {
      logger.log(Level.SEVERE, "Error", x);
    }
  }
  
  private class LatchTask implements Runnable {
    private long timeout;
    private CountDownLatch latch;
    
    private LatchTask(long timeout, CountDownLatch latch) {
      this.timeout = timeout;
      this.latch = latch;
    }
    
    @Override
    public void run() {
      try {
        TimeUnit.SECONDS.sleep(timeout);
        logger.log(Level.INFO, "{0} is up.", Thread.currentThread().getName());
        this.latch.countDown();
      } catch (InterruptedException x) {
        logger.log(Level.INFO, "Error", x);
      }
    }
  }
}
