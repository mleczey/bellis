package com.mleczey.pattern.creational;

import java.util.Random;

public class MoleMound {
  private int endurance;
  
  public MoleMound() {
    this.endurance = new Random().nextInt(20) + 10;
  }

  public void destroyWith(Shovel shovel) {
    this.endurance -= shovel.getStrength();
  }
  
  public boolean isDestroyed() {
    return 0 > this.endurance;
  }
}
