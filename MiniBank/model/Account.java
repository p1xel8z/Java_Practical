package model;

import java.io.Serializable;

import model.annotation.Id;
import model.annotation.MaxLength;
import model.annotation.Positive;
import exception.BankException;
import exception.InsufficientFundsException;
import exception.InvalidAmountException;

public abstract class Account
        implements Transactable, InterestBearing, Serializable, Comparable<Account> {

    private static final long serialVersionUID = 1L;

    @Id
    private final String accountNumber;

    @MaxLength(50)
    private String ownerName;

    @Positive
    private long balance;

    private boolean active;

    private static int accountCounter = 0;

    public Account(String ownerName, long openingBalance) {
        this.ownerName = ownerName;
        this.balance = openingBalance;
        this.active = true;
        this.accountNumber = generateAccountId();
    }

    public Account(String ownerName) {
        this(ownerName, 0);
    }

    private static synchronized String generateAccountId() {
        return "ACC" + (++accountCounter);
    }

    @Override
    public synchronized void deposit(long amount) throws InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Deposit amount must be > 0");
        }
        balance += amount;
    }

    @Override
    public synchronized boolean withdraw(long amount)
            throws InsufficientFundsException, InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Withdrawal amount must be > 0");
        }
        if (!canWithdraw(amount)) {
            long shortfall = Math.max(0, amount - balance);
            throw new InsufficientFundsException(
                    "Insufficient funds: shortfall is " + shortfall, shortfall);
        }
        balance -= amount;
        return true;
    }

    // Unsafe transfer for deadlock demo
    public void transferUnsafe(Account to, long amount) throws BankException {
        synchronized (this) {
            try { Thread.sleep(50); } catch (InterruptedException ignored) {}
            synchronized (to) {
                try {
                    this.withdraw(amount);
                    to.deposit(amount);
                } catch (BankException e) {
                    throw e;
                }
            }
        }
    }

    // Ordered locking by account number
    public void transfer(Account to, long amount) throws BankException {
        Account first  = this.accountNumber.compareTo(to.accountNumber) < 0 ? this : to;
        Account second = (first == this) ? to : this;

        synchronized (first) {
            synchronized (second) {
                try {
                    this.withdraw(amount);
                    to.deposit(amount);
                } catch (BankException e) {
                    throw e;
                } finally {
                    System.out.println("Transfer attempt: "
                            + this.accountNumber + " -> " + to.accountNumber
                            + "  amount=" + amount);
                }
            }
        }
    }

    public abstract boolean canWithdraw(long amount);

    @Override
    public abstract double interestRate();

    public String getAccountNumber() { return accountNumber; }
    public String getOwnerName()     { return ownerName; }

    @Override
    public synchronized long getBalance() { return balance; }

    public boolean isActive() { return active; }

    @Override
    public String toString() {
        return "Account Number: " + accountNumber
                + ", Owner Name: " + ownerName
                + ", Balance: " + balance;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Account other)) return false;
        return accountNumber.equals(other.accountNumber);
    }

    @Override
    public int hashCode() {
        return accountNumber.hashCode();
    }

    @Override
    public int compareTo(Account other) {
        return this.accountNumber.compareTo(other.accountNumber);
    }
}
