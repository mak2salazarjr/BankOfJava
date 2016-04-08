/*
 * Copyright (c) 2016 Richik Sinha Choudhury and Nick Dimitrov
 */

package com.bankofjava;
/**
 * Created by Richik SC on 4/6/2016.
 * A simple checking account
 */
public class CheckingAccount extends Account {

  private String checkNumber;

  public CheckingAccount(String accountId, double initialDeposit, Customer holder, String accountName) {
    super(accountId, initialDeposit, holder, accountName);
  }

  public void withdrawCash(double amount) {
    balance -= amount;
    // Overdraft
    if(balance < 0) {
      accountHolder.notifyCustomer("Overdraft, balance currently $" + balance);
      // TODO: Add double fee to charges list with String description.
    }
  }

  public void deposit(double amount) {
    balance += amount;
  }

  // TODO: writeCheck method, deposits money in another customer's account

}
