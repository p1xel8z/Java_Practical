
import java.util.*;

public class Leaderboard {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Map<String, Integer> scores = new HashMap<>();

        System.out.print("Enter number of players: ");
        int n = Integer.parseInt(sc.nextLine());

        for (int i = 0; i < n; i++) {
            System.out.print("Enter player name: ");
            String name = sc.nextLine().trim();

            System.out.print("Enter score for " + name + ": ");
            int score = Integer.parseInt(sc.nextLine());

            scores.put(name, score);
        }

        System.out.print("Enter N (number of top players): ");
        int topN = Integer.parseInt(sc.nextLine());

        List<Map.Entry<String, Integer>> leaderboard =
                new ArrayList<>(scores.entrySet());

        leaderboard.sort(
            Comparator.comparingInt(
                (Map.Entry<String, Integer> e) -> e.getValue()
            ).reversed().thenComparing(Map.Entry::getKey)
        );

        int limit = Math.max(0, Math.min(topN, leaderboard.size()));

        System.out.println("\n--- Leaderboard ---");
        System.out.printf("%-6s %-20s %s%n", "Rank", "Player", "Score");

        for (int i = 0; i < limit; i++) {
            Map.Entry<String, Integer> player = leaderboard.get(i);

            System.out.printf("%-6d %-20s %d%n",
                    i + 1, player.getKey(), player.getValue());
        }

        sc.close();
    }
}
