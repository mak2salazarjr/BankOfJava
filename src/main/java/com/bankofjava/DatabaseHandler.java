/*
 * Copyright (c) 2016 Richik Sinha Choudhury and Nick Dimitrov
 */

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

    System.out.println("Connected to " + conn.getMetaData().getDatabaseProductName() + " database" +
        " on " + dbUrl);
    return conn;

  }

  // Copied from JDBC tutorial
  public static void printSQLException(SQLException ex) {

    for (Throwable e : ex) {
      if (e instanceof SQLException) {
        if (ignoreSQLException(
            ((SQLException)e).
                getSQLState()) == false) {

          e.printStackTrace(System.err);
          System.err.println("SQLState: " +
              ((SQLException)e).getSQLState());

          System.err.println("Error Code: " +
              ((SQLException)e).getErrorCode());

          System.err.println("Message: " + e.getMessage());

          Throwable t = ex.getCause();
          while(t != null) {
            System.out.println("Cause: " + t);
            t = t.getCause();
          }
        }
      }
    }
  }

  // Copied from JDBCTutorial utilites
  public static boolean ignoreSQLException(String sqlState) {
    if (sqlState == null) {
      System.out.println("The SQL state is not defined!");
      return false;
    }
    // X0Y32: Jar file already exists in schema
    if (sqlState.equalsIgnoreCase("X0Y32"))
      return true;
    // 42Y55: Table already exists in schema
    if (sqlState.equalsIgnoreCase("42Y55"))
      return true;
    return false;
  }

}
