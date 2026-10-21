package app;

import java.util.Scanner;
import discounts.DiscountRule;

public class DiscountMain {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        double[] prices = {1000, 2000, 500, 1500};

        double total = 0;

        for (double price : prices) {

            System.out.println("\nPrice: " + price);
            System.out.println("1. 10% discount");
            System.out.println("2. $100 discount");
            System.out.println("3. No discount");

            System.out.print("Choose discount: ");
            int choice = sc.nextInt();

            DiscountRule rule;

            if (choice == 1) {
                rule = p -> p * 0.90;
            }
            else if (choice == 2) {
                rule = p -> p - 100;
            }
            else {
                rule = p -> p;
            }

            double discounted = rule.apply(price);

            System.out.println("Final price: " + discounted);

            total += discounted;
        }

        System.out.println("\nTotal: " + total);

        sc.close();
    }
}