package com.mleczey.pattern.creational;

import java.util.ArrayList;
import java.util.List;

public class Field {
  private static final int NUMBER_OF_MOLE_MOUNDS = 10;
  
  private List<MoleMound> moleMounds;
  
  public Field() {
    this.moleMounds = new ArrayList<>(10);
    for (int i = 0; i < NUMBER_OF_MOLE_MOUNDS; i++) {
      this.moleMounds.add(new MoleMound());      
    }
  }
  
  public synchronized MoleMound searchForMole() {
    return this.moleMounds.remove(0);
  }
  
  public synchronized boolean hasMoles() {
    return !this.moleMounds.isEmpty();
  }
}
