package service;

import java.util.Scanner;

import model.Account;
import model.SavingsAccount;
import model.CurrentAccount;
import model.FixedDepositAccount;
import model.Command;
import model.Customer;
import model.BankInfo;

import util.CommandParser;
import util.StatementFormatter;

import static util.Validator.isValidEmail;

public class MiniBank {

    enum MenuOption {
        OPEN_ACCOUNT,
        DEPOSIT,
        WITHDRAW,
        TRANSFER,
        EXIT
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

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
            System.out.println("Savings minimum balance: "
                    + savings.getMinBalance());
        }

        // WithdrawRule using an anonymous class
        WithdrawRule anonymousRule = new WithdrawRule() {
            @Override
            public boolean allow(Account account, long amount) {
                return amount > 0 && account.canWithdraw(amount);
            }
        };

        // WithdrawRule using a lambda expression
        WithdrawRule lambdaRule =
                (account, amount) -> amount > 0 && account.canWithdraw(amount);

        System.out.println("\n-------- WithdrawRule Demo --------");
        System.out.println("Anonymous class allows withdrawal: "
                + anonymousRule.allow(accounts[0], 1000));

        System.out.println("Lambda allows withdrawal: "
                + lambdaRule.allow(accounts[0], 1000));

        // Validator tests - static import used for isValidEmail()
        System.out.println("\n-------- Validator Tests --------");

        System.out.println("Mobile 9876543210: "
                + (util.Validator.isValidMobile("9876543210")
                ? "Valid" : "Invalid"));

        System.out.println("Mobile 987654321: "
                + (util.Validator.isValidMobile("987654321")
                ? "Valid" : "Invalid"));

        System.out.println("Email test@gmail.com: "
                + (isValidEmail("test@gmail.com") ? "Valid" : "Invalid"));

        System.out.println("Email test@gmail: "
                + (isValidEmail("test@gmail") ? "Valid" : "Invalid"));

        System.out.println("PAN ABCDE1234F: "
                + (util.Validator.isValidPan("ABCDE1234F")
                ? "Valid" : "Invalid"));

        System.out.println("PAN ABC1234: "
                + (util.Validator.isValidPan("ABC1234")
                ? "Valid" : "Invalid"));

        System.out.println("IFSC SBIN0001234: "
                + (util.Validator.isValidIfsc("SBIN0001234")
                ? "Valid" : "Invalid"));

        System.out.println("IFSC SBI123: "
                + (util.Validator.isValidIfsc("SBI123")
                ? "Valid" : "Invalid"));

        // Command parser demo
        Command command =
                CommandParser.parse("DEPOSIT ACC1 500");

        System.out.println("\n-------- Command --------");
        System.out.println("Type: " + command.type());
        System.out.println("Account Number: " + command.accountNumber());
        System.out.println("Amount: " + command.amount());

        // Statement formatter demo
        System.out.println("\n"
                + StatementFormatter.buildStatement(accounts[0]));

        // Customer and clone demo
        Customer customer =
                new Customer("David", "david@gmail.com", "9876543210");

        customer.setAddress(
                new Customer.Address("123 Main Road", "Ahmedabad", "380001"));

        Customer copiedCustomer = customer.clone();

        System.out.println("\nCustomer ID: " + customer.getCustomerId());
        System.out.println("Cloned Customer ID: "
                + copiedCustomer.getCustomerId());

        // P3 equals demo
        System.out.println("\nAccounts equal: "
                + accounts[0].equals(accounts[1]));

        MenuOption option;

        do {
            System.out.println("\n-------- Virtual Mini Bank --------");
            System.out.println("1. Open Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. Exit");

            System.out.print("Enter option: ");
            int choice = scanner.nextInt();

            option = switch (choice) {
                case 1 -> MenuOption.OPEN_ACCOUNT;
                case 2 -> MenuOption.DEPOSIT;
                case 3 -> MenuOption.WITHDRAW;
                case 4 -> MenuOption.TRANSFER;
                case 5 -> MenuOption.EXIT;
                default -> null;
            };

            if (option == null) {
                System.out.println("Invalid option");
                continue;
            }

            switch (option) {
                case OPEN_ACCOUNT ->
                        System.out.println("Open Account - to be implemented");

                case DEPOSIT, WITHDRAW -> {
                    System.out.print("Enter account number: ");
                    String id = scanner.next();

                    Account selected = null;

                    for (Account account : accounts) {
                        if (account.getAccountNumber().equals(id)) {
                            selected = account;
                            break;
                        }
                    }

                    if (selected == null) {
                        System.out.println("Account not found");
                        break;
                    }

                    System.out.print("Enter amount: ");
                    long amount = scanner.nextLong();

                    if (option == MenuOption.DEPOSIT) {
                        selected.deposit(amount);
                        System.out.println("New Balance: "
                                + selected.getBalance());
                    } else {
                        if (selected.withdraw(amount)) {
                            System.out.println("Withdrawal successful");
                        } else {
                            System.out.println("Withdrawal failed");
                        }

                        System.out.println("Balance: "
                                + selected.getBalance());
                    }
                }

                case TRANSFER ->
                        System.out.println("Transfer - to be implemented");

                case EXIT ->
                        System.out.println("Goodbye!");
            }

            System.out.println("---------------");

        } while (option != MenuOption.EXIT);

        scanner.close();
    }
}