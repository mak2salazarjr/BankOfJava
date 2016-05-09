/*
 * Copyright (c) 2016 Richik Sinha Choudhury and Nick Dimitrov
 */

package com.bankofjava;

import com.bankofjava.exceptions.CustomerIsInvalidException;

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
  private List<String> accountIds = new ArrayList<>(1);

  public static void main(String[] args) {

    Bank myBank = new Bank();
    String customerId;

    try {

      // Creating and retrieving our customer
      customerId = myBank.registerCustomer("Jeffrey Blankinson", 32, "Male", "thisIsMyPassword");
      Customer myCustomer = myBank.getCustomer(customerId);
      System.out.println(myCustomer.getCustomerId());

      // Open the account
      CheckingAccount acc = myCustomer.openAccount(25.0, "CHECKING");

      // Basic account properties
      System.out.println(acc);
      System.out.println("acc.getHolder() = " + acc.getHolder());
      System.out.println("acc.getBalance() = " + acc.getBalance());
      acc.withdrawCash(2.0);
      System.out.println("acc.getBalance() = " + acc.getBalance());
      acc.deposit(10.0);
      System.out.println("acc.getBalance() = " + acc.getBalance());
      acc.withdrawCash(45.0);
      System.out.println("acc.getBalance() = " + acc.getBalance());
      System.out.println("");

      // Statements printing
      System.out.println("Statement:");
      System.out.println("");
      System.out.println(myCustomer.getConsolidatedStatements());

      System.out.println(myCustomer.getAccountByName("SAVINGS")); // Should throw exception

    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  public Map<String, Customer> getCustomers() {
    return customers;
  }

  public String registerCustomer(String name, int age, String gender, String password) throws
      CustomerIsInvalidException {
    if(age >= 18) {
      String customerId = generateCustomerId();
      customers.put(customerId, new Customer(customerId, name, age, gender, password, this));
      return customerId;
    }
    throw new CustomerIsInvalidException(CustomerIsInvalidException.Reason.IS_UNDERAGE);
  }

  public String generateCustomerId() {
    String customerId = Integer.toHexString((int) (Math.random() * 100000)).toUpperCase();
    while (customers.containsKey(customerId))
      customerId = Integer.toHexString((int) (Math.random() * 100000)).toUpperCase();
    return customerId;
  }

  public String generateAccountId() {
    String accountId = Integer.toString((int) (Math.random() * 10000000));
    while (accountIds.contains(accountId))
      accountId = Integer.toString((int) (Math.random() * 10000000));
    accountIds.add(accountId);
    return accountId;
  }

  public Customer getCustomer(String customerId) {
    return getCustomers().get(customerId);
  }

}
