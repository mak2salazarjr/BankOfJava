/*
 * Copyright (c) 2016 Richik Sinha Choudhury and Nick Dimitrov
 */

package com.bankofjava;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Richik SC on 4/6/2016.
 * The main bank class.
 */
public class Bank {

  // TODO: 4/6/2016 Make this a class proper (attributes, etc)

  private Map<String, Customer> customers = new HashMap<>(1);

  public static void main(String[] args) {

    Bank myBank = new Bank();
    String customerId;

    try {
      customerId = myBank.registerCustomer("Jeffrey Blankinson", 19, "Male");
      Customer myCustomer = myBank.getCustomer(customerId);
      System.out.println(myCustomer.getCustomerId());

      CheckingAccount acc = myCustomer.openAccount(25.0, "CHECKING");
      System.out.println(acc);
      System.out.println("acc.getAccountHolder() = " + acc.getAccountHolder());
      System.out.println("acc.getBalance() = " + acc.getBalance());
      acc.withdrawCash(2.0);
      System.out.println("acc.getBalance() = " + acc.getBalance());
      acc.deposit(10.0);
      System.out.println("acc.getBalance() = " + acc.getBalance());
      acc.withdrawCash(45.0);
      System.out.println("acc.getBalance() = " + acc.getBalance());
      System.out.println("");
      System.out.println("Statement:");
      System.out.println("");
      System.out.println(myCustomer.getConsolidatedStatements());

    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  public Map<String, Customer> getCustomers() {
    return customers;
  }

  public String registerCustomer(String name, int age, String gender) throws Exception {
    if(age >= 18) {
      String customerId = String.valueOf((int) Math.floor(Math.random() * 100000));
      while(customers.containsKey(customerId))
        customerId = String.valueOf(Math.floor(Math.random() * 100000));
      customers.put(customerId, new Customer(customerId, name, age, gender));
      return customerId;
    }
    throw new Exception("Customer is underage.");
  }

  public Customer getCustomer(String customerId) {
    return getCustomers().get(customerId);
  }

}
