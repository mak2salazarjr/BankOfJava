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
  protected Customer accountHolder;

  protected String accountName;

  public Account(String accountId, double initialDeposit, Customer holder, String accountName) {
    this.accountId = accountId;
    this.balance = initialDeposit;
    this.accountHolder = holder;
    this.accountName = accountName;
  }

  public double getBalance() {
    return balance;
  }

  public Customer getAccountHolder() {
    return accountHolder;
  }
  
  @Override
  public String toString() {
	  return accountId + " " + accountName + " " + accountHolder.getCustomerId();
  }
}
