package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;


public class ReportGenerator {

    public static String generate(Path logsFolder, Path reportFile) throws IOException {
        long totalDeposits    = 0;
        long totalWithdrawals = 0;
        List<String> fileDetails = new ArrayList<>();

        if (Files.exists(logsFolder)) {
            try (DirectoryStream<Path> stream =
                    Files.newDirectoryStream(logsFolder, "*.log")) {

                for (Path logFile : stream) {
                    // File metadata
                    BasicFileAttributes attrs =
                            Files.readAttributes(logFile, BasicFileAttributes.class);
                    fileDetails.add("  " + logFile.getFileName()
                            + "  size=" + attrs.size() + "B"
                            + "  modified=" + attrs.lastModifiedTime());

                    // Read line by line
                    try (BufferedReader reader =
                            new BufferedReader(new FileReader(logFile.toFile()))) {
                        String logLine;
                        while ((logLine = reader.readLine()) != null) {
                            String[] parts = logLine.trim().split("\\s+");
                            if (parts.length >= 3) {
                                try {
                                    long amount = Long.parseLong(parts[2]);
                                    if (parts[0].equalsIgnoreCase("DEPOSIT")) {
                                        totalDeposits += amount;
                                    } else if (parts[0].equalsIgnoreCase("WITHDRAW")) {
                                        totalWithdrawals += amount;
                                    }
                                } catch (NumberFormatException ignored) {
                                    // skip malformed lines
                                }
                            }
                        }
                    }
                }
            }
        }

        StringBuilder report = new StringBuilder();
        report.append("-------- End-of-Day Report --------\n");
        if (fileDetails.isEmpty()) {
            report.append("  No log files found.\n");
        } else {
            for (String d : fileDetails) report.append(d).append("\n");
        }
        report.append("Total Deposits:    ").append(totalDeposits).append("\n");
        report.append("Total Withdrawals: ").append(totalWithdrawals).append("\n");
        report.append("Net:               ").append(totalDeposits - totalWithdrawals).append("\n");
        report.append("-----------------------------------");

        String content = report.toString();
        Files.writeString(reportFile, content,
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

        return content;
    }
}
