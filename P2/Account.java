class Account {
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

    private String generateAccountId() {

        accountCounter++;
         String accountNumber = String.format("AC%04d" , accountCounter);
        return accountNumber;

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

        else if (amount < 0)
            System.out.println("Invalid Amount");
        else
            System.out.println("Insufficient Balance");

        return false;
    }

    public String getAccountNumber(){return accountNumber;}

    public String getOwnerName() {
        return ownerName;
    }

    public long getBalance() {
        return balance;
    }

    public boolean isActive() {
        return active;
    }
}