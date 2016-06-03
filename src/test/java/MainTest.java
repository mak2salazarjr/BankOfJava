/*
 * Copyright (c) 2016 Richik Sinha Choudhury and Nick Dimitrov
 */

import com.bankofjava.CheckingAccount;
import com.bankofjava.Customer;
import com.bankofjava.Bank;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * The test driver program to squeeze everything out of the classes.
 */
public class MainTest {

  public static void main(String[] args) {

    Bank myBank = new Bank();
    String customerId;

    try {

      // Creating and retrieving our customer
      customerId = myBank.registerCustomer("Jeffrey Blankinson", 32, "Male", "thisIsMyPassword");
      Customer myCustomer = myBank.getCustomer(customerId, "thisIsMyPassword");

      // Open the account
      CheckingAccount acc = myCustomer.openAccount(25.0, "CHECKING");

      // Basic account properties
      MainTest.println(acc);
      MainTest.println("acc.getHolder() = " + acc.getHolder());
      MainTest.println("acc.getBalance() = " + acc.getBalance());
      acc.withdrawCash(2.0);
      MainTest.println("Withdrawing 2 dollars");
      MainTest.println("acc.getBalance() = " + acc.getBalance());
      acc.deposit(10.0);
      MainTest.println("acc.getBalance() = " + acc.getBalance());
      acc.withdrawCash(45.0);
      MainTest.println("acc.getBalance() = " + acc.getBalance());
      MainTest.println("");

      // Statements printing
      MainTest.println("Statement:");
      MainTest.println("");
      MainTest.println(myCustomer.getConsolidatedStatements());

      //  MainTest.println(myCustomer.getAccountByName("SAVINGS")); // Should throw exception

    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  public static void println(Object in) {
    String toPrint = in.toString();
    System.out.println();
    File log = new File("test_log.txt");
    if(!log.isFile())
      try {
        log.createNewFile();
      } catch (IOException e) {
        e.printStackTrace();
      }
    try {
      PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(log, true)));
      printWriter.println(toPrint);
      printWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
}
