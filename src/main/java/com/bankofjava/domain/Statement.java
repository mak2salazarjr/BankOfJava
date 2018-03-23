/*
 * Copyright (c) 2018 Richik Sinha Choudhury and Nick Dimitrov
 */

package com.bankofjava.domain;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A statement class.
 */
public class Statement extends ArrayList<StatementItem> {

  private Account account;
  public Statement(Account a) {
    super();
    this.account = a;
  }

  public boolean addItem(String desc, double charge) {
    return this.add(new StatementItem(desc, charge));
  }

  public boolean addItem(String desc, double charge, Date time) {
    return this.add(new StatementItem(desc, charge, time));
  }

  public String getDescription(int index) {
    return this.get(index).getDescription();
  }

  public double getCharge(int index) {
    return this.get(index).getCharge();
  }

  public Date getTime(int index) {
    return this.get(index).getTime();
  }

  public static String padRight(String s, int n) {
    return String.format("%-" + n + "s", s);
  }

  public static String padLeft(String s, int n) {
    return String.format("%" + n + "s", s);
  }

  public static String loopChar(char c, int n) {
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < n; i++) {
      builder.append(c);
    }
    return builder.toString();
  }

  @Override
  public String toString() {
    int maxLengthD = 0;
    int maxLengthC = 0;
    for (StatementItem i : this) {
      if (i.getDescription().length() > maxLengthD) {
        maxLengthD = i.getDescription().length();
      }
      if (String.format("%.2f", i.getCharge()).length() > maxLengthC) {
        maxLengthC = Double.toString(i.getCharge()).length();
      }
    }
    StringBuilder statement = new StringBuilder();
    statement.append(account.getAccountId());
    statement.append(" " + account.getName());
    statement.append(System.lineSeparator());
    statement.append(Statement.loopChar('-', maxLengthC + maxLengthD + 26));
    statement.append(System.lineSeparator());
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    for (int i = 0; i < this.size(); i++) {
      statement.append(" " + i + " |");
      statement.append(" " + Statement.padRight(this.getDescription(i), maxLengthD) + " |");
      statement.append(" " + sdf.format(this.getTime(i)) + " |");
      statement.append(" " + Statement.padLeft(Double.toString(this.getCharge(i)), maxLengthC) +
          " ");
      statement.append(System.lineSeparator());
    }
    statement.append("   |");
    statement.append(" " + Statement.padRight("TOTAL BALANCE", maxLengthD) + " |");
    statement.append(" " + account.getBalance() + " ");
    return statement.toString();
  }
}
