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
    // TODO: 4/6/2016 Pass accountName to constructor when name feature finished.
    String accId = Integer.toString(getCustomerId()) + Integer.toString(accounts.size());
    CheckingAccount acc = new CheckingAccount(accId, initialDeposit, this, accountName);
    accounts.add(acc);
    return acc;
  }

  public void notifyCustomer(String text) {
    System.out.println("New Notification - " + text);
  }
}
