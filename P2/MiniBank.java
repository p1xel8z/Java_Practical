import java.util.Scanner;

record BankInfo(String name, String branch) {
}

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

        Account[] accounts = new Account[3];

        accounts[0] = new Account("David", 1000);
        accounts[1] = new Account("Mark", 2000);
        accounts[2] = new Account("Peter", 3000);


        accounts[0].deposit(500);
        accounts[1].withdraw(500);
        accounts[2].deposit(1000);


        System.out.println(" ");
        System.out.println("-------- Account Details --------");
        System.out.println(" ");

        for (Account account : accounts) {
            System.out.println(
                    "Account Number: " + account.getAccountNumber());
            System.out.println(
                    "Owner Name: " + account.getOwnerName());
            System.out.println(
                    "Balance: " + account.getBalance());
            System.out.println("-------------------------------");
        }

        MenuOption option;

        do {
            System.out.println(" ");
            System.out.println("-------- Virtual Mini Bank --------");
            System.out.println(" ");

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
                    System.out.print("Enter account ID: ");
                    String id = scanner.next();

                    int index = -1;

                    for (int i = 0; i < accounts.length; i++) {
                        if (accounts[i].getAccountNumber().equals(id)) {
                            index = i;
                            break;
                        }
                    }

                    if (index != -1) {
                        System.out.print("Enter amount to deposit: ");
                        long amount = scanner.nextLong();

                        accounts[index].deposit(amount);

                        System.out.println(
                                "New balance: " +
                                accounts[index].getBalance());
                    } else {
                        System.out.println("Account not found");
                    }
                }

                case WITHDRAW -> {
                    System.out.print("Enter account ID: ");
                    String id = scanner.next();

                    int index = -1;

                    for (int i = 0; i < accounts.length; i++) {
                        if (accounts[i].getAccountNumber().equals(id)) {
                            index = i;
                            break;
                        }
                    }

                    if (index != -1) {
                        System.out.print("Enter amount to withdraw: ");
                        long amount = scanner.nextLong();

                        if (accounts[index].withdraw(amount)) {
                            System.out.println(
                                    "New balance: " +
                                    accounts[index].getBalance());
                        }
                    } else {
                        System.out.println("Account not found");
                    }
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