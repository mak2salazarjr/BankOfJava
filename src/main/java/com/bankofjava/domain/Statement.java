/*
 * Copyright (c) 2018 Richik Sinha Choudhury and Nick Dimitrov
 */

package com.bankofjava.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A statement class.
 */

@Entity
public class Statement {

  @Id
  private String id;

  @OneToOne(cascade = CascadeType.ALL)
  @MapsId
  @JoinColumn(name = "id", nullable = false, unique = true)
  @JsonBackReference
  private Account account;

  @OneToMany(mappedBy = "statement")
  private List<StatementItem> items = new ArrayList<>();

  public Statement() {}

  public Statement(Account a) {
    this.account = a;
  }

  public StatementItem addItem(String desc, double charge) {
    StatementItem item = new StatementItem(desc, charge, this);
    items.add(item);
    return item;
  }

  public StatementItem addItem(String desc, double charge, Date time) {
    StatementItem item = new StatementItem(desc, charge, time, this);
    items.add(item);
    return item;
  }

  public StatementItem getItem(int index) {
    return items.get(index);
  }

  public String getDescription(int index) {
    return items.get(index).getDescription();
  }

  public double getCharge(int index) {
    return items.get(index).getCharge();
  }

  public Date getTime(int index) {
    return items.get(index).getTime();
  }

  public String getId() {
    return id;
  }

  public List<StatementItem> getItems() {
    return items;
  }

  public Account getAccount() {
    return account;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setItems(List<StatementItem> items) {
    this.items = items;
  }

  public void setAccount(Account account) {
    this.account = account;
  }
}
