
import java.util.*;

public class WordFrequency {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter a paragraph:");
        String paragraph = sc.nextLine().toLowerCase();

        // Split into words, ignoring punctuation
        String[] words = paragraph.split("[^a-z0-9']+");

        Map<String, Integer> frequency = new HashMap<>();

        for (String word : words) {
            if (!word.isEmpty()) {
                frequency.put(word, frequency.getOrDefault(word, 0) + 1);
            }
        }

        // Sort entries by frequency descending, then word alphabetically
        List<Map.Entry<String, Integer>> entries =
                new ArrayList<>(frequency.entrySet());

        entries.sort(
            Comparator.comparingInt(
                (Map.Entry<String, Integer> e) -> e.getValue()
            ).reversed().thenComparing(Map.Entry::getKey)
        );

        System.out.println("\nWord frequencies (highest first):");
        for (Map.Entry<String, Integer> entry : entries) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }

        sc.close();
    }
}
