/*
 * Copyright (c) 2016 Richik Sinha Choudhury and Nick Dimitrov
 */

package com.bankofjava.exception;

/**
 * Created by Richik SC on 5/9/2016.
 */
public class AccountNotFoundException extends Exception {
  public AccountNotFoundException() {
    super("We were unable to find the account in our system.");
  }
  public AccountNotFoundException(String msg) {
    super("We were unable to find the account in our system. " + msg);
  }
}
