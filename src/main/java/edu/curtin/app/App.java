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
    private static List<Criterion> curCriteria = getDefaultCriterion();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String dirPath = (args.length < 1)
                ? FileSystems.getDefault().getPath(".").toString()
                : args[0];

        FileSystemIO fileSystemIO = new FileSystemIO();
        FileSystemItem root = fileSystemIO.readFileSystem(dirPath);
        System.out.println("Reading: " + dirPath);

        Report countReport = new CountReport();
        List<Criterion> criteria = new ArrayList<>();
        countReport.generate(root, criteria);

        Report showReport = new ShowReport();
        showReport.generate(root, criteria);
    }

    public static List<Criterion> readCriteria(Scanner sc) {
        List<String> inputLines = new ArrayList<>();
        List<Criterion> criteriaList = new ArrayList<>();

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
            inputLines.add(input);
        }

        for (String line : inputLines) {
            try {
                String[] parts = line.split(" ", 3);
                if (parts.length != 3) {
                    System.err.println("Invalid format. Format: [+/-] [t/r] [search pattern]");
                    log.warning("Invalid format");
                    continue;
                }
                String flag = parts[0].trim();
                String criteriaType = parts[1].trim();
                String searchPattern = parts[2].trim();

                if (!(flag.equals("+")) && !(flag.equals("-"))) {
                    System.err.println("Invalid format: Must be start with '+' or '-'");
                    log.warning("Invalid format");
                    continue;
                }

                if (!(criteriaType.equals("t")) && !(criteriaType.equals("r"))) {
                    System.err.println("Invalid format: Second part must be start with 't' or 'r'");
                    log.warning("Invalid format");
                    continue;
                }

                if (searchPattern.trim().isEmpty()) {
                    System.err.println("Invalid format: Search pattern cannot be empty");
                    log.warning("Invalid format");
                    continue;
                }

                boolean isInclude = flag.equals("+"); // Defines isInclude is "+"
                Criterion criterion;

                if (criteriaType.equals("t")) {
                    criterion = new TextCriterion(isInclude, searchPattern);
                } else {
                    criterion = new RegexCriterion(isInclude, searchPattern);
                }
                criteriaList.add(criterion);
            } catch (Exception e) {
                System.err.println("Error parsing line" + line + e.getMessage());
            }
        }

        if (criteriaList.isEmpty()) {
            System.out.println("No criteria entered. Use the default criteria");
            return null;
        }

        return criteriaList;
    }

    public static List<Criterion> getDefaultCriterion () {
        List<Criterion> defaultCriterion = new ArrayList<>();
        Criterion criterion = new RegexCriterion(true, ".*");
        defaultCriterion.add(criterion);
        return defaultCriterion;
    }

    public static void printMenu() {
        System.out.println();
        System.out.println("a. Set Criteria");
        System.out.println("b. Set Output Format");
        System.out.println("c. Report");
        System.out.println("d. Quit");
    }

}
