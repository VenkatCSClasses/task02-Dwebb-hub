# BankAccount Library

Java 17 implementation of a simple bank account with email and amount validation.

## Features

- Create accounts with validated email and non-negative balance
- `withdraw`, `deposit`, and `transfer` with:
  - non-negative amounts
  - at most 2 decimal places (currency format)
  - sufficient funds for withdrawals/transfers
- Email validation with a simple, conservative pattern
- Static helpers: `isEmailValid(String)`, `isAmountValid(float)`

## Running Tests

Ensure JUnit 5 is available on the classpath, then from the project root:

```bash
javac -cp .;junit-jupiter-api.jar;junit-jupiter-engine.jar BankAccount.java BankAccountTest.java
java  -cp .;junit-jupiter-api.jar;junit-jupiter-engine.jar org.junit.platform.console.ConsoleLauncher --scan-classpath
```

In VS Code, use the Java Test Runner extension to discover and run `BankAccountTest`.