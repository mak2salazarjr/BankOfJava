/*
 * Copyright (c) 2016 Richik Sinha Choudhury and Nick Dimitrov
 */

package com.bankofjava;

/**
 * Created by Richik SC on 5/5/2016.
 */
public class StatementItem {
  private String description;
  private double charge;

  public StatementItem(String desc, double charge) {
    this.description = desc;
    this.charge = charge;
  }

  public String getDescription() {
    return description;
  }

  public double getCharge() {
    return charge;
  }
}
