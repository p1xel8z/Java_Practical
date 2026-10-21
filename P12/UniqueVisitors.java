
import java.util.*;

public class UniqueVisitors {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("How many login entries? ");
        int n = Integer.parseInt(sc.nextLine());

        List<String> logins = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            System.out.print("Enter login name " + (i + 1) + ": ");
            logins.add(sc.nextLine().trim());
        }

        // HashSet stores distinct names
        Set<String> uniqueUsers = new HashSet<>(logins);

        // LinkedHashSet preserves first-seen order
        Set<String> orderedUsers = new LinkedHashSet<>(logins);

        System.out.println("\nUnique visitor count: " + uniqueUsers.size());
        System.out.println("First three distinct visitors:");

        int count = 0;
        for (String user : orderedUsers) {
            if (count == 3) {
                break;
            }

            System.out.println(user);
            count++;
        }

        if (orderedUsers.isEmpty()) {
            System.out.println("No visitors found.");
        }

        sc.close();
    }
}
