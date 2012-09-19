package com.mleczey.basic;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import com.mleczey.basic.EqualsExample.Rabbit;
import org.junit.Before;
import org.junit.Test;

public class RabbitTest {
  private Rabbit rabbitA;
  private Rabbit rabbitB;
  private Rabbit rabbitC;
  private Rabbit rabbitD;
  
  @Before
  public void setUp() {
    this.rabbitA = new Rabbit("Carrot");
    this.rabbitB = new Rabbit("Carrot");
    this.rabbitC = new Rabbit("Carrot");
    this.rabbitD = new Rabbit("Letuce");
  }
  
  @Test
  public void reflexivity() {
    assertThat(this.rabbitA, is(this.rabbitA));
  }
  
  @Test
  public void symmetry() {
    assertThat(this.rabbitA, is(this.rabbitB));
    assertThat(this.rabbitB, is(this.rabbitA));
  }
  
  @Test
  public void transitivity() {
    assertThat(this.rabbitA, is(this.rabbitB));
    assertThat(this.rabbitB, is(this.rabbitC));
    assertThat(this.rabbitA, is(this.rabbitC));
  }
  
  @Test
  public void nullCheck() {
    assertTrue(!this.rabbitA.equals(null));
  }
  
  @Test
  public void consistently() {
    for (int i = 0; i < 500; i++) {
      assertThat(this.rabbitA, is(this.rabbitB));
    }
  }
  
  @Test
  public void notEquals() {
    assertTrue(!this.rabbitA.equals(this.rabbitD));
  }
  
  @Test
  public void hashCodesEquals() {
    // when
    assertThat(this.rabbitA, is(this.rabbitB));
    // then
    assertThat(this.rabbitA.hashCode(), is(this.rabbitB.hashCode()));
  }
}
