import java.util.Scanner;

class VendingMachine {

    public static void main (String [] args)
    {
        enum Coin {
            ONE,
            TWO,
            FIVE,
            TEN
        };

        int price = 15,total = 0;
        String input;
        Scanner scanner = new Scanner(System.in);


        while(total < price)
        {
            System.out.print("Enter coin (ONE, TWO, FIVE, TEN): ");
            input = scanner.nextLine();
            Coin coin = Coin.valueOf(input.toUpperCase());

           int value =  switch(coin) {
                case ONE ->  1;
                case TWO ->  2;
                case FIVE ->  5;
                case TEN ->  10;
            };

            total += value;
            System.out.println("Total: " + total);

        }

         int change =  total - price;
         System.out.print("Paid. Change: " + change);

         scanner.close();
 

    } 
}