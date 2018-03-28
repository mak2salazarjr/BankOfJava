/*
 * Copyright (c) 2018 Richik Sinha Choudhury and Nick Dimitrov
 */

package com.bankofjava.domain;

/**
 * Created by Richik SC on 4/6/2016.
 * A simple checking account
 */

@Deprecated
public class CheckingAccount extends Account {

  private String checkNumber;

  public CheckingAccount(String accountId, double initialDeposit, Customer holder, String accountName) {
    super(accountId, initialDeposit, holder, accountName);
  }

  public void withdrawCash(double amount) {
    balance -= amount;
    statement.addItem("Withdrawal", amount * -1D);
    // Overdraft
    if(balance < 0) {
      statement.addItem("Overdraft fee", -0.05 * amount);
      holder.notifyCustomer("Overdraft, balance currently $" + balance);
    }
  }

  public void deposit(double amount) {
    balance += amount;
    statement.addItem("Deposit", amount);
  }

}
