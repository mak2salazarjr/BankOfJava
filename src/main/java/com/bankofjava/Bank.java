/*
 * Copyright (c) 2016 Richik Sinha Choudhury and Nick Dimitrov
 */

package com.bankofjava;

import com.bankofjava.exceptions.CustomerIsInvalidException;
import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by Richik SC on 4/6/2016.
 * The main bank class.
 */
public class Bank {

  private Map<String, Customer> customers = new HashMap<>(1);
  private List<String> accountIds = new ArrayList<>(1);

  public Map<String, Customer> getCustomers() {
    return customers;
  }

  public String registerCustomer(String name, int age, String gender, String password) throws
      CustomerIsInvalidException {
    if(age >= 18) {
      try {
        String customerId = generateCustomerId();
        String pwdHash = shaHash(password);
        System.out.println("SHA HASH OF USER PASSWORD: " + pwdHash);
        customers.put(customerId, new Customer(customerId, name, age, gender, pwdHash, this));
        return customerId;
      } catch (NoSuchAlgorithmException e) {
        e.printStackTrace();
      }
    }
    throw new CustomerIsInvalidException(CustomerIsInvalidException.Reason.IS_UNDERAGE);
  }

  public String generateCustomerId() {
    String customerId = lenRandHexString(16).toUpperCase();
    while (customers.containsKey(customerId))
      customerId = lenRandHexString(16).toUpperCase();
    return customerId;
  }

  public String generateAccountId() {
    String accountId = lenRandHexString(8).toUpperCase();
    while (accountIds.contains(accountId))
      accountId = lenRandHexString(8).toUpperCase();
    accountIds.add(accountId);
    return accountId;
  }

  public Customer getCustomer(String customerId, String password) throws Exception {
    Customer cust = getCustomers().get(customerId);
    if (cust.getPassword().equals(shaHash(password))) {
      return cust;
    } else {
      throw new Exception("Wrong password!");
    }

  }

  public static String shaHash(String toCrypt) throws NoSuchAlgorithmException {
    MessageDigest md = MessageDigest.getInstance("SHA-256");
    byte[] hash = md.digest(toCrypt.getBytes(StandardCharsets.UTF_8));
    StringBuffer sb = new StringBuffer();
    for(int i=0;i<hash.length;i++){
      sb.append(Integer.toHexString(0xff & hash[i]));
    }
    return sb.toString();
  }

  public static String lenRandHexString(int length) {
    byte[] buffer = new byte[64];
    new Random().nextBytes(buffer);
    StringBuilder sb = new StringBuilder();
    for (byte b : buffer) {
      sb.append(String.format("%02x", b));
    }
    return sb.toString().substring(0, length);
  }

}
