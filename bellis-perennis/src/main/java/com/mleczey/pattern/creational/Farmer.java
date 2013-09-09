package com.mleczey.pattern.creational;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Farmer extends Thread {
  private static final Logger logger = Logger.getLogger(Farmer.class.getName());
  
  private Shed shed;
  private Field field;
  private int motivation;
  
  public Farmer(Shed shed, Field field) {
    logger.log(Level.INFO, "Farmer {0} will work on field.", super.getName());
    this.shed = shed;
    this.field = field;
    this.motivation = new Random().nextInt(5) + 1;
  }
  
  @Override
  public void run() {
    while (this.field.hasMoles()) {
      MoleMound moleMound = this.field.searchForMole();
      Shovel shovel = this.shed.takeShovel();

      logger.log(Level.INFO, ">>> Farmer {0} working here with mole mound already destroyed: {1}!", new Object[] {super.getName(), moleMound.isDestroyed()});
      while (!moleMound.isDestroyed()) {
        moleMound.destroyWith(shovel);
        this.rest();
      }
      
      logger.log(Level.INFO, "<<< Farmer {0} returning shovel.", super.getName());
      this.shed.giveBack(shovel);
    }
  }
  
  private void rest() {
    try {
      logger.log(Level.INFO, "Farmer {0} is resting {1} seconds.", new Object[] {super.getName(), this.motivation});
      TimeUnit.SECONDS.sleep(this.motivation);
    } catch (InterruptedException x) {
      logger.log(Level.SEVERE, "Error while digging hole.", x);
    }
  }
}
