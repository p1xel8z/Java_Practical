package service;

import model.Account;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TransactionProcessor {

    private final ExecutorService pool;
    private final ConcurrentHashMap<String, Account> accounts;

    public TransactionProcessor() {
        this.pool     = Executors.newFixedThreadPool(4);
        this.accounts = new ConcurrentHashMap<>();
    }

    public void addAccount(Account account) {
        accounts.put(account.getAccountNumber(), account);
    }

    public Account getAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }

    public void submit(Runnable task) {
        pool.execute(task);
    }

    public void stop() throws InterruptedException {
        pool.shutdown();
        pool.awaitTermination(60, TimeUnit.SECONDS);
    }
}
