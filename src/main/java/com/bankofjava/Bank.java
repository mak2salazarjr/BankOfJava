/*
 * Copyright (c) 2016 Richik Sinha Choudhury and Nick Dimitrov
 */

package com.bankofjava;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Richik SC on 4/6/2016.
 * The main bank class.
 */
public class Bank {

  // TODO: 4/6/2016 Make this a class proper (attributes, etc)

  private List<Customer> customers = new ArrayList<>(1);

  public static void main(String[] args) {

    Bank myBank = new Bank();
    if(myBank.registerCustomer("Jeffrey Blankinson", 19, "Male")) {
      Customer myCustomer = myBank.getCustomer(0);
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
    }

  }

  public List<Customer> getCustomers() {
    return customers;
  }

  public boolean registerCustomer(String name, int age, String gender) {
    if(age >= 18) {
      customers.add(new Customer(customers.size(), name, age, gender));
      return true;
    }
    return false;
  }

  public Customer getCustomer(int customerId) {
    return getCustomers().get(customerId);
  }

}
