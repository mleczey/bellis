package com.mleczey.pattern;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Constructor design pattern
 * 
 * When to use this pattern?
 * - when there are too many constructors to maintain (lot of parameters, which for example some of them are not mandatory)
 * - error prone, because many fields has same type, but different measurements (for example flour and butter), compiler will not complain, but cake might be
 *   not eatable
 * - solving this problem with setters might cause Object to be in inconsistent state, cake should not be available until its created
 * 
 * Advantages:
 * - improves readability, more maintainable if number of fields required to create is more than four or five
 * - reduces chance of error, user is aware what is passing
 * - makes object available when it is fully constructed
 * 
 * Disadvantages:
 * - verbose
 * - code duplication in builder
 */
public class BuilderPattern {
  private static final Logger logger = Logger.getLogger(BuilderPattern.class.getName());
  
  public static void main(String[] args) {
    BuilderPattern pattern = new BuilderPattern();
    pattern.run();
  }
  
  private void run() {
    Cake chocolateCake = new Cake.Builder().sugar(2.0).flour(2.5).butter(15).eggs(3).milk(0.5).bakingPowder(0.75).chocolate(20).build();
    logger.log(Level.INFO, "{0}", chocolateCake);
  }
}

class Cake {
  private double sugar;
  private double flour;
  private double butter;
  private int eggs;
  private double milk;
  private double bakingPowder;
  private double chocolate;
  
  public static class Builder {
    private double sugar;
    private double flour;
    private double butter;
    private int eggs;
    private double milk;
    private double bakingPowder;
    private double chocolate;
    
    public Builder sugar(double cup) {
      this.sugar = cup;
      return this;
    }
    
    public Builder flour(double cup) {
      this.flour = cup;
      return this;
    }
    
    public Builder butter(double gramm) {
      this.butter = gramm;
      return this;
    }
    
    public Builder eggs(int number) {
      this.eggs = number;
      return this;
    }
    
    public Builder milk(double cup) {
      this.milk = cup;
      return this;
    }
    
    public Builder bakingPowder(double spoon) {
      this.bakingPowder = spoon;
      return this;
    }
    
    public Builder chocolate(double bar) {
      this.chocolate = bar;
      return this;
    }
    
    public Cake build() {
      return new Cake(this);
    }
  }
  
  private Cake(Builder builder) {
    this.sugar = builder.sugar;
    this.flour = builder.flour;
    this.butter = builder.butter;
    this.eggs = builder.eggs;
    this.milk = builder.milk;
    this.bakingPowder = builder.bakingPowder;
    this.chocolate = builder.chocolate;
  }
  
  @Override
  public String toString() {
    return "Cake[sugar=" + this.sugar + ", flour=" + this.flour + ", butter=" + this.butter
            + ", eggs=" + this.eggs + ", milk=" + this.milk + ", bakingPowder=" + this.bakingPowder
            + ", chocolate=" + this.chocolate + "]";
  }
}
