package test;

import exception.AccountNotFoundException;
import exception.InsufficientFundsException;
import exception.InvalidAmountException;
import model.Account;
import model.SavingsAccount;
import service.BankService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;


public class BankServiceTest {

    private BankService service;
    private String savingsId;

    @BeforeEach
    void setUp() throws Exception {
        service   = new BankService();
        Account a = service.openAccount("Alice", "SAVINGS", 5000);
        savingsId = a.getAccountNumber();
    }

    // ----------------------------------------------------------------
    // 1. Deposit increases balance
    // ----------------------------------------------------------------
    @Test
    void depositIncreasesBalance() throws Exception {
        service.deposit(savingsId, 1000);
        String stmt = service.statement(savingsId);
        assertTrue(stmt.contains("6000"),
                "Balance should be 6000 after depositing 1000 into account with 5000");
    }

    // ----------------------------------------------------------------
    // 2. Withdrawal over limit throws InsufficientFundsException
    // ----------------------------------------------------------------
    @Test
    void withdrawOverLimitThrowsException() {
        // SavingsAccount: balance=5000, minBalance=1000 → max withdrawal=4000
        assertThrows(InsufficientFundsException.class,
                () -> service.withdraw(savingsId, 10_000),
                "Withdrawing 10 000 from a 5000-balance savings account should throw");
    }

    // ----------------------------------------------------------------
    // 3. Concurrent deposits produce the correct total
    // ----------------------------------------------------------------
    @Test
    void concurrentDepositsAreCorrect() throws Exception {
        Account shared = service.openAccount("Shared", "SAVINGS", 1000);
        String sharedId = shared.getAccountNumber();

        int threads     = 10;
        long perDeposit = 100;
        CountDownLatch latch = new CountDownLatch(threads);

        for (int i = 0; i < threads; i++) {
            new Thread(() -> {
                try {
                    service.deposit(sharedId, perDeposit);
                } catch (AccountNotFoundException | InvalidAmountException e) {
                    fail("Unexpected exception: " + e.getMessage());
                } catch (Exception e) {
                    fail("Unexpected exception: " + e.getMessage());
                } finally {
                    latch.countDown();
                }
            }).start();
        }

        latch.await();
        assertEquals(1000 + (threads * perDeposit), shared.getBalance(),
                "1000 opening + 10 threads × 100 should give balance 2000");
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Running BankServiceTest suite directly...");
        BankServiceTest suite = new BankServiceTest();

        suite.setUp();
        suite.depositIncreasesBalance();
        System.out.println("PASS: depositIncreasesBalance");

        suite.setUp();
        suite.withdrawOverLimitThrowsException();
        System.out.println("PASS: withdrawOverLimitThrowsException");

        suite.setUp();
        suite.concurrentDepositsAreCorrect();
        System.out.println("PASS: concurrentDepositsAreCorrect");

        System.out.println("\nAll 3 tests passed successfully!");
    }
}
