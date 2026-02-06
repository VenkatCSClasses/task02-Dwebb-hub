Overview:
This repository contains a class and a set of tests designed to implement and test a fully functioning bank account class. 
This class is rather simplistic and allows the user to deposit, withdraw, and transfer money between bank accounts. 

Design Principles:
1. The class should be able to pass all the tests found and converted from the tests.yaml file
2. Any email should follow the generally agreed upon guidelines of what makes an email valid, otherwise it is considered invalid
3. An amount is considered invalid if it does not follow the format of currency (less than three decimal places) or is negative
4. Any invalid amounts or emails will throw proper errors

Output:
1. Generate the minimal files required
2. Other functions may be used to facilitate the implementation of the key functions, but there should be no other methods involving the bank account itself
3. Document functions and tests with comments: for tests, outline the equivalence classes and/or boundary cases

Key Functions:
- Constructor(String email, float balance): Creates a bank account object that contains a valid email and a balance, or throws errors for invalid email/balance
- Withdraw(float amount): Removes the given amount from the account balance if the amount is valid, otherwise throws an error
- Deposit(float amount): Adds the given amount to the account balance if it is valid
- Transfer(float amount, BankAccount destination): Takes an amount and, if it is valid, removes it from the current account and deposits it to the destination account
- getBalance(): returns the account's balance
- getEmail(): returns the account's email
- isAmountValid(int amount): determines if the amount is valid, aka if it is positive and has less than 3 decimal places
- isEmailValid(String email): determines if the email is valid based on the general guidelines 
 
