import org.junit.Test;
import static org.junit.Assert.*;

// ...existing code...

/**
 * Tests generated from tests.yaml.
 *
 * Equivalence classes & boundaries:
 * - Amounts: negative / zero / positive; <= balance / > balance; 0–2 decimals / 3+ decimals
 * - Email: empty / missing '@' / invalid TLD / valid basic formats
 * - Transfers: valid vs insufficient funds vs invalid destination vs invalid amounts
 */
public class BankAccountTest {

    // -------- withdraw tests --------

    // EC: valid amount strictly between 0 and balance
    @Test
    void withdraw_validAmountBetweenZeroAndBalance() {
        BankAccount account = new BankAccount("a@b.com", 200f);
        account.withdraw(100f);
        assertEquals(100f, account.getBalance());
    }

    // Boundary: amount equal to balance
    @Test
    void withdraw_validAmountEqualToBalance() {
        BankAccount account = new BankAccount("a@b.com", 200f);
        account.withdraw(200f);
        assertEquals(0f, account.getBalance());
    }

    // Boundary: amount equal to 0 (invalid)
    @Test
    void withdraw_invalidAmountZero() {
        BankAccount account = new BankAccount("a@b.com", 200f);
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(0f));
    }

    // EC: negative withdrawal
    @Test
    void withdraw_invalidNegativeAmount() {
        BankAccount account = new BankAccount("a@b.com", 200f);
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(-50f));
    }

    // EC: withdrawal larger than balance
    @Test
    void withdraw_invalidLargerThanBalance() {
        BankAccount account = new BankAccount("a@b.com", 200f);
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(500f));
    }

    // EC: invalid 3-decimal withdrawal
    @Test
    void withdraw_invalidThreeDecimalAmount() {
        BankAccount account = new BankAccount("a@b.com", 200f);
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(500.001f));
    }

    // -------- deposit tests --------

    // EC: valid positive deposit
    @Test
    void deposit_validDeposit() {
        BankAccount account = new BankAccount("a@b.com", 200f);
        account.deposit(100f);
        assertEquals(300f, account.getBalance());
    }

    // EC: invalid negative deposit (tests.yaml description typo; intent is negative)
    @Test
    void deposit_invalidNegativeDeposit() {
        BankAccount account = new BankAccount("a@b.com", 200f);
        assertThrows(IllegalArgumentException.class, () -> account.deposit(-100f));
    }

    // EC: invalid 3-decimal deposit
    @Test
    void deposit_invalidThreeDecimalDeposit() {
        BankAccount account = new BankAccount("a@b.com", 200f);
        assertThrows(IllegalArgumentException.class, () -> account.deposit(100.001f));
    }

    // -------- constructor tests --------

    // EC: valid constructor
    @Test
    void constructor_valid() {
        BankAccount account = new BankAccount("a@b.com", 2000f);
        assertEquals("a@b.com", account.getEmail());
        assertEquals(2000f, account.getBalance());
    }

    // EC: invalid email
    @Test
    void constructor_invalidEmail() {
        assertThrows(IllegalArgumentException.class, () -> new BankAccount("adddcom", 2000f));
    }

    // EC: invalid balance (negative)
    @Test
    void constructor_invalidNegativeBalance() {
        assertThrows(IllegalArgumentException.class, () -> new BankAccount("a@b.com", -2000f));
    }

    // -------- transfer tests --------

    // EC: valid transfer
    @Test
    void transfer_validTransfer() {
        BankAccount source = new BankAccount("a@b.com", 200f);
        BankAccount dest = new BankAccount("b@b.com", 0f);

        source.transfer(100f, dest);

        assertEquals(100f, source.getBalance());
        assertEquals(100f, dest.getBalance());
    }

    // Boundary: transfer entire balance
    @Test
    void transfer_validTransferEdgeCaseAllFunds() {
        BankAccount source = new BankAccount("a@b.com", 200f);
        BankAccount dest = new BankAccount("b@b.com", 0f);

        source.transfer(200f, dest);

        assertEquals(0f, source.getBalance());
        assertEquals(200f, dest.getBalance());
    }

    // EC: insufficient funds
    @Test
    void transfer_invalidInsufficientFunds() {
        BankAccount source = new BankAccount("a@b.com", 200f);
        BankAccount dest = new BankAccount("b@b.com", 0f);

        assertThrows(IllegalArgumentException.class, () -> source.transfer(300f, dest));
    }

    // EC: invalid destination (null)
    @Test
    void transfer_invalidDestination() {
        BankAccount source = new BankAccount("a@b.com", 200f);
        assertThrows(IllegalArgumentException.class, () -> source.transfer(100f, null));
    }

    // EC: invalid transfer amount (negative)
    @Test
    void transfer_invalidNegativeAmount() {
        BankAccount source = new BankAccount("a@b.com", 200f);
        BankAccount dest = new BankAccount("b@b.com", 0f);

        assertThrows(IllegalArgumentException.class, () -> source.transfer(-300f, dest));
    }

    // EC: invalid transfer amount (3 decimals)
    @Test
    void transfer_invalidThreeDecimalAmount() {
        BankAccount source = new BankAccount("a@b.com", 200f);
        BankAccount dest = new BankAccount("b@b.com", 0f);

        assertThrows(IllegalArgumentException.class, () -> source.transfer(100.001f, dest));
    }

    // -------- isEmailValid tests --------

    // EC: valid basic email
    @Test
    void isEmailValid_valid1() {
        assertTrue(BankAccount.isEmailValid("a@b.com"));
    }

    // EC: valid email with longer domain segments
    @Test
    void isEmailValid_valid2() {
        assertTrue(BankAccount.isEmailValid("a@bb.cc"));
    }

    // EC: empty email
    @Test
    void isEmailValid_invalidEmpty() {
        assertFalse(BankAccount.isEmailValid(""));
    }

    // EC: missing '@'
    @Test
    void isEmailValid_invalidNoAt() {
        assertFalse(BankAccount.isEmailValid("testemail.com"));
    }

    // EC: invalid TLD (too short)
    @Test
    void isEmailValid_invalidDomain() {
        assertFalse(BankAccount.isEmailValid("jbob@jimbo.h"));
    }

    // -------- isAmountValid tests --------

    // EC: valid whole amount
    @Test
    void isAmountValid_valid1() {
        assertTrue(BankAccount.isAmountValid(100f));
    }

    // EC: valid 2-decimal amount
    @Test
    void isAmountValid_valid2() {
        assertTrue(BankAccount.isAmountValid(1000.05f));
    }

    // EC: valid 1-decimal amount
    @Test
    void isAmountValid_valid3() {
        assertTrue(BankAccount.isAmountValid(1000.1f));
    }

    // EC: invalid 3-decimal amount
    @Test
    void isAmountValid_invalidThreeDecimals() {
        assertFalse(BankAccount.isAmountValid(100.001f));
    }

    // EC: invalid negative amount
    @Test
    void isAmountValid_invalidNegative() {
        assertFalse(BankAccount.isAmountValid(-100f));
    }
}