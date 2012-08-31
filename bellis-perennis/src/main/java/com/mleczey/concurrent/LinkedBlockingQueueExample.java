package com.mleczey.concurrent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Blocking Queue is a collection that allows operations, that wait if:
 * - there is no space available in queue to store element,
 * - queue is empty, while retrieving element.
 * Blocking Queue has four types of methods to add/remove elements to/from queue, that:
 * - throw exception,
 * - return special value (for example: null),
 * - block execution,
 * - with time out.
 * 
 * Producer Consumer Pattern
 * - can code Producer and Consumer independently, only shared object is need to known
 * - producer and consumer do not have to know about each other, and how many of them
 *   there is
 * - producer and consumer can work with different speed, after monitoring can introduce more resources
 *   (consumers, producers) for better performance
 */
public class LinkedBlockingQueueExample {
  private static final Logger logger = Logger.getLogger(LinkedBlockingQueueExample.class.getName());
  
  public static final int NUMBER_OF_FARMERS = 1;
  public static final int NUMBER_OF_FIELDS = 1;
  public static final int NUMBER_OF_RABBITS = 2;
  
  public static final int SEEDS_THROWING_FREQUENCY_IN_SECONDS = 5;
  public static final int MIN_NUMBER_OF_SEEDS_IN_ONE_THROW = 1;
  public static final int MAX_NUMBER_OF_SEEDS_IN_ONE_THROW = 2;
  public static final int MAX_FIELD_CAPACITY = 1;
  public static final int NUMBER_OF_CARROTS_FROM_ONE_SEED = 2;
  
  public static final int CARROT_BOWL_CAPACITY = 10;
  
  private BlockingQueue<Seed> seedsQueue;
  private BlockingQueue<Carrot> carrotBowl;
  
  private Farmer[] farmers;
  private Field[] fields;
  private Rabbit[] rabbits;
  
  public LinkedBlockingQueueExample() {
    this.farmers = new Farmer[NUMBER_OF_FARMERS];
    this.fields = new Field[NUMBER_OF_FIELDS];
    this.rabbits = new Rabbit[NUMBER_OF_RABBITS];
    
    this.seedsQueue = new LinkedBlockingQueue<>();
    this.carrotBowl = new LinkedBlockingQueue<>(CARROT_BOWL_CAPACITY);
  }
  
  public static void main(String[] args) {
    LinkedBlockingQueueExample example = new LinkedBlockingQueueExample();
    example.run();
  }
  
  private void run() {
    this.initActors();
    this.startScene();
    this.listenForEnd();
  }
  
  private void initActors() {
    for (int i = 0; i < NUMBER_OF_FARMERS; i++) {
      this.farmers[i] = new Farmer(i, this.seedsQueue);
    }
    
    for (int i = 0; i < NUMBER_OF_FIELDS; i++) {
      this.fields[i] = new Field(i, MAX_FIELD_CAPACITY, this.seedsQueue, this.carrotBowl);
    }
    
    for (int i = 0; i < NUMBER_OF_RABBITS; i++) {
      this.rabbits[i] = new Rabbit(i, this.carrotBowl);
    }
  }
  
  private void startScene() {
    for (int i = 0; i < NUMBER_OF_FARMERS; i++) {
      new Thread(this.farmers[i]).start();
    }
    
    for (int i = 0; i < NUMBER_OF_FIELDS; i++) {
      new Thread(this.fields[i]).start();
    }
    
    for (int i = 0; i < NUMBER_OF_RABBITS; i++) {
      new Thread(this.rabbits[i]).start();
    }
  }
  
  private void listenForEnd() {
    Scanner in = new Scanner(System.in);
    String line = in.next();
    while (!line.equals("end")) {
      line = in.next();
    }
    
    this.stopScene();
    this.viewInformation();
  }
  
  private void stopScene() {
    for (int i = 0; i < NUMBER_OF_FARMERS; i++) {
      this.farmers[i].stopThrowing();
    }
    
    for (int i = 0; i < NUMBER_OF_FIELDS; i++) {
      this.fields[i].stopGrowing();
    }
    
    for (int i = 0; i < NUMBER_OF_RABBITS; i++) {
      this.rabbits[i].stopEating();
    }
  }
  
  private void viewInformation() {
    logger.log(Level.INFO, "Remaining seeds {0}\nRemaining carrots {1}", new Object[] {this.seedsQueue.size(), this.carrotBowl.size()});
  }
  
  private class Farmer implements Runnable {    
    private int id;
    private BlockingQueue<Seed> seedsQueue;
    private boolean stop;
    private Random random;
    
    private Farmer(int id, BlockingQueue<Seed> seedsQueue) {
      this.id = id;
      this.seedsQueue = seedsQueue;
      this.stop = false;
      this.random = new Random();
    }
    
    @Override
    public void run() {
      try {
        while (!this.stop) {
          this.harvestSeeds();
          this.throwSeeds();
        }
      } catch (InterruptedException x) {
        logger.log(Level.SEVERE, "Error while throwing seeds!", x);
      }
    }
    
    private void harvestSeeds() throws InterruptedException {
      logger.log(Level.INFO, "Farmer {0} is harvesting seeds...", this.id);
      TimeUnit.SECONDS.sleep(SEEDS_THROWING_FREQUENCY_IN_SECONDS);
    }
    
    private void throwSeeds() throws InterruptedException {
      int numberOfSeeds = MIN_NUMBER_OF_SEEDS_IN_ONE_THROW + this.random.nextInt(MAX_NUMBER_OF_SEEDS_IN_ONE_THROW - MIN_NUMBER_OF_SEEDS_IN_ONE_THROW);
      for (int i = 0; i < numberOfSeeds; i++) {
        Seed seed = new Seed("[Farmer-" + this.id + "]");
        logger.log(Level.INFO, "Farmer {0} is throwing seed {1}.", new Object[] {this.id, seed});
        this.seedsQueue.put(seed);
      }
    }
    
    public void stopThrowing() {
      this.stop = true;
    }
  }
  
  private class Seed {
    private String id;
    
    private Seed(String id) {
      this.id = id;
    }

    @Override
    public String toString() {
      return "[Seed]<-" + this.id;
    }
  }
  
  private class Field implements Runnable {
    private int id;
    private int capacity;
    private BlockingQueue<Seed> seedsQueue;
    private BlockingQueue<Carrot> carrotBowl;
    private boolean stop;
    private Random random;
    private List<Seed> seeds;
    
    private Field(int id, int capacity, BlockingQueue<Seed> seedsQueue, BlockingQueue<Carrot> carrotBowl) {
      this.id = id;
      this.capacity = capacity;
      this.seedsQueue = seedsQueue;
      this.carrotBowl = carrotBowl;
      this.stop = false;
      this.random = new Random();
      this.seeds = new ArrayList<>(this.capacity);
    }
    
    @Override
    public void run() {
      try {
        while (!this.stop) {
          logger.log(Level.INFO, "Planting seeds...");
          this.plantSeeds();
          logger.log(Level.INFO, "Growing carrots...");
          this.growCarrots();
        }
      } catch(InterruptedException x) {
        logger.log(Level.SEVERE, "Error while growin carrots!", x);
      }
    }
    
    private void plantSeeds() throws InterruptedException {
      int numberOfSeeds = this.seedsQueue.drainTo(this.seeds, this.capacity);
      logger.log(Level.INFO, "On field {0} fall {1} number of seeds.", new Object[] {this.id, numberOfSeeds});
      for (int i = 0; i < this.seeds.size(); i++) {
        logger.log(Level.INFO, "Field {0} is growing carrot from seed {1}.", new Object[] {this.id, this.seeds.get(i)});
      }
    }
    
    private void growCarrots() throws InterruptedException {
      TimeUnit.SECONDS.sleep(3 + this.random.nextInt(2));
      Iterator<Seed> iterator = this.seeds.iterator();
      while (iterator.hasNext()) {
        Seed seed = iterator.next();
        iterator.remove();
        for (int i = 0; i < NUMBER_OF_CARROTS_FROM_ONE_SEED; i++) {
          Carrot carrot = new Carrot("[Field-" + this.id + "]<-" + seed.toString());
          logger.log(Level.INFO, "Field {0} grow carrot {1} from seed {2}.", new Object[] {this.id, carrot, seed});
          this.carrotBowl.add(carrot);
        }
      }
    }
    
    public void stopGrowing() {
      this.stop = true;
    }
  }
  
  private class Carrot {
    private String id;
    
    private Carrot(String id) {
      this.id = id;
    }

    @Override
    public String toString() {
      return "[Carrot]<-" + this.id;
    }
  }
  
  private class Rabbit implements Runnable {
    private int id;
    private BlockingQueue<Carrot> carrotBowl;
    private boolean stop;
    private Random random;
    
    private Rabbit(int id, BlockingQueue<Carrot> carrotBowl) {
      this.id = id;
      this.carrotBowl = carrotBowl;
      this.stop = false;
      this.random = new Random();
    }
    
    @Override
    public void run() {
      try {
        while (!this.stop) {
          this.sleep();
          this.eatCarrots();
        }
      } catch (InterruptedException x) {
        logger.log(Level.SEVERE, "Error while eating carrot!", x);
      }
    }
    
    private void sleep() throws InterruptedException {
      logger.log(Level.INFO, "Rabbit {0} is sleeping...", this.id);
      TimeUnit.SECONDS.sleep(1 + this.random.nextInt(2));
    }
    
    private void eatCarrots() throws InterruptedException {
      Carrot carrot = this.carrotBowl.poll(2, TimeUnit.SECONDS);
      if (null != carrot) {
        logger.log(Level.INFO, "Rabbit {0} is eating carrot {1}.", new Object[] {this.id, carrot});
      }
    }
    
    public void stopEating() {
      this.stop = true;
    }
  }
}
