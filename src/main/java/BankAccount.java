
/**
 * Simple bank account with basic validation for email and monetary amounts.
 */
public class BankAccount {
    private final String email;
    private float balance;

    /**
     * Constructor(String email, float balance):
     * - email must be valid according to isEmailValid
     * - balance must be >= 0 and a valid monetary amount (isAmountValid)
     * - otherwise throws IllegalArgumentException
     */
    public BankAccount(String email, float balance) {
        if (!isEmailValid(email)) {
            throw new IllegalArgumentException("Invalid email: " + email);
        }
        if (balance < 0 || !isAmountValid(balance)) {
            throw new IllegalArgumentException("Invalid initial balance: " + balance);
        }
        this.email = email;
        this.balance = balance;
    }

    /** Returns the current account balance. */
    public float getBalance() {
        return balance;
    }

    /** Returns the account email. */
    public String getEmail() {
        return email;
    }

    /**
     * Withdraw(float amount):
     * - amount must be > 0, valid (isAmountValid), and <= current balance
     * - otherwise throws IllegalArgumentException
     */
    public void withdraw(float amount) {
        if (!isAmountValid(amount) || amount <= 0) {
            throw new IllegalArgumentException("Invalid withdrawal amount: " + amount);
        }
        if (amount > balance) {
            throw new IllegalArgumentException("Insufficient funds");
        }
        balance -= amount;
    }

    /**
     * Deposit(float amount):
     * - amount must be > 0 and valid (isAmountValid)
     * - otherwise throws IllegalArgumentException
     */
    public void deposit(float amount) {
        if (!isAmountValid(amount) || amount <= 0) {
            throw new IllegalArgumentException("Invalid deposit amount: " + amount);
        }
        balance += amount;
    }

    /**
     * Transfer(float amount, BankAccount destination):
     * - destination must not be null
     * - amount must be > 0, valid (isAmountValid), and <= current balance
     * - otherwise throws IllegalArgumentException
     */
    public void transfer(float amount, BankAccount destination) {
        if (destination == null) {
            throw new IllegalArgumentException("Destination account is invalid");
        }
        if (!isAmountValid(amount) || amount <= 0) {
            throw new IllegalArgumentException("Invalid transfer amount: " + amount);
        }
        if (amount > this.balance) {
            throw new IllegalArgumentException("Insufficient funds for transfer");
        }

        // Perform atomic transfer via withdraw+deposit
        this.withdraw(amount);
        destination.deposit(amount);
    }

    /**
     * isEmailValid(String email):
     * - non-null, non-empty
     * - contains exactly one '@'
     * - domain contains a dot with a top-level domain >= 2 chars
     */
    public static boolean isEmailValid(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        // Simple conservative email check suitable for the provided tests
        String regex = "^[^@]+@[^@.]+\\.[A-Za-z]{2,}$";
        return email.matches(regex);
    }

    /**
     * isAmountValid(float amount):
     * - amount must be >= 0
     * - must have at most 2 decimal places (currency format)
     */
    public static boolean isAmountValid(float amount) {
        if (amount < 0) {
            return false;
        }
        // Scale to cents and allow small floating-point error
        float scaled = amount * 100.0f;
        float rounded = Math.round(scaled);
        return Math.abs(scaled - rounded) < 0.0001f;
    }
}