package com.mleczey.pattern.creational;

import java.util.Random;
import java.util.Stack;

/**
 * Object pool offers a performance boost, when cost of class instance initialization
 * is high and number of instantiations in use at any one time is low and when you
 * need to frequently create and destroy objects. Object pool
 * manages the object caching, it means that program can avoid creating new objects
 * by simply asking the pool for one that has already been instantiated.
 * 
 * It is recommended to store ale not used and reusable objects in the same object
 * pool. To achieve this, object pool is designed to be a singleton class.
 * - If there are any reusable objects in the pool, then object pool removes that
 * reusable object from the pool and returns it. 
 * - If the pool is empty, then object pool creates new reusable object if it can.
 * If not, it waits until reusable object is returned to pool.
 * 
 * There might be some reasons to limit maximum number of reusable objects in pool.
 */
public class ObjectPoolPattern {
  public static void main(String[] args) {
    ObjectPoolPattern o = new ObjectPoolPattern();
    o.run();
  }
  
  private void run() {
    Shed shed = Shed.getInstance();
    
    Farmer farmer = new Farmer(shed);
    MoleMound moleMound = new MoleMound();
    farmer.searchForMole(moleMound);
  }
}

class Shed {
  private static final int CAPACITY = 5;
  private int numberOfCreatedShovels;
  private Stack<Shovel> shovels;

  private static class Farm {
    public static final Shed SHED = new Shed();

    private Farm() {
    }
  }
  
  private Shed() {
    this.numberOfCreatedShovels = 0;

    this.shovels = new Stack<>();
  }

  public static Shed getInstance() {
    return Farm.SHED;
  }

  public Shovel takeShovel() {
    Shovel shovel = null;
    if (this.shovels.isEmpty()) {
      if (this.numberOfCreatedShovels < CAPACITY) {
        shovel = new Shovel();
        this.numberOfCreatedShovels++;
      } else {
        // wait for free shovel
      }
    } else {
      shovel = this.shovels.pop();
    }
    return shovel;
  }
  
  public void giveBack(Shovel shovel) {
    this.shovels.push(shovel);
  }
}

class Shovel {
  private int strength;
  
  public Shovel() {
    this.strength = new Random().nextInt(10);
  }

  public int getStrength() {
    return this.strength;
  }
}

class Farmer {
  private Shovel shovel;
  private Shed shed;
  
  public Farmer(Shed shed) {
    this.shed = shed;
  }
  
  public void searchForMole(MoleMound moleMound) {
    this.takeShovelFrom();
    this.destroyMoleMound(moleMound);
    this.giveBackShovel();
  }
  
  private void takeShovelFrom() {
    this.shovel = this.shed.takeShovel();
  }
  
  private void destroyMoleMound(MoleMound moleMound) {
    boolean moleMoundIsDestoyed = false;
    while (!moleMoundIsDestoyed) {
      moleMoundIsDestoyed = moleMound.destroyWith(this.shovel);
    }
  }
  
  private void giveBackShovel() {
    this.shed.giveBack(this.shovel);
  }
}

class MoleMound {
  private int endurance;
  
  public MoleMound() {
    this.endurance = new Random().nextInt(20) + 10;
  }

  public boolean destroyWith(Shovel shovel) {
    this.endurance -= shovel.getStrength();
    return 0 > this.endurance;
  }
}
