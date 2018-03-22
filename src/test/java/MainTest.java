/*
 * Copyright (c) 2016 Richik Sinha Choudhury and Nick Dimitrov
 */

import com.bankofjava.domain.CheckingAccount;
import com.bankofjava.domain.Customer;
import com.bankofjava.Bank;

/**
 * The test driver program to squeeze everything out of the classes.
 */
public class MainTest {

  public static void main(String[] args) {

    try {
    Bank myBank = new Bank();
    String customerId;
      try ( TestOut out = new TestOut("test.log") ) {

        // Creating and retrieving our customer
        customerId = myBank.registerCustomer("Jeffrey Blankinson", 32, "Male", "thisIsMyPassword");
        Customer myCustomer = myBank.getCustomer(customerId, "thisIsMyPassword");

        // Open the account
        CheckingAccount acc = myCustomer.openAccount(25.0, "CHECKING");

        // Basic account properties
        out.println(acc);
        out.println("acc.getHolder() = " + acc.getHolder());
        out.println("acc.getBalance() = " + acc.getBalance());
        acc.withdrawCash(2.0);
        out.println("Withdrawing 2 dollars");
        out.println("acc.getBalance() = " + acc.getBalance());
        acc.deposit(10.0);
        out.println("acc.getBalance() = " + acc.getBalance());
        acc.withdrawCash(45.0);
        out.println("acc.getBalance() = " + acc.getBalance());
        out.println("");

        // Statements printing
        out.println("Statement:");
        out.println("");
        out.println(myCustomer.getConsolidatedStatements());

      } catch (Exception e) {
        e.printStackTrace();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  
}
