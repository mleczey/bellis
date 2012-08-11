package com.mleczey.pattern;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Structural design pattern
 * 
 * When to use this pattern?
 * - when sub classing is impractical, there is a need for large number of different possibilities to make independent object (number of combinations for an object)
 * - it is desirable to add functionality to individual object at run-time (we do not add functionality to all objects)
 * 
 * Advantages:
 * - more flexible than inheritance, inheritance add responsibilities at compile time, this pattern add them at run-time (dynamically)
 * - at the same time other instance of the same class will not be affected by this enhancement, individual objects get new behavior
 * - enhances or modifies object functionality
 * 
 * Disadvantages:
 * - code maintenance can be a problem as it provides a lot of similar kind of small objects (each decorator that is)
 */
public class DecoratorPattern {
  private static final Logger logger = Logger.getLogger(DecoratorPattern.class.getName());
  
  public static void main(String[] args) {
    DecoratorPattern pattern = new DecoratorPattern();
    pattern.run();
  }
  
  private void run() {
    Coffee coffee = new BlackCoffee();
    logger.log(Level.INFO, "{0}", coffee.toString());
    coffee = new CoffeeWithMilk(coffee);
    logger.log(Level.INFO, "{0}", coffee.toString());
    coffee = new CoffeeWithSprinkles(coffee);
    logger.log(Level.INFO, "{0}", coffee.toString());
  }
}

abstract class Coffee {
  abstract public String getName();
  abstract public double getCost();
  abstract public List<String> getIngredients();
  
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder(this.getName());
    sb.append("[cost=").append(this.getCost()).append(", ingredients=[");
    List<String> ingredients = this.getIngredients();
    Iterator<String> i = ingredients.iterator();
    while (i.hasNext()) {
      String ingredient = i.next();
      sb.append(ingredient);
      if (i.hasNext()) {
        sb.append(", ");
      }
    }
    sb.append("]]");
    return sb.toString();
  }
}

class BlackCoffee extends Coffee {
  @Override
  public String getName() {
    return "BlackCoffee";
  }

  
  @Override
  public double getCost() {
    return 1.0;
  }

  @Override
  public List<String> getIngredients() {
    List<String> ingredients = new ArrayList<String>(8);
    ingredients.add("Coffee");
    return ingredients;
  }
}

abstract class CoffeeDecorator extends Coffee {
  private final Coffee coffee;
  
  public CoffeeDecorator(Coffee coffee) {
    this.coffee = coffee;
  }

  @Override
  public String getName() {
    return this.coffee.getName();
  }
  
  @Override
  public double getCost() {
    return this.coffee.getCost();
  }

  @Override
  public List<String> getIngredients() {
    return this.coffee.getIngredients();
  }
}
class CoffeeWithMilk extends CoffeeDecorator {
  public CoffeeWithMilk(Coffee coffee) {
    super(coffee);
  }
  
  @Override
  public double getCost() {
    return super.getCost() + 0.5;
  }

  @Override
  public List<String> getIngredients() {
    List<String> ingredients = super.getIngredients();
    ingredients.add("Milk");
    return ingredients;
  }
}

class CoffeeWithSprinkles extends CoffeeDecorator {
  public CoffeeWithSprinkles(Coffee coffee) {
    super(coffee);
  }

  @Override
  public double getCost() {
    return super.getCost() + 0.7;
  }

  @Override
  public List<String> getIngredients() {
    List<String> ingredients = super.getIngredients();
    ingredients.add("Sprinkles");
    return ingredients;
  }
}