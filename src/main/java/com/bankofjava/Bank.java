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

  public static void main(String[] args) {

    List<Customer> customers = new ArrayList<>(1);

    customers.add(new Customer(customers.size()));
    Customer myCustomer = customers.get(0);
    System.out.println(myCustomer.getCustomerId());
    
    CheckingAccount acc = myCustomer.openAccount(25.0, "CHECKING");
    System.out.println("acc.getAccountHolder() = " + acc.getAccountHolder());
    System.out.println("acc.getBalance() = " + acc.getBalance());
    acc.withdrawCash(2.0);
    System.out.println("acc.getBalance() = " + acc.getBalance());
    acc.deposit(10.0);
    System.out.println("acc.getBalance() = " + acc.getBalance());
    acc.withdrawCash(45.0);
    System.out.println("acc.getBalance() = " + acc.getBalance());

  }

}
