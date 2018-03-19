/*
 * Copyright (c) 2016 Richik Sinha Choudhury and Nick Dimitrov
 */

package com.bankofjava;

import com.bankofjava.exception.CustomerIsInvalidException;
import com.bankofjava.domain.Customer;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by Richik SC and Nick Dimitrov on 4/6/2016.
 * The main bank class.
 */
public class Bank {

  private Map<String, Customer> customers = new HashMap<>(1);
  private List<String> accountIds = new ArrayList<>(1);
  private Connection conn;

  public Bank() throws SQLException {
    MysqlDataSource source = new MysqlDataSource();
    source.setServerName("localhost");
    source.setPort(3306);
    source.setUser("admin");
    source.setPassword("@16&db101#");
    source.setDatabaseName("bank_of_java");
    conn = source.getConnection();
  }

  public Customer[] getCustomers() throws SQLException {
    PreparedStatement stmt = conn.prepareStatement("SELECT * FROM customers");
    ResultSet rs = stmt.executeQuery();
    List<Customer> customers = new ArrayList<>();
    while (rs.next()) {
      customers.add(new Customer(
          rs.getString("id"),
          rs.getString("fname") + " " + rs.getString("lname"),
          rs.getInt("age"),
          rs.getString("gender"),
          rs.getString("password"),
          this
      ));
    }
    return customers.toArray(new Customer[customers.size()]);
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
    PreparedStatement stmt = conn.prepareStatement("SELECT * FROM customers WHERE id = ?");
    stmt.setString(1, customerId);
    ResultSet rs = stmt.executeQuery();
    if (rs.next()) {
      Customer cust = new Customer(
          rs.getString("id"),
          rs.getString("fname") + " " + rs.getString("lname"),
          rs.getInt("age"),
          rs.getString("gender"),
          rs.getString("password"),
          this
      );
      if (cust.getPassword().equals(shaHash(password))) {
        return cust;
      } else {
        throw new Exception("Wrong password!");
      }
    } else {
      throw new NullPointerException();
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
