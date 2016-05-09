/*
 * Copyright (c) 2016 Richik Sinha Choudhury and Nick Dimitrov
 */

package com.bankofjava;

/**
 * Created by Richik SC on 4/6/2016.
 * A simple account
 */
public class Account {

  protected String accountId;
  protected double balance = 0;
  protected Customer holder;
  protected Statement statement;

  protected String name;

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
