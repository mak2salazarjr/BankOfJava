/*
 * Copyright (c) 2018 Richik Sinha Choudhury and Nick Dimitrov
 */

package com.bankofjava.domain;

import com.bankofjava.Bank;
import com.bankofjava.exception.AccountNotFoundException;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * A class representing the customer.
 */

@Entity
public class Customer {

  @Id
  @Basic(optional = false)
  @GeneratedValue(strategy = GenerationType.TABLE)
  @Column(name = "id")
  private String customerId;

  @Column private int age;
  @Column private String name;
  @Column private String gender;
  @Column private String password;
  @Transient private Bank bank;

  @OneToMany(mappedBy = "holder")
  @MapKey(name = "accountId")
  private Map<String, Account> accounts = new HashMap<>();

  public Customer(String id, String name, int age, String gender, String password) {
    this.customerId = id;
    this.name = name;
    this.age = age;
    this.gender = gender;
    this.password = password;
    this.bank = Bank.getInstance();
  }
  
  public String getCustomerId() {
    return customerId;
  }

  /*public CheckingAccount openAccount(double initialDeposit, String accountName) {
    String accId = bank.generateAccountId();
    CheckingAccount acc = new CheckingAccount(accId, initialDeposit, this, accountName);
    accounts.put(accId, acc);
    return acc;
  }*/

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
    String obj =  "{\n" +
        "id: " + getCustomerId() + ",\n" +
        "name: " + getName() + ",\n" +
        "age: " + this.age + ",\n" +
        "gender: " + this.gender + "n" +
        "accounts: [\n";
    Set keys = accounts.keySet();
    for(Iterator it = keys.iterator(); it.hasNext();) {
      obj += it.next();
      if(it.hasNext()) {
        obj += ",\n";
      }

    }
    obj += "]}";
    return obj;
  }
}
