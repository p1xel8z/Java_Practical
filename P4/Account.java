public class Account {

    private final String accountNumber;
    private String ownerName;
    private long balance;
    private boolean active;

    private static int accountCounter = 0;

    Account(String ownerName, long openingBalance) {
        this.ownerName = ownerName;
        balance = openingBalance;
        active = true;
        accountNumber = generateAccountId();
    }

    Account(String ownerName) {
        this(ownerName, 0);
    }

    private static String generateAccountId() {
        return "ACC" + String.valueOf(++accountCounter);
    }

    public void deposit(long amount) {
        if (amount > 0)
            balance += amount;
        else
            System.out.println("Invalid Amount");
    }

    public boolean withdraw(long amount) {
        if (amount > 0 && balance - amount >= 0) {
            balance -= amount;
            return true;
        }

        if (amount <= 0)
            System.out.println("Invalid Amount");
        else
            System.out.println("Insufficient Balance");

        return false;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

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

        if (this == obj)
            return true;

        if (!(obj instanceof Account))
            return false;

        Account other = (Account) obj;

        return accountNumber.equals(other.accountNumber);
    }


    @Override
    public int hashCode() {
        return accountNumber.hashCode();
    }
}