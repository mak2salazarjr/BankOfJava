/*
 * Copyright (c) 2016 Richik Sinha Choudhury and Nick Dimitrov
 */

package com.bankofjava;

import com.bankofjava.exceptions.AccountNotFoundException;

import java.util.HashMap;
import java.util.Map;

/**
 * A class representing the customer.
 */
public class Customer {

  private String customerId;
  private int age;
  private String name;
  private String gender;
  private String password;
  private Bank bank;
  
  private Map<String, CheckingAccount> accounts = new HashMap<>();

  public Customer(String id, String name, int age, String gender, String password, Bank bank) {
    this.customerId = id;
    this.name = name;
    this.age = age;
    this.gender = gender;
    this.password = password;
    this.bank = bank;
  }
  
  public String getCustomerId() {
    return customerId;
  }

  public CheckingAccount openAccount(double initialDeposit, String accountName) {
    String accId = bank.generateAccountId();
    CheckingAccount acc = new CheckingAccount(accId, initialDeposit, this, accountName);
    accounts.put(accId, acc);
    return acc;
  }

  public Account getAccount(String accountId) {
    return accounts.get(accountId);
  }

  public Account getAccountByName(String accountName) throws Exception {
    for (Account acc : accounts.values()) {
      if(acc.getName().equals(accountName))
        return acc;
    }
    throw new AccountNotFoundException("Account not found for customer " + getName() + ".");
  }

  public String getName() {
    return name;
  }

  public String getConsolidatedStatements() {
    String fullStatement = String.format("#%s %s%n", getCustomerId(), getName().toUpperCase());
    fullStatement += Statement.loopChar('-', fullStatement.length());
    fullStatement += System.lineSeparator();
    for (Account a : accounts.values()) {
      fullStatement += a.getStatement();
    }
    return fullStatement;
  }

  public Account[] getAccounts() {
    return (Account[]) accounts.values().toArray();
  }

  public void notifyCustomer(String text) {
    System.out.println("New Notification - " + text);
  }
}
