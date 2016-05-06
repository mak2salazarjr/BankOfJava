/*
 * Copyright (c) 2016 Richik Sinha Choudhury and Nick Dimitrov
 */

package com.bankofjava;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Richik SC on 4/6/2016.
 */
public class Customer {

  private int customerId;
  private int age;
  private String name;
  private String gender;
  
  private List<CheckingAccount> accounts = new ArrayList<>();
  // TODO: add charges list and monthly statements

  public Customer(int id, String name, int age, String gender) {
    this.customerId = id;
    this.name = name;
    this.age = age;
    this.gender = gender;
  }
  
  public int getCustomerId() {
    return customerId;
  }

  public CheckingAccount openAccount(double initialDeposit, String accountName) {
    String accId = Integer.toString(getCustomerId()) + Integer.toString(accounts.size());
    CheckingAccount acc = new CheckingAccount(accId, initialDeposit, this, accountName);
    accounts.add(acc);
    return acc;
  }

  public Account getAccount(int i) {
    return accounts.get(i);
  }

  public String getName() {
    return name;
  }

  public String getConsolidatedStatements() {
    String fullStatement = String.format("#%d %s%n", getCustomerId(), getName().toUpperCase());
    fullStatement += Statement.loopChar('-', fullStatement.length());
    fullStatement += System.lineSeparator();
    for (Account a : accounts) {
      fullStatement += a.getStatement();
    }
    return fullStatement;
  }

  public void notifyCustomer(String text) {
    System.out.println("New Notification - " + text);
  }
}
