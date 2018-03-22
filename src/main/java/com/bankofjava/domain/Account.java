/*
 * Copyright (c) 2018 Richik Sinha Choudhury and Nick Dimitrov
 */

package com.bankofjava.domain;

import javax.persistence.*;

/**
 * Created by Richik SC and Nick Dimitrov on 4/6/2016.
 * A simple account
 */

@Entity
public class Account {

  @Id
  @Basic(optional = false)
  @GeneratedValue(strategy = GenerationType.TABLE)
  @Column(name = "id")
  protected String accountId;

  @Column protected double balance = 0;
  @Column protected String name;

  @ManyToOne
  @JoinColumn(name = "customer_id")
  protected Customer holder;

  @Transient protected Statement statement;

  public Account(String accountId, double initialDeposit, Customer holder, String name) {
    this.accountId = accountId;
    this.balance = initialDeposit;
    this.holder = holder;
    this.name = name;
    this.statement = new Statement(this);
  }

  public String getAccountId() {
    return accountId;
  }

  public String getName() {
    return name;
  }

  public double getBalance() {
    return balance;
  }

  public Customer getHolder() {
    return holder;
  }

  public Statement getStatement() {
    return statement;
  }

  @Override
  public String toString() {
	  return accountId + " " + name + " " + holder.getCustomerId();
  }
}
