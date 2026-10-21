
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Scanner;

public class LogAnalyzer {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Path directory = Paths.get("logs");

        try {
            Files.createDirectories(directory);

            // Create sample log files
            Files.write(directory.resolve("app1.txt"), java.util.Arrays.asList(
                "INFO: Application started",
                "ERROR: Database connection failed",
                "INFO: Retrying connection"
            ));

            Files.write(directory.resolve("app2.txt"), java.util.Arrays.asList(
                "INFO: User logged in",
                "WARNING: Low memory",
                "ERROR: Request failed"
            ));

            Files.write(directory.resolve("app3.txt"), java.util.Arrays.asList(
                "INFO: Service ready",
                "INFO: Request completed"
            ));

            System.out.print("Enter keyword to search: ");
            String keyword = sc.nextLine().toLowerCase();

            long totalLines = 0;
            long matchingLines = 0;

            System.out.println("\nLog Analysis Report");

            try (DirectoryStream<Path> files =
                         Files.newDirectoryStream(directory, "*.txt")) {

                for (Path file : files) {
                    BasicFileAttributes attributes =
                            Files.readAttributes(file, BasicFileAttributes.class);

                    long fileLines = 0;
                    long fileMatches = 0;

                    try (BufferedReader reader = Files.newBufferedReader(file)) {
                        String line;

                        while ((line = reader.readLine()) != null) {
                            fileLines++;

                            if (line.toLowerCase().contains(keyword)) {
                                fileMatches++;
                            }
                        }
                    }

                    totalLines += fileLines;
                    matchingLines += fileMatches;

                    System.out.println("\nFile: " + file.getFileName());
                    System.out.println("Size: " + attributes.size() + " bytes");
                    System.out.println("Lines: " + fileLines);
                    System.out.println("Matching lines: " + fileMatches);
                }
            }

            System.out.println("\n--- Overall Summary ---");
            System.out.println("Total lines: " + totalLines);
            System.out.println("Lines containing \"" + keyword + "\": "
                    + matchingLines);

        } catch (IOException e) {
            System.out.println("File error: " + e.getMessage());
        }

        sc.close();
    }
}
