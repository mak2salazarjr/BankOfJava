package com.bankofjava.web;

import com.bankofjava.domain.Account;
import com.bankofjava.domain.Customer;
import com.bankofjava.domain.Statement;
import com.bankofjava.exception.AccountNotFoundException;
import com.bankofjava.exception.CustomerIsInvalidException;
import com.bankofjava.exception.CustomerNotFoundException;
import com.bankofjava.repository.AccountRepository;
import com.bankofjava.repository.CustomerRepository;
import com.bankofjava.repository.StatementItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/v1")
public class BankController {

  @Autowired
  private CustomerRepository customerRepository;

  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private StatementItemRepository itemRepository;

  @PostMapping("/customers/add")
  public @ResponseBody Customer registerCustomer(
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
      return customer;
    }
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
    return customerRepository.findById(id)
        .orElseThrow(CustomerNotFoundException::new);
  }

  @GetMapping(path="/customers/{customerId}/accounts")
  public @ResponseBody Account[] getCustomerAccounts(
      @PathVariable(value="customerId") int id
  ) throws CustomerNotFoundException {
    return customerRepository
        .findById(id)
        .orElseThrow(CustomerNotFoundException::new)
        .getAccounts();
  }

  @PostMapping(path="/customers/{customerId}/accounts")
  public @ResponseBody Account openAccountForCustomer(
      @PathVariable(name = "customerId") int customerId,
      @RequestParam(name = "deposit", defaultValue = "0.0") double initialDeposit,
      @RequestParam(name = "name") String accountName
  ) throws CustomerNotFoundException {
    Customer customer = customerRepository.findById(customerId)
        .orElseThrow(CustomerNotFoundException::new);
    Account acc = customer.openAccount(initialDeposit, accountName);
    accountRepository.save(acc);
    return acc;
  }

  @GetMapping(path="/customers/{customerId}/statement")
  public @ResponseBody Map<String, Statement> getCustomerStatement(
      @PathVariable(value="customerId") int id
  ) throws CustomerNotFoundException {
    return customerRepository
        .findById(id)
        .orElseThrow(CustomerNotFoundException::new)
        .getStatements();
  }

  @GetMapping("/withdraw")
  @Transactional
  public @ResponseBody Account withdraw(
      @RequestParam String accountId,
      @RequestParam double amount
  ) throws AccountNotFoundException {
    Account acc =  accountRepository.findById(accountId)
        .orElseThrow(AccountNotFoundException::new);

    acc.setBalance(acc.getBalance() - amount);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    Statement stmt = acc.getStatement();
    itemRepository.save(
        stmt.addItem("Withdrawal on " +
            sdf.format(Calendar.getInstance().getTime()), -amount)
    );
    // Overdraft
    if(acc.getBalance() < 0) {
      itemRepository.save(
          stmt.addItem("Overdraft fee", -0.05 * amount)
      );
      acc.getHolder().notifyCustomer("Overdraft, balance currently $" + acc.getBalance());
    }
    return acc;
  }

  @GetMapping("/deposit")
  @Transactional
  public @ResponseBody Account deposit(
      @RequestParam String accountId,
      @RequestParam double amount
  ) throws AccountNotFoundException {
    Account acc =  accountRepository.findById(accountId)
        .orElseThrow(AccountNotFoundException::new);
    acc.setBalance(acc.getBalance() + amount);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    itemRepository.save(
        acc.getStatement().addItem("Deposit on" +
            sdf.format(Calendar.getInstance().getTime()), amount)
    );
    return acc;
  }


}
