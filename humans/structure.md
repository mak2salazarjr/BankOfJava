# Bank of Java
## Project and Inheritance Structure
com.bankofjava
  |
  |- Bank
  |- Customer
  |- Account >|
              |=> com.bankofjava.CheckingAccount
              |
  |

## Relationships
  Bank 1-----0..n Customer
  Customer 1*-----0..n Account
  Account 0..n-----1 Bank

  *For now, no joint custody accounts.