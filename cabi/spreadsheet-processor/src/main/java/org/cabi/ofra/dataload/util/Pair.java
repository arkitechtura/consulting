package org.cabi.ofra.dataload.util;

/**
 * Created by equiros on 06/11/14.
 */
public class Pair <A, B> {
  private A car;
  private B cdr;

  public Pair (A car, B cdr) {
    this.car = car;
    this.cdr = cdr;
  }

  public A car() {return car;}

  public B cdr() { return cdr;}
}
