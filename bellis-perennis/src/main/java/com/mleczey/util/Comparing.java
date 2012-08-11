package com.mleczey.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Comparing {
  private static final Logger logger = Logger.getLogger(Comparing.class.getName());
  
  public static void main(String[] args) {
    Comparing comparing = new Comparing();
    comparing.run();
  }
  
  private void run() {
    Basket[] baskets = new Basket[] {
      new Basket("Lunch", new HashSet<String>() {{
        add("Bread");
        add("Letuce");
      }}),
      new Basket("Party", new HashSet<String>() {{
        add("Chips");
        add("Soda");
        add("Bread sticks");
      }}),
      new Basket("Lunch", new HashSet<String>() {{
        add("Bread");
        add("Salad");
      }}),
      new Basket("Empty", Collections.<String>emptySet()),
      new Basket("Lunch", new HashSet<String>() {{
        add("Bread");
        add("Letuce");
      }})
    };
    Arrays.sort(baskets);
    
    this.printBaskets(baskets);
    
    Arrays.sort(baskets, new CompareBasketsByName());
    
    this.printBaskets(baskets);
  }
  
  private void printBaskets(Basket[] baskets) {
    StringBuilder sb = new StringBuilder("\n");
    for (Basket basket : baskets) {
      sb.append(basket.toString()).append("\n");
    }
    logger.log(Level.INFO, "{0}", sb.toString());
  }
}

class Basket implements Comparable<Basket> {
  private String name;
  private Set<String> ingredients;

  public Basket(String name, Set<String> ingredients) {
    this.name = name;
    this.ingredients = ingredients;
  }

  public String getName() {
    return this.name;
  }
  
  public Set<String> getIngredients() {
    return this.ingredients;
  }

  @Override
  public int hashCode() {
    return 3 * this.name.hashCode() + 7 * this.ingredients.hashCode();
  }
  
  @Override
  public int compareTo(Basket basket) {
    int result = this.name.compareTo(basket.getName());
    if (0 == result) {
      result = this.ingredients.size() - basket.getIngredients().size();
      if (0 == result && !this.ingredients.containsAll(basket.getIngredients())) {
        result = this.hashCode() - basket.hashCode();
      }
    }
    return result;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("Basket[name=");
    sb.append(this.name).append(", ingredients=[");
    Iterator<String> i = this.ingredients.iterator();
    while (i.hasNext()) {
      sb.append(i.next());
      if (i.hasNext()) {
        sb.append(", ");
      }
    }
    sb.append("]]");
    return sb.toString();
  }
}

class CompareBasketsByName implements Comparator<Basket> {
  @Override
  public int compare(Basket basket1, Basket basket2) {
    return basket1.getName().compareTo(basket2.getName());
  }
}
