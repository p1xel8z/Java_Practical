import java.util.Scanner;
import java.util.Random;

class RPSLS {

    enum Move {
        ROCK,
        PAPER,
        SCISSORS,
        LIZARD,
        SPOCK
    };

    static int winner(Move computer_move, Move player_move) {
        if (player_move == computer_move)
            return 0;

        switch (player_move) {
            case ROCK -> {
                if (computer_move == Move.SCISSORS || computer_move == Move.LIZARD)
                    return 1;
                else
                    return -1;
            }
            case SCISSORS -> {
                if (computer_move == Move.PAPER || computer_move == Move.LIZARD)
                    return 1;
                else
                    return -1;
            }
            case PAPER -> {
                if (computer_move == Move.SPOCK || computer_move == Move.ROCK)
                    return 1;
                else
                    return -1;
            }
            case LIZARD -> {
                if (computer_move == Move.SPOCK || computer_move == Move.PAPER)
                    return 1;
                else
                    return -1;
            }
            case SPOCK -> {
                if (computer_move == Move.SCISSORS || computer_move == Move.ROCK)
                    return 1;
                else
                    return -1;
            }
            default -> {
                return -2;
            }

        }

    }

    public static void main(String[] agrs) {
        int rounds = 5, result;
        Random random = new Random();
        Scanner scanner = new Scanner(System.in);
        int computer_points = 0, player_points = 0;

        while (rounds > 0) {
            int index = random.nextInt(Move.values().length);
            Move computer_move = Move.values()[index];

            System.out.print("Enter you move: ");
            String input = scanner.nextLine();
            System.out.println("Computer move: " + computer_move.toString());

            Move player_move = Move.valueOf(input.toUpperCase());
            result = winner(computer_move, player_move);
            if (result == -1) {
                computer_points++;
            } else if (result == 1) {
                player_points++;
            }
            rounds--;
        }

        if (player_points > computer_points)
            System.out.println("You win " + player_points + "-" + computer_points);
        else
            System.out.println("Computer wins " + player_points + "-" + computer_points);

        scanner.close();

    }

}