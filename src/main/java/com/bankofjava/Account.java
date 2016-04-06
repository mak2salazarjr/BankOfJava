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

  // TODO: Add account names

  public Account(String accountId, double initialDeposit, Customer holder) {
    this.accountId = accountId;
    this.balance = initialDeposit;
    this.accountHolder = holder;
  }

  public double getBalance() {
    return balance;
  }

  public Customer getAccountHolder() {
    return accountHolder;
  }
}
