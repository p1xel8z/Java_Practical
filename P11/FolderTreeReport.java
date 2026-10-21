
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.util.Scanner;

public class FolderTreeReport {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter folder path to scan: ");
        Path root = Paths.get(sc.nextLine()).toAbsolutePath().normalize();

        System.out.print("Enter report filename (e.g. folder_report.txt): ");
        Path report = Paths.get(sc.nextLine()).toAbsolutePath().normalize();

        sc.close();

        if (!Files.isDirectory(root)) {
            System.out.println("Error: The specified folder does not exist.");
            return;
        }

        if (report.startsWith(root)) {
            System.out.println("Error: Save the report outside the folder being scanned.");
            return;
        }

        try {
            Path parent = report.getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }

            try (var writer = Files.newBufferedWriter(report)) {
                writer.write("FOLDER TREE REPORT");
                writer.newLine();
                writer.write("Root: " + root);
                writer.newLine();
                writer.write("==============================================");
                writer.newLine();

                Files.walkFileTree(root, new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult visitFile(
                            Path file, BasicFileAttributes attrs) throws IOException {

                        writer.write("Name: " + root.relativize(file));
                        writer.newLine();
                        writer.write("Size: " + attrs.size() + " bytes");
                        writer.newLine();
                        writer.write("Modified: " +
                                Instant.ofEpochMilli(attrs.lastModifiedTime().toMillis()));
                        writer.newLine();
                        writer.write("----------------------------------------------");
                        writer.newLine();

                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult visitFileFailed(
                            Path file, IOException exc) throws IOException {

                        writer.write("Could not access: " + file);
                        writer.newLine();
                        writer.write("Reason: " + exc.getMessage());
                        writer.newLine();

                        return FileVisitResult.CONTINUE;
                    }
                });
            }

            System.out.println("Report generated successfully.");
            System.out.println("Saved to: " + report);

        } catch (IOException e) {
            System.out.println("Error generating report: " + e.getMessage());
        }
    }
}
