/*
 * Copyright (c) 2018 Richik Sinha Choudhury and Nick Dimitrov
 */

package com.bankofjava.domain;

import com.bankofjava.Bank;
import com.bankofjava.exception.AccountNotFoundException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.GenericGenerator;

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
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "id")
  private int customerId;

  @Column private int age;
  @Column private String name;
  @Column private String gender;
  @Column private String password;
  @Transient private Bank bank;

  @OneToMany(mappedBy = "holder")
  @MapKey(name = "accountId")
  @JsonManagedReference
  private Map<String, Account> accounts = new HashMap<>();

  public Customer(int id, String name, int age, String gender, String password) {
    this.customerId = id;
    this.name = name;
    this.age = age;
    this.gender = gender;
    this.password = password;
    this.bank = Bank.getInstance();
  }

  public Customer() {
    this.bank = Bank.getInstance();
  }
  
  public int getCustomerId() {
    return customerId;
  }

  public Account openAccount(double initialDeposit, String accountName) {
    Account acc = new Account();
    acc.setName(accountName);
    acc.setBalance(initialDeposit);
    acc.setHolder(this);
    System.out.println(acc.getAccountId());
    accounts.put(acc.getAccountId(), acc);
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


  @JsonIgnore
  public String getConsolidatedStatements() {
    StringBuilder fullStatement = new StringBuilder();
    fullStatement.append(String.format("#%s %s%n", getCustomerId(), getName().toUpperCase()));
    fullStatement.append(Statement.loopChar('-', fullStatement.length()));
    fullStatement.append(System.lineSeparator());
    for (Account a : accounts.values()) {
      fullStatement.append(a.getStatement().toString());
    }
    return fullStatement.toString();
  }

  @JsonIgnore
  public Map<String, Statement> getStatements() {
    Map<String, Statement> statements = new HashMap<>();
    for (Account acc : accounts.values()) {
      statements.put(acc.getAccountId(), acc.getStatement());
    }
    return statements;
  }

  public Account[] getAccounts() {
    return accounts.values().toArray(new Account[accounts.size()]);
  }

  public String getPassword() {
    return password;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public void setPassword(String password) {
    this.password = password;
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
