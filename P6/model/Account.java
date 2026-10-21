package model;

public abstract class Account
        implements Transactable, InterestBearing {

    private final String accountNumber;
    private String ownerName;
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

    private static String generateAccountId() {
        return "ACC" + (++accountCounter);
    }

    @Override
    public void deposit(long amount) {
        if (amount > 0) {
            balance += amount;
        } else {
            System.out.println("Invalid Amount");
        }
    }

    @Override
    public boolean withdraw(long amount) {
        if (amount > 0 && canWithdraw(amount)) {
            balance -= amount;
            return true;
        }

        System.out.println("Withdrawal not allowed");
        return false;
    }

    public abstract boolean canWithdraw(long amount);

    @Override
    public abstract double interestRate();

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    @Override
    public long getBalance() {
        return balance;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public String toString() {
        return "Account Number: " + accountNumber
                + ", Owner Name: " + ownerName
                + ", Balance: " + balance;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Account)) {
            return false;
        }

        Account other = (Account) obj;
        return accountNumber.equals(other.accountNumber);
    }

    @Override
    public int hashCode() {
        return accountNumber.hashCode();
    }
}