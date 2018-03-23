/*
 * Copyright (c) 2018 Richik Sinha Choudhury and Nick Dimitrov
 */

package com.bankofjava.domain;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Richik SC on 5/5/2016.
 */
public class StatementItem {
  private String description;
  private double charge;
  private Date time;

  public StatementItem(String desc, double charge) {
    this.description = desc;
    this.charge = charge;
    this.time = Calendar.getInstance().getTime();
  }

  public StatementItem(String desc, double charge, Date time) {
    this.description = desc;
    this.charge = charge;
    this.time = time;
  }

  public String getDescription() {
    return description;
  }

  public double getCharge() {
    return charge;
  }

  public Date getTime() {
    return time;
  }
}
