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

        BankInfo bank = new BankInfo("Virtual Mini Bank", "Main Branch");

        System.out.println(" ");
        System.out.println("-------- " + bank.name() + " --------");
        System.out.println("Branch: " + bank.branch());
        System.out.println(" ");

        MenuOption option;

        do {
            System.out.println("-------- Menu --------");
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