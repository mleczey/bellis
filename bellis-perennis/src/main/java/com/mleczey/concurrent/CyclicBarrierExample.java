package com.mleczey.concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * CyclicBarrier differs from CountDownLatch, that it can be reused. Both are used
 * to implement threads waiting for each other. While CountDownLatch is good for one
 * time event like application startup, CyclicBarrier can be used in case of
 * recurrent event, for example concurrently calculating solution of big problem.
 * 
 * When to use CycylicBarrier in Java?
 * It can be handy to implement map reduce kind of task similar to fork join in JDK7,
 * where a big task is broken down into smaller pieces and to complete the task you
 * need output from individual small task.
 * You can use CyclicBarrier in Java:
 * - to implement multi player game which can not begin until all players has joined
 * - perform lengthy calculation by breaking it into smaller individual tasks
 *   (Map reduce technique)
 * 
 * Important notes:
 * - CyclicBarrier can perform a completion task once all thread reaches to barrier.
 * - If CycliBarrier is initialized with 3 parties means, 3 threads needs to call
 *   await method to break the barrier.
 * - threads will block on await() until all threads reach to barrier
 * - if another thread interrupts the thread which is waiting on barrier it will throw
 *   BrokenBarrierException
 * - CyclicBarrier.reset() puts barrier on its initial state, other thread which is
 *   waiting or not yet reached barrier will terminate with BrokenBarrierException.
 */
public class CyclicBarrierExample {
  private static final Logger logger = Logger.getLogger(CyclicBarrierExample.class.getName());
  
  public static void main(String[] args) {
    CyclicBarrierExample example = new CyclicBarrierExample();
    example.run();
  }
  
  private void run() {
    CyclicBarrier barrier = new CyclicBarrier(3, new Runnable() {
      @Override
      public void run() {
        logger.log(Level.INFO, "Everyone have reached the barrier!");
      }
    });
    
    new Thread(new BarrierTask(barrier)).start();
    new Thread(new BarrierTask(barrier)).start();
    new Thread(new BarrierTask(barrier)).start();
  }
  
  private class BarrierTask implements Runnable {
    private CyclicBarrier barrier;
    
    private BarrierTask(CyclicBarrier barrier) {
      this.barrier = barrier;
    }
    
    @Override
    public void run() {
      try {
        logger.log(Level.INFO, "Thread {0} waiting on the barrier.", Thread.currentThread().getName());
        barrier.await();
        logger.log(Level.INFO, "Thread {0} has crossed the barrier.", Thread.currentThread().getName());
      } catch (InterruptedException x) {
        logger.log(Level.SEVERE, "Error", x);
      } catch (BrokenBarrierException x) {
        logger.log(Level.SEVERE, "Barrier has been breached!", x);
      }
    }
  }
}
