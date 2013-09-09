package com.mleczey.pattern.creational;

import java.util.Stack;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Shed {
  private static final Logger logger = Logger.getLogger(Shed.class.getName());
  
  private static final int CAPACITY = 3;
  
  public static Shed getInstance() {
    return Farm.SHED;
  }
  
  private Stack<Shovel> shovels;
  private Semaphore semaphore;
  
  private Shed() {
    this.shovels = new Stack<>();
    this.semaphore = new Semaphore(CAPACITY, true);
  }

  public Shovel takeShovel() {
    Shovel shovel = null;
    try {
      this.semaphore.acquire();
    } catch (InterruptedException x) {
      logger.log(Level.SEVERE, "Error while acquiring semaphore.", x);
    }
    if (this.shovels.isEmpty()) {
      shovel = new Shovel();
    } else {
      shovel = this.shovels.pop();
    }
    
    return shovel;
  }
  
  public void giveBack(Shovel shovel) {
    this.shovels.push(shovel);
    this.semaphore.release();
  }

  private static class Farm {
    public static final Shed SHED = new Shed();

    private Farm() {
    }
  }
}
