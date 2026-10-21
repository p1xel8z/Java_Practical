import java.util.Scanner;

class Driver {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter size: ");
        int n = sc.nextInt();
        sc.nextLine();

        int counter = 0;
        Card cards[] = new Card[n];

        for (int i = 0; i < cards.length; i++) {
            System.out.print("Enter rank: ");
            String rank = sc.nextLine();
            System.out.print("Enter suit: ");
            String suit = sc.nextLine();
            boolean appeared = false;

            Card temp = new Card(rank, suit);

            for (int j = 0; j < counter; j++) {
                if (cards[j].equals(temp)) {
                    appeared = true;
                    System.out.println("Duplicate found: " + temp);
                    break;
                }
            }

            if (!appeared) {
                cards[counter] = temp;
                counter++;
            }
        }

        sc.close();
    }
}