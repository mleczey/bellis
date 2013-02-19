package com.mleczey.concurrent;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Alternatives: CountDownLatch and CyclicBarrier
 * Primary use of join method is to wait for another thread and start execution once that Thread has completed execution or dies. It is also a blocking method,
 * which blocks until thread on which join has called die or specified waiting time is over.
 * - join is final method
 * - throws InterruptedException, if another thread interrupted waiting thread as a result of join call
 * 
 */
public class JoiningThreadsExample {
  private static final Logger logger = Logger.getLogger(JoiningThreadsExample.class.getName());
  
  public static void main(String[] args) {
    try {
      JoiningThreadsExample j = new JoiningThreadsExample();
      j.run();
    } catch (InterruptedException x) {
      logger.log(Level.SEVERE, "Error", x);
    }
  }
  
  private void run() throws InterruptedException {
    logger.log(Level.INFO, "Start");
    Thread thread = new Thread(new Runner());
    thread.start();
    //this thread waits until thread created here is completed
    thread.join();
    logger.log(Level.INFO, "End");
  }
  
  class Runner implements Runnable {
    @Override
    public void run() {
      try {
        logger.log(Level.INFO, "{0} is started.", Thread.currentThread().getName());
        Thread.sleep(2000);
        logger.log(Level.INFO, "{0} is completed.", Thread.currentThread().getName());
      } catch (InterruptedException x) {
        logger.log(Level.SEVERE, "Error", x);
      }
    }
  }
}
