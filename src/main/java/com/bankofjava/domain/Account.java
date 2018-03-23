/*
 * Copyright (c) 2018 Richik Sinha Choudhury and Nick Dimitrov
 */

package com.bankofjava.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Richik SC and Nick Dimitrov on 4/6/2016.
 * A simple account
 */

@Entity
public class Account {

  @Id
  @Basic(optional = false)
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "uaid")
  @GenericGenerator(name = "uaid", strategy = "uuid2")
  @Column(name = "id")
  protected String accountId;

  @Column protected double balance = 0;
  @Column protected String name;

  @ManyToOne
  @JoinColumn(name = "customer_id")
  @JsonBackReference
  protected Customer holder;

  @Transient protected Statement statement;

  public Account(String accountId, double initialDeposit, Customer holder, String name) {
    this.accountId = accountId;
    this.balance = initialDeposit;
    this.holder = holder;
    this.name = name;
    this.statement = new Statement(this);
  }

  public Account() {
    this.statement = new Statement(this);
  }

  public String getAccountId() {
    return accountId;
  }

  public String getName() {
    return name;
  }

  public double getBalance() {
    return balance;
  }

  public Customer getHolder() {
    return holder;
  }

  public Statement getStatement() {
    return statement;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setBalance(double balance) {
    this.balance = balance;
  }

  public void setHolder(Customer holder) {
    this.holder = holder;
  }

  public void withdraw(double amount) {
    balance -= amount;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    statement.addItem("Withdrawal " +
        sdf.format(Calendar.getInstance().getTime()), -amount);
    // Overdraft
    if(balance < 0) {
      statement.addItem("Overdraft fee", -0.05 * amount);
      holder.notifyCustomer("Overdraft, balance currently $" + balance);
    }
  }

  public void deposit(double amount) {
    balance += amount;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    statement.addItem("Deposit " +
        sdf.format(Calendar.getInstance().getTime()), amount);
  }

  @Override
  public String toString() {
	  return accountId + " " + name + " " + holder.getCustomerId();
  }
}
