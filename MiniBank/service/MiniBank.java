package service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import model.Account;
import model.BankInfo;
import model.Command;
import model.CurrentAccount;
import model.Customer;
import model.FixedDepositAccount;
import model.SavingsAccount;

import exception.AccountNotFoundException;
import exception.BankException;
import exception.InsufficientFundsException;
import exception.InvalidAmountException;

import util.AnnotationValidator;
import util.CommandParser;
import util.ReportGenerator;
import util.StatePersister;
import util.StatementFormatter;
import util.TransactionLog;

import static util.Validator.isValidEmail;

public class MiniBank {

    enum MenuOption {
        OPEN_ACCOUNT,
        DEPOSIT,
        WITHDRAW,
        TRANSFER,
        STATEMENT,
        LIST_ACCOUNTS,
        REPORT,
        BATCH_TRANSACTIONS,
        EXIT
    }

    // Resource for try-with-resources demo
    static class BankResource implements AutoCloseable {
        final String name;

        BankResource(String name) {
            this.name = name;
            System.out.println("[Resource] " + name + " opened");
        }

        @Override
        public void close() {
            System.out.println("[Resource] " + name + " closed automatically");
        }
    }

    public static void main(String[] args) throws Exception {
        runP1toP6Demos();
        runP7Demo();
        runP8Demo();
        runP9Demo();
        runP10Demo();
        runP11Demo();
        runP12Demo();
        runP13Demo();
        runInteractiveMenu();
    }

    // ================================================================
    // P1 - P6 Demos
    // ================================================================
    private static void runP1toP6Demos() {
        BankInfo bank = new BankInfo("Virtual Mini Bank", "Main Branch");
        System.out.println(bank.name() + " - " + bank.branch());

        Account[] accounts = new Account[3];
        accounts[0] = new SavingsAccount("David", 10000, 5000);
        accounts[1] = new CurrentAccount("Mark", 2000, 5000);
        accounts[2] = new FixedDepositAccount("Peter", 20000);

        System.out.println("\n-------- Account Types --------");
        for (Account account : accounts) {
            System.out.println(account);
            System.out.println("Interest Rate: " + account.interestRate() + "%");
            System.out.println("Yearly Interest: " + account.yearlyInterest());
            System.out.println();
        }

        // Pattern matching instanceof
        if (accounts[0] instanceof SavingsAccount savings) {
            System.out.println("Savings minimum balance: " + savings.getMinBalance());
        }

        // WithdrawRule with anonymous class and lambda
        WithdrawRule anonymousRule = new WithdrawRule() {
            @Override
            public boolean allow(Account account, long amount) {
                return amount > 0 && account.canWithdraw(amount);
            }
        };

        WithdrawRule lambdaRule =
                (account, amount) -> amount > 0 && account.canWithdraw(amount);

        System.out.println("\n-------- WithdrawRule Demo --------");
        System.out.println("Anonymous class allows withdrawal: "
                + anonymousRule.allow(accounts[0], 1000));
        System.out.println("Lambda allows withdrawal: "
                + lambdaRule.allow(accounts[0], 1000));

        // Validator tests with static import
        System.out.println("\n-------- Validator Tests --------");
        System.out.println("Mobile 9876543210: "
                + (util.Validator.isValidMobile("9876543210") ? "Valid" : "Invalid"));
        System.out.println("Mobile 987654321: "
                + (util.Validator.isValidMobile("987654321") ? "Valid" : "Invalid"));
        System.out.println("Email test@gmail.com: "
                + (isValidEmail("test@gmail.com") ? "Valid" : "Invalid"));
        System.out.println("Email test@gmail: "
                + (isValidEmail("test@gmail") ? "Valid" : "Invalid"));
        System.out.println("PAN ABCDE1234F: "
                + (util.Validator.isValidPan("ABCDE1234F") ? "Valid" : "Invalid"));
        System.out.println("PAN ABC1234: "
                + (util.Validator.isValidPan("ABC1234") ? "Valid" : "Invalid"));
        System.out.println("IFSC SBIN0001234: "
                + (util.Validator.isValidIfsc("SBIN0001234") ? "Valid" : "Invalid"));
        System.out.println("IFSC SBI123: "
                + (util.Validator.isValidIfsc("SBI123") ? "Valid" : "Invalid"));

        Command command = CommandParser.parse("DEPOSIT ACC1 500");
        System.out.println("\n-------- Command --------");
        System.out.println("Type: " + command.type());
        System.out.println("Account Number: " + command.accountNumber());
        System.out.println("Amount: " + command.amount());

        System.out.println("\n" + StatementFormatter.buildStatement(accounts[0]));

        Customer customer = new Customer("David", "david@gmail.com", "9876543210");
        customer.setAddress(
                new Customer.Address("123 Main Road", "Ahmedabad", "380001"));
        Customer copiedCustomer = customer.clone();
        System.out.println("\nCustomer ID: " + customer.getCustomerId());
        System.out.println("Cloned Customer ID: " + copiedCustomer.getCustomerId());

        System.out.println("\nAccounts equal: " + accounts[0].equals(accounts[1]));
    }

    // ================================================================
    // P7 Demo: Annotations & Reflection
    // ================================================================
    private static void runP7Demo() {
        System.out.println("\n======== P7: Annotations & Reflection ========");

        SavingsAccount invalid = new SavingsAccount("Test", -500, 1000);
        String[] errors = AnnotationValidator.validate(invalid);
        System.out.println("Errors for balance=-500:");
        if (errors.length == 0) {
            System.out.println("  (none)");
        } else {
            for (String e : errors) System.out.println("  - " + e);
        }

        SavingsAccount valid = new SavingsAccount("Alice", 5000, 1000);
        String[] ok = AnnotationValidator.validate(valid);
        System.out.println("Errors for valid account: "
                + (ok.length == 0 ? "none" : String.join(", ", ok)));
    }

    // ================================================================
    // P8 Demo: Exception Handling
    // ================================================================
    private static void runP8Demo() {
        System.out.println("\n======== P8: Exception Handling ========");

        SavingsAccount account = new SavingsAccount("Alice", 5000, 1000);

        // Insufficient funds test
        try {
            account.withdraw(9000);
        } catch (InsufficientFundsException e) {
            System.out.println("Caught InsufficientFundsException: "
                    + e.getMessage() + "  (shortfall=" + e.getShortfall() + ")");
        } catch (InvalidAmountException e) {
            System.out.println("Caught InvalidAmountException: " + e.getMessage());
        } finally {
            System.out.println("Balance is " + account.getBalance());
        }

        // Invalid amount test
        try {
            account.deposit(-100);
        } catch (InvalidAmountException e) {
            System.out.println("Caught InvalidAmountException: " + e.getMessage());
        }

        // Transfer with re-throw
        CurrentAccount current = new CurrentAccount("Bob", 3000, 5000);
        try {
            account.transfer(current, 1000);
            System.out.println("Transfer ok.  Alice=" + account.getBalance()
                    + "  Bob=" + current.getBalance());
        } catch (BankException e) {
            System.out.println("Transfer failed: " + e.getMessage());
        }

        // Try-with-resources test
        System.out.println("\n-- try-with-resources --");
        try (BankResource res = new BankResource("AuditLog")) {
            System.out.println("  Working with: " + res.name);
        }
    }

    // ================================================================
    // P9 Demo: Threading & Synchronization
    // ================================================================
    private static void runP9Demo() throws InterruptedException {
        System.out.println("\n======== P9: Threading ========");

        SavingsAccount shared = new SavingsAccount("Shared", 0, 0);
        int threadCount       = 10;
        int depositsEach      = 100;
        long amountEach       = 10;

        Thread[] threads = new Thread[threadCount];
        for (int i = 0; i < threadCount; i++) {
            threads[i] = new Thread(
                    new AccountWorker(shared, depositsEach, amountEach),
                    "Worker-" + (i + 1));
        }

        for (Thread t : threads) {
            System.out.println("Starting: " + t.getName());
            t.start();
        }

        for (Thread t : threads) t.join();

        long expected = (long) threadCount * depositsEach * amountEach;
        System.out.println("Expected: " + expected
                + "   Actual: " + shared.getBalance()
                + "   Correct (synchronized): " + (shared.getBalance() == expected));
    }

    // ================================================================
    // P10 Demo: Thread Pool & Producer-Consumer
    // ================================================================
    private static void runP10Demo() throws InterruptedException {
        System.out.println("\n======== P10: Thread Pool & Concurrency ========");

        TransactionProcessor processor = new TransactionProcessor();
        SavingsAccount batchAcc = new SavingsAccount("Batch", 0, 0);
        processor.addAccount(batchAcc);

        for (int i = 0; i < 20; i++) {
            processor.submit(() -> {
                try {
                    batchAcc.deposit(100);
                } catch (InvalidAmountException e) {
                    System.out.println("Deposit error: " + e.getMessage());
                }
            });
        }
        processor.stop();
        System.out.println("Batch done. Balance=" + batchAcc.getBalance()
                + " (expected 2000)");

        // Producer-Consumer with wait/notify
        System.out.println("\n-- Producer-Consumer --");
        final Object lock   = new Object();
        final String[] buf  = new String[5];
        final int[]   count = {0};
        final int     total = 6;

        Thread producer = new Thread(() -> {
            for (int i = 0; i < total; i++) {
                synchronized (lock) {
                    while (count[0] == buf.length) {
                        try { lock.wait(); }
                        catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                    }
                    buf[count[0]++] = "TX-" + i;
                    System.out.println("[Producer] added TX-" + i);
                    lock.notify();
                }
            }
        }, "Producer");

        Thread consumer = new Thread(() -> {
            int done = 0;
            while (done < total) {
                synchronized (lock) {
                    while (count[0] == 0) {
                        try { lock.wait(); }
                        catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                    }
                    String tx = buf[--count[0]];
                    System.out.println("[Consumer] processed " + tx);
                    done++;
                    lock.notify();
                }
            }
        }, "Consumer");

        producer.start(); consumer.start();
        producer.join();  consumer.join();

        // Deadlock-free transfer using ordered locking
        System.out.println("\n-- Deadlock-free transfer (ordered locking) --");

        SavingsAccount accA = new SavingsAccount("Alice", 5000, 1000);
        SavingsAccount accB = new SavingsAccount("Bob",   5000, 1000);

        Thread t1 = new Thread(() -> {
            try { accA.transfer(accB, 500); System.out.println("T1: A→B done"); }
            catch (BankException e) { System.out.println("T1 error: " + e.getMessage()); }
        }, "T1-AtoB");

        Thread t2 = new Thread(() -> {
            try { accB.transfer(accA, 300); System.out.println("T2: B→A done"); }
            catch (BankException e) { System.out.println("T2 error: " + e.getMessage()); }
        }, "T2-BtoA");

        t1.start(); t2.start();
        t1.join();  t2.join();
        System.out.println("A=" + accA.getBalance() + "  B=" + accB.getBalance()
                + "  total=" + (accA.getBalance() + accB.getBalance())
                + " (expected 10000)");
    }

    // ================================================================
    // P11 Demo: File I/O & Serialization
    // ================================================================
    private static void runP11Demo() throws Exception {
        System.out.println("\n======== P11: File I/O & Serialisation ========");

        Path dataDir = Paths.get("data");
        Files.createDirectories(dataDir);
        Path stateFile = dataDir.resolve("accounts.dat");
        Path logsDir   = dataDir.resolve("logs");
        Files.createDirectories(logsDir);
        Path logFile   = logsDir.resolve("transactions.log");
        Path reportFile = dataDir.resolve("report.txt");

        Account[] toSave = {
            new SavingsAccount("Alice",   5000,  1000),
            new CurrentAccount("Bob",     3000,  5000),
            new FixedDepositAccount("Charlie", 20000)
        };

        // Save accounts
        StatePersister.save(toSave, stateFile);
        System.out.println("Saved " + toSave.length + " accounts to " + stateFile);

        // Append to transaction log
        TransactionLog.append(logFile, "DEPOSIT  " + toSave[0].getAccountNumber() + " 5000");
        TransactionLog.append(logFile, "DEPOSIT  " + toSave[1].getAccountNumber() + " 3000");
        TransactionLog.append(logFile, "WITHDRAW " + toSave[0].getAccountNumber() + " 1000");

        // Reload accounts
        Account[] loaded = StatePersister.load(stateFile);
        System.out.println("Reloaded accounts:");
        for (Account a : loaded) System.out.println("  " + a);

        // Generate report
        System.out.println(ReportGenerator.generate(logsDir, reportFile));
    }

    // ================================================================
    // P12 Demo: Collections Framework
    // ================================================================
    private static void runP12Demo() {
        System.out.println("\n======== P12: Collections Framework ========");

        AccountStore store = new AccountStore();
        store.add(new SavingsAccount("Charlie", 8000,  1000));
        store.add(new CurrentAccount("Diana",   1500,  5000));
        store.add(new SavingsAccount("Eve",     12000, 1000));
        store.add(new FixedDepositAccount("Frank", 25000));

        System.out.println("Sorted by balance (Comparator):");
        for (Account a : store.sortedByBalance()) {
            System.out.println("  " + a);
        }

        store.recordTransaction("Sample TX recorded");
        System.out.println("History: " + store.getTransactionHistory());
    }

    // ================================================================
    // P13 Demo: Generic Repository
    // ================================================================
    private static void runP13Demo() {
        System.out.println("\n======== P13: Generic Repository ========");

        Repository<Account> repo = new Repository<>();

        SavingsAccount a1 = new SavingsAccount("Grace", 6000, 1000);
        SavingsAccount a2 = new SavingsAccount("Hank",  9000, 1000);
        CurrentAccount a3 = new CurrentAccount("Iris",  2000, 5000);

        repo.saveAll(a1, a2, a3);

        System.out.println("All in repository:");
        for (Account a : repo.findAll()) System.out.println("  " + a);

        Account found = repo.findById(a1.getAccountNumber());
        System.out.println("findById(" + a1.getAccountNumber() + "): " + found);

        // Bounded generic method
        Long[] balances = {6000L, 9000L, 2000L};
        System.out.println("average(balances) = " + Repository.average(balances));

        repo.delete(a3.getAccountNumber());
        System.out.println("After delete, size=" + repo.findAll().size());
    }

    // ================================================================
    // P14: Interactive Menu
    // ================================================================
    private static void runInteractiveMenu() throws Exception {
        System.out.println("\n======== P14: MiniBank - Live Menu ========");

        BankService service = new BankService();
        Path stateFile = Paths.get("data", "accounts.dat");

        // Load persisted accounts
        if (Files.exists(stateFile)) {
            try {
                Account[] saved = StatePersister.load(stateFile);
                service.loadAll(saved);
                System.out.println("Loaded " + saved.length
                        + " account(s) from " + stateFile);
            } catch (Exception e) {
                System.out.println("Could not load accounts: " + e.getMessage());
            }
        }

        Scanner scanner = new Scanner(System.in);
        MenuOption option;

        do {
            System.out.println("\n-------- Virtual Mini Bank --------");
            System.out.println("1. Open Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. Statement");
            System.out.println("6. List Accounts");
            System.out.println("7. Report");
            System.out.println("8. Batch Transactions");
            System.out.println("9. Exit");
            System.out.print("Option: ");

            int choice = scanner.nextInt();
            option = switch (choice) {
                case 1 -> MenuOption.OPEN_ACCOUNT;
                case 2 -> MenuOption.DEPOSIT;
                case 3 -> MenuOption.WITHDRAW;
                case 4 -> MenuOption.TRANSFER;
                case 5 -> MenuOption.STATEMENT;
                case 6 -> MenuOption.LIST_ACCOUNTS;
                case 7 -> MenuOption.REPORT;
                case 8 -> MenuOption.BATCH_TRANSACTIONS;
                case 9 -> MenuOption.EXIT;
                default -> null;
            };

            if (option == null) { System.out.println("Invalid option"); continue; }

            switch (option) {

                case OPEN_ACCOUNT -> {
                    System.out.print("Name: ");            String name = scanner.next();
                    System.out.print("Type (SAVINGS/CURRENT/FIXED): "); String type = scanner.next();
                    System.out.print("Opening balance: "); long bal = scanner.nextLong();
                    try {
                        Account acc = service.openAccount(name, type, bal);
                        System.out.println("Opened: " + acc.getAccountNumber());
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }

                case DEPOSIT -> {
                    System.out.print("Account: "); String id = scanner.next();
                    System.out.print("Amount: ");  long amt  = scanner.nextLong();
                    try {
                        service.deposit(id, amt);
                        System.out.println("Deposit successful");
                    } catch (AccountNotFoundException e) {
                        System.out.println("Not found: " + e.getMessage());
                    } catch (BankException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }

                case WITHDRAW -> {
                    System.out.print("Account: "); String id = scanner.next();
                    System.out.print("Amount: ");  long amt  = scanner.nextLong();
                    try {
                        service.withdraw(id, amt);
                        System.out.println("Withdrawal successful");
                    } catch (InsufficientFundsException e) {
                        System.out.println("Insufficient funds. Shortfall: "
                                + e.getShortfall());
                    } catch (AccountNotFoundException e) {
                        System.out.println("Not found: " + e.getMessage());
                    } catch (BankException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }

                case TRANSFER -> {
                    System.out.print("From: ");   String from = scanner.next();
                    System.out.print("To: ");     String to   = scanner.next();
                    System.out.print("Amount: "); long amt    = scanner.nextLong();
                    try {
                        service.transfer(from, to, amt);
                        System.out.println("Transfer successful");
                    } catch (InsufficientFundsException e) {
                        System.out.println("Insufficient funds. Shortfall: "
                                + e.getShortfall());
                    } catch (AccountNotFoundException e) {
                        System.out.println("Not found: " + e.getMessage());
                    } catch (BankException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }

                case STATEMENT -> {
                    System.out.print("Account: "); String id = scanner.next();
                    try { System.out.println(service.statement(id)); }
                    catch (AccountNotFoundException e) {
                        System.out.println("Not found: " + e.getMessage());
                    }
                }

                case LIST_ACCOUNTS -> {
                    ArrayList<Account> all = service.listAccounts();
                    if (all.isEmpty()) System.out.println("No accounts.");
                    else all.forEach(a -> System.out.println("  " + a));
                }

                case REPORT -> {
                    try {
                        Path logsDir    = Paths.get("data", "logs");
                        Path reportFile = Paths.get("data", "report.txt");
                        System.out.println(ReportGenerator.generate(logsDir, reportFile));
                    } catch (Exception e) {
                        System.out.println("Report error: " + e.getMessage());
                    }
                }

                case BATCH_TRANSACTIONS -> {
                    System.out.println("Executing concurrent batch transactions via TransactionProcessor...");
                    ArrayList<Account> currentAccounts = service.listAccounts();
                    if (currentAccounts.isEmpty()) {
                        System.out.println("No accounts found. Opening demo accounts for batch run...");
                        try {
                            service.openAccount("BatchAlice", "SAVINGS", 5000);
                            service.openAccount("BatchBob", "CURRENT", 10000);
                            currentAccounts = service.listAccounts();
                        } catch (Exception e) {
                            System.out.println("Error initializing batch accounts: " + e.getMessage());
                            break;
                        }
                    }
                    String acc1 = currentAccounts.get(0).getAccountNumber();
                    String acc2 = currentAccounts.size() > 1 ? currentAccounts.get(1).getAccountNumber() : acc1;

                    java.util.List<Command> batchCommands = java.util.List.of(
                            new Command(model.TransactionType.DEPOSIT, acc1, 500),
                            new Command(model.TransactionType.WITHDRAW, acc1, 200),
                            new Command(model.TransactionType.DEPOSIT, acc2, 1000),
                            new Command(model.TransactionType.WITHDRAW, acc2, 300)
                    );

                    try {
                        service.processBatchCommands(batchCommands);
                        System.out.println("Batch transaction processing completed safely!");
                    } catch (InterruptedException e) {
                        System.out.println("Batch processing interrupted: " + e.getMessage());
                    }
                }

                case EXIT -> {
                    // Save accounts on exit
                    try {
                        Path save = Paths.get("data", "accounts.dat");
                        Files.createDirectories(save.getParent());
                        StatePersister.save(service.toArray(), save);
                        System.out.println("Accounts saved. Goodbye!");
                    } catch (Exception e) {
                        System.out.println("Save failed: " + e.getMessage());
                        System.out.println("Goodbye!");
                    }
                }
            }

            System.out.println("---------------");

        } while (option != MenuOption.EXIT);

        scanner.close();
    }
}
