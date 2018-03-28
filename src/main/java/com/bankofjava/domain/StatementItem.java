/*
 * Copyright (c) 2018 Richik Sinha Choudhury and Nick Dimitrov
 */

package com.bankofjava.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Richik SC on 5/5/2016.
 * THE MOST PLAIN OF ALL POJOS
 */

@Entity
public class StatementItem {

  @Id
  @GeneratedValue
  @Column(unique = true, nullable = false)
  private long id;

  @Column private String description;
  @Column private double charge;
  @Column private Date time;

  @ManyToOne
  @JoinColumn(name = "statement_id", referencedColumnName = "id")
  @JsonIgnore
  private Statement statement;

  public StatementItem() {}

  public StatementItem(String desc, double charge, Statement statement) {
    this.description = desc;
    this.charge = charge;
    this.time = Calendar.getInstance().getTime();
    this.statement = statement;
  }

  public StatementItem(String desc, double charge, Date time, Statement statement) {
    this.description = desc;
    this.charge = charge;
    this.time = time;
    this.statement = statement;
  }

  public long getId() {
    return id;
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

  public Statement getStatement() {
    return statement;
  }

  public void setId(long id) {
    this.id = id;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setCharge(double charge) {
    this.charge = charge;
  }

  public void setTime(Date time) {
    this.time = time;
  }

  public void setStatement(Statement statement) {
    this.statement = statement;
  }
}
