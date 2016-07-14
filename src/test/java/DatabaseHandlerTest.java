/*
 * Copyright (c) 2016 Richik Sinha Choudhury and Nick Dimitrov
 */

import com.bankofjava.DatabaseHandler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Richik SC on 7/13/2016.
 */
public class DatabaseHandlerTest {

  public static void main(String[] args) throws SQLException {

    // Create a new DatabaseHandler to handle the MySQL DB on localhost port 3306
    DatabaseHandler dbHandler = new DatabaseHandler(3306);
    Connection conn = null;
    try {
      conn = dbHandler.getConnection();
      conn.setSchema("bankofjava.2016");
      ResultSet rs = null;
      try (Statement stmt = conn.createStatement()) {
        System.out.println("Statement created!");
        // Get all names that start with 'S'
        rs = stmt.executeQuery("select * from `bankofjava.2016`.`customers` where name " +
            "like 'S%'");
        // % means wildcard - starts with s - any number of any characters after that

        while (rs.next()) {
          // Loop through result set and print each of them
          System.out.printf(
              " %s | %s | %d | %s %n",
              rs.getString("id"),
              rs.getString("name"),
              rs.getInt("age"),
              rs.getString("gender")
          );
        }
      } catch (SQLException e) {
        DatabaseHandler.printSQLException(e);
      } finally {
        if (rs != null) rs.close();
      }
    } catch (SQLException e) {
      DatabaseHandler.printSQLException(e);
    } finally {
      if (conn != null)
        conn.close();
    }
  }

}
