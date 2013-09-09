package com.mleczey.pattern.creational;

import java.util.Random;

public class Shovel {
  private int strength;
  
  public Shovel() {
    this.strength = new Random().nextInt(10) + 1;
  }

  public int getStrength() {
    return this.strength;
  }
}
