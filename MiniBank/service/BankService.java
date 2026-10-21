package service;

import exception.AccountNotFoundException;
import exception.BankException;
import exception.InvalidAmountException;
import model.Account;
import model.Command;
import model.CurrentAccount;
import model.FixedDepositAccount;
import model.SavingsAccount;
import model.annotation.Audit;
import util.AnnotationValidator;
import util.StatementFormatter;
import util.Validator;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class BankService {

    private final Repository<Account> repository = new Repository<>();

    @Audit
    public Account openAccount(String name, String type, long openingBalance)
            throws InvalidAmountException {

        logAudit("openAccount");

        if (openingBalance < 0) {
            throw new InvalidAmountException("Opening balance cannot be negative");
        }

        Account account = switch (type.toUpperCase()) {
            case "SAVINGS" -> new SavingsAccount(name, openingBalance, 1000);
            case "CURRENT" -> new CurrentAccount(name, openingBalance, 5000);
            case "FIXED"   -> new FixedDepositAccount(name, openingBalance);
            default -> throw new IllegalArgumentException("Unknown type: " + type
                    + "  (use SAVINGS, CURRENT or FIXED)");
        };

        String[] errors = AnnotationValidator.validate(account);
        if (errors.length > 0) {
            throw new InvalidAmountException("Validation failed: " + String.join(", ", errors));
        }

        repository.save(account);
        return account;
    }

    @Audit
    public Account openAccount(String name, String type, long openingBalance, String email, String mobile)
            throws BankException {

        logAudit("openAccount");

        if (email != null && !email.trim().isEmpty() && !Validator.isValidEmail(email)) {
            throw new BankException("Invalid email format: " + email);
        }
        if (mobile != null && !mobile.trim().isEmpty() && !Validator.isValidMobile(mobile)) {
            throw new BankException("Invalid mobile format: " + mobile);
        }

        return openAccount(name, type, openingBalance);
    }

    @Audit
    public void deposit(String accountNumber, long amount)
            throws AccountNotFoundException, BankException {

        logAudit("deposit");
        findAccount(accountNumber).deposit(amount);
    }

    @Audit
    public void withdraw(String accountNumber, long amount)
            throws AccountNotFoundException, BankException {

        logAudit("withdraw");
        findAccount(accountNumber).withdraw(amount);
    }

    @Audit
    public void transfer(String fromNumber, String toNumber, long amount)
            throws AccountNotFoundException, BankException {

        logAudit("transfer");
        Account from = findAccount(fromNumber);
        Account to   = findAccount(toNumber);
        from.transfer(to, amount);
    }

    @Audit
    public String statement(String accountNumber) throws AccountNotFoundException {
        logAudit("statement");
        return StatementFormatter.buildStatement(findAccount(accountNumber));
    }

    @Audit
    public ArrayList<Account> listAccounts() {
        logAudit("listAccounts");
        return repository.findAll();
    }

    @Audit
    public void processBatchCommands(List<Command> commands) throws InterruptedException {
        logAudit("processBatchCommands");
        TransactionProcessor processor = new TransactionProcessor();
        for (Account a : repository.findAll()) {
            processor.addAccount(a);
        }

        for (Command cmd : commands) {
            processor.submit(() -> {
                try {
                    switch (cmd.type()) {
                        case DEPOSIT -> deposit(cmd.accountNumber(), cmd.amount());
                        case WITHDRAW -> withdraw(cmd.accountNumber(), cmd.amount());
                        case TRANSFER -> { /* transfer handled between accounts */ }
                    }
                } catch (Exception e) {
                    System.out.println("Batch error [" + cmd.accountNumber() + "]: " + e.getMessage());
                }
            });
        }
        processor.stop();
    }

    public void loadAll(Account[] accounts) {
        for (Account a : accounts) repository.save(a);
    }

    public Account[] toArray() {
        return repository.findAll().toArray(new Account[0]);
    }

    public Account findAccount(String accountNumber) throws AccountNotFoundException {
        Account account = repository.findById(accountNumber);
        if (account == null) {
            throw new AccountNotFoundException("Account not found: " + accountNumber);
        }
        return account;
    }

    private void logAudit(String methodName) {
        for (Method m : this.getClass().getDeclaredMethods()) {
            if (m.getName().equals(methodName)
                    && m.isAnnotationPresent(Audit.class)) {
                System.out.println("[AUDIT] BankService." + methodName + "()");
                break;
            }
        }
    }
}
