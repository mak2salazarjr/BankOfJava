package com.bankofjava.web;

import com.bankofjava.domain.Account;
import com.bankofjava.domain.Customer;
import com.bankofjava.domain.Statement;
import com.bankofjava.exception.CustomerIsInvalidException;
import com.bankofjava.exception.CustomerNotFoundException;
import com.bankofjava.repository.AccountRepository;
import com.bankofjava.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@Controller
public class BankController {
  @Autowired
  private CustomerRepository customerRepository;
  @Autowired
  private AccountRepository accountRepository;

  @PostMapping(path = "/customers/add", produces = "text/html")
  public @ResponseBody String registerCustomer(
      @RequestParam int age,
      @RequestParam String name,
      @RequestParam String gender,
      @RequestParam String password
  ) throws CustomerIsInvalidException {
    Customer customer = new Customer();
    if (age < 18) {
      throw new CustomerIsInvalidException
          (CustomerIsInvalidException.Reason.IS_UNDERAGE);
    } else {
      customer.setAge(age);
      customer.setName(name);
      customer.setGender(gender);
      customer.setPassword(password);
      customerRepository.save(customer);
      return "<h1>Saved!</h1>";
    }
  }

  @GetMapping(path ="/customers/all")
  public @ResponseBody Iterable<Customer> getAllCustomers_all() {
    return getAllCustomers();
  }

  @PostMapping(path="/account/create")
  public @ResponseBody Account createAccount(
      @RequestParam int customerId,
      @RequestParam(defaultValue = "0.0") double initialDeposit,
      @RequestParam String accountName) throws CustomerNotFoundException {
    Optional<Customer> query = customerRepository.findById(customerId);
    if (query.isPresent()) {
      Account acc = query.get().openAccount(initialDeposit, accountName);
      accountRepository.save(acc);
      return acc;
    }
    else throw new CustomerNotFoundException();
  }

  @GetMapping(path="/customers")
  public @ResponseBody Iterable<Customer> getAllCustomers() {
    return customerRepository.findAll();
  }

  @GetMapping(path="/customers/{customerId}")
  public @ResponseBody Customer getCustomer(
      @PathVariable(value="customerId") int id
  ) throws CustomerNotFoundException {
    Optional<Customer> query = customerRepository.findById(id);
    if (query.isPresent()) { return query.get(); }
    else throw new CustomerNotFoundException();
  }

  @GetMapping(path="/customers/{customerId}/accounts")
  public @ResponseBody Account[] getCustomerAccounts(
      @PathVariable(value="customerId") int id
  ) throws CustomerNotFoundException {
    Optional<Customer> query = customerRepository.findById(id);
    if (query.isPresent()) {
      return query.get().getAccounts();
    }
    else throw new CustomerNotFoundException();
  }

  @GetMapping(path="/customers/{customerId}/statement")
  public @ResponseBody Map<String, Statement> getCustomerStatement(
      @PathVariable(value="customerId") int id
  ) throws CustomerNotFoundException {
    Optional<Customer> query = customerRepository.findById(id);
    if (query.isPresent()) {
      System.out.println(query.get().getConsolidatedStatements());
      return query.get().getStatements();
    }
    else throw new CustomerNotFoundException();
  }


}
