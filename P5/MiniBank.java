import java.util.Scanner;

record BankInfo(String name, String branch) {}

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

        // P5: Different account types
        Account[] accounts = new Account[3];

        accounts[0] =
                new SavingsAccount("David", 10000, 5000);

        accounts[1] =
                new CurrentAccount("Mark", 2000, 5000);

        accounts[2] =
                new FixedDepositAccount("Peter", 20000);

        // P5: Polymorphism
        System.out.println("\n-------- Account Interest Rates --------");

        for (Account account : accounts) {

            System.out.println(
                    account.getOwnerName()
                    + " - "
                    + account.getClass().getSimpleName()
                    + " - Interest Rate: "
                    + account.interestRate()
                    + "%"
            );
        }

        // P5: Pattern instanceof
        System.out.println("\n-------- Pattern instanceof --------");

        for (Account account : accounts) {

            if (account instanceof SavingsAccount savings) {

                System.out.println(
                        savings.getOwnerName()
                        + " is a SavingsAccount"
                        + " with minimum balance "
                        + savings.getMinBalance()
                );
            }
        }

        // P2: Deposit and withdrawal
        accounts[0].deposit(1000);
        accounts[0].withdraw(2000);

        accounts[1].withdraw(6000);

        accounts[2].withdraw(1000);

        // Display account details
        System.out.println("\n-------- Account Details --------");

        for (Account account : accounts) {
            System.out.println(account);
        }

        // P3: equals()
        System.out.println("\nAre account 1 and account 2 equal?");
        System.out.println(accounts[0].equals(accounts[1]));

        // P4: Validator tests
        System.out.println("\n-------- Validator Tests --------");

        System.out.println(
                "Mobile: 9876543210 -> "
                + (Validator.isValidMobile("9876543210")
                ? "Valid" : "Invalid"));

        System.out.println(
                "Mobile: 987654321 -> "
                + (Validator.isValidMobile("987654321")
                ? "Valid" : "Invalid"));

        System.out.println(
                "Email: test@gmail.com -> "
                + (Validator.isValidEmail("test@gmail.com")
                ? "Valid" : "Invalid"));

        System.out.println(
                "Email: test@gmail -> "
                + (Validator.isValidEmail("test@gmail")
                ? "Valid" : "Invalid"));

        System.out.println(
                "PAN: ABCDE1234F -> "
                + (Validator.isValidPan("ABCDE1234F")
                ? "Valid" : "Invalid"));

        System.out.println(
                "PAN: ABC1234 -> "
                + (Validator.isValidPan("ABC1234")
                ? "Valid" : "Invalid"));

        System.out.println(
                "IFSC: SBIN0001234 -> "
                + (Validator.isValidIfsc("SBIN0001234")
                ? "Valid" : "Invalid"));

        System.out.println(
                "IFSC: SBI123 -> "
                + (Validator.isValidIfsc("SBI123")
                ? "Valid" : "Invalid"));

        // P4: Command parser
        Command command =
                CommandParser.parse("DEPOSIT AC0001 500");

        System.out.println("\n-------- Command --------");
        System.out.println("Type: " + command.type());
        System.out.println("Account Number: " + command.accountNumber());
        System.out.println("Amount: " + command.amount());

        // P4: Statement formatter
        System.out.println("\n" +
                StatementFormatter.buildStatement(accounts[0]));

        // P1/P2 menu
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

                case OPEN_ACCOUNT -> {
                    System.out.println(
                            "Open Account - to be implemented in a later lab");
                }

                case DEPOSIT -> {
                    System.out.println(
                            "Deposit - to be implemented in a later lab");
                }

                case WITHDRAW -> {
                    System.out.println(
                            "Withdraw - to be implemented in a later lab");
                }

                case TRANSFER -> {
                    System.out.println(
                            "Transfer - to be implemented in a later lab");
                }

                case EXIT -> {
                    System.out.println("Goodbye!");
                }
            }

            System.out.println("---------------");

        } while (option != MenuOption.EXIT);

        scanner.close();
    }
}