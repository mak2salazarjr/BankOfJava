package com.bankofjava;/*
 * Copyright (c) 2016 Richik Sinha Choudhury and Nick Dimitrov
 */

import java.sql.*;
import java.util.Properties;

/**
 * Handling the database since 7/8/2016
 */
public class DatabaseHandler {
  public String hostname;
  public int port;
  private String dbms = "mysql";

  public DatabaseHandler(int port) {
    this.port = port;
    this.hostname = "localhost";
  }
  
  public DatabaseHandler(String hostname, int port) {
    this.port = port;
    this.hostname = hostname;
  }

  public Connection getConnection() throws SQLException {

    Connection conn;
    Properties connectionProps = new Properties();
    connectionProps.put("user", "root");
    connectionProps.put("password", "@16&db101#");

    String dbUrl =
        this.hostname + ":" +
        this.port + "/";

    conn = DriverManager.getConnection(
        "jdbc:" + this.dbms + "://" + dbUrl,
        connectionProps
    );

    System.out.println("Connected to " + this.dbms + "database on" + dbUrl);
    return conn;

  }

}
