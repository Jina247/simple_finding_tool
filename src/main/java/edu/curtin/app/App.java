package edu.curtin.app;

import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Entry point into the application.
 */
public class App {
    private static final Logger log = Logger.getLogger(App.class.getName());

    public static void main(String[] args) {
        String dirPath = (args.length < 1)
                ? FileSystems.getDefault().getPath(".").toString()
                : args[0];

        FileSystemIO fileSystemIO = new FileSystemIO();
        fileSystemIO.readFileSystem(dirPath);
        System.out.println("Reading: " + dirPath);

        Report countReport = new CountReport();
        List<Criterion> criteria = new ArrayList<>();
        countReport.generate(fileSystemIO.readFileSystem(dirPath), criteria);

        Report showReport = new ShowReport();
        showReport.generate(fileSystemIO.readFileSystem(dirPath), criteria);
    }

    public static void readCriteria(Scanner sc) {
        List<String> inputArr = new ArrayList<>();
        System.out.println("Enter search criteria (one per line, blank line to finish):");
        System.out.println("Format: [+/-] [t/r] [search pattern]");
        System.out.println("  + = include, - = exclude");
        System.out.println("  t = text search, r = regex search");
        System.out.println("Examples:");
        System.out.println("  + t class");
        System.out.println("  - r \\d+");
        System.out.println();

        while (true) {
            String input = sc.nextLine();
            if (input.isEmpty()) {
                break;
            }
            inputArr.add(input);
        }
        for (String line : inputArr) {
            try {
                String[] parts = line.split(" ", 3);
                if (parts.length != 3) {
                    System.err.println("Invalid format. Format: [+/-] [t/r] [search pattern]");
                    log.warning("Invalid format");
                    continue;
                }
                String flag = parts[0].trim();
                String criteria = parts[1].trim();
                String searchPattern = parts[2].trim();

                if (!(flag.equals("+")) && !(flag.equals("-"))) {
                    System.err.println("Invalid format: Must be start with '+' or '-'");
                    log.warning("Invalid format");
                    continue;
                }

                if (!(criteria.equals("t")) && !(criteria.equals("r"))) {
                    System.err.println("Invalid format: Second part must be start with 't' or 'r'");
                    log.warning("Invalid format");
                    continue;
                }

                if (searchPattern.trim().isEmpty()) {
                    System.err.println("Invalid format: Search pattern cannot be empty");
                    log.warning("Invalid format");
                    continue;
                }


            } catch (Exception e) {
                System.err.println("Error parsing line" + line + e.getMessage());
            }

        }
    }

}
