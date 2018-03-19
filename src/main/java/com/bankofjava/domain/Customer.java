/*
 * Copyright (c) 2018 Richik Sinha Choudhury and Nick Dimitrov
 */

package com.bankofjava.domain;

import com.bankofjava.Bank;
import com.bankofjava.exception.AccountNotFoundException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

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

  public String getPassword() {
    return password;
  }

  public void notifyCustomer(String text) {
    System.out.println("New Notification - " + text);
  }

  @Override
  public String toString() {
    String obj =  "{" +
        "id: " + getCustomerId() + "," +
        "name: " + getName() + "," +
        "age: " + this.age + "," +
        "gender: " + this.gender + "," +
        "accounts: [";
    Set keys = accounts.keySet();
    for(Iterator it = keys.iterator(); it.hasNext();) {
      obj += it.next();
      if(it.hasNext()) {
        obj += ",";
      }

    }
    obj += "]}";
    return obj;
  }
}