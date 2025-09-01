    package edu.curtin.app;

    import java.nio.file.FileSystems;
    import java.util.ArrayList;
    import java.util.List;
    import java.util.Scanner;
    import java.util.logging.Logger;

    /**
     * Entry point into the application.
     * Provides an interactive menu for setting search criteria and generating reports.
     */

    public class App {
        private static List<Criterion> curCriteria = getDefaultCriterion();
        private static final Logger log = Logger.getLogger(App.class.getName());

        public static void main(String[] args) {
            log.info("Application started");
            try (Scanner sc = new Scanner(System.in)) {
                // Determines directory path from args or use current directory.
                String dirPath = (args.length < 1)
                        ? FileSystems.getDefault().getPath(".").toString()
                        : args[0];
                log.info(() -> "Loading directory: " + dirPath);

                FileSystemIO fileSystemIO = new FileSystemIO();
                FileSystemItem root = fileSystemIO.readFileSystem(dirPath);

                if (root == null) {
                    log.severe(()-> "Failed to load directory: " + dirPath);
                    System.err.println("Failed to load directory: " + dirPath);
                    return;
                }

                System.out.println("Reading: " + dirPath);
                Report curReport = new CountReport();
                log.info(() -> "Default output format set to 'Count Report' ");

                while (true) {
                    printMenu();
                    String option = sc.nextLine();
                    log.fine(() -> "User selected option: " + option);

                    switch (option) {
                        case "a":
                            curCriteria = readCriteria(sc);
                            break;

                        case "b":
                            System.out.println("Choose which report format by the following options:");
                            System.out.println("1. Count of matching lines per file");
                            System.out.println("2. Show actual matching lines");
                            String choice = sc.nextLine();

                            switch (choice) {
                                case "1":
                                    curReport = new CountReport();
                                    System.out.println("Report type: Counting report");
                                    log.info(()-> "Report type to 'count'");
                                    break;

                                case "2":
                                    curReport = new ShowReport();
                                    System.out.println("Report type: Showing report");
                                    log.info(()-> "Report type to 'show'");
                                    break;

                                default:
                                    System.out.println("Invalid option. Please try again");
                            }
                            break;

                        case "c":
                            curReport.generate(root, curCriteria);
                            break;

                        case "d":
                            System.out.println("Exit program. Good bye...");
                            return;

                        default:
                            System.out.printf("Invalid option '%s\n'", option);
                    }
                }
            }
        }

        /**
         * Reads search criteria from user input.
         * Parses input format: [+/-] [t/r] [search pattern]
         * 
         * @param sc Scanner for reading user input
         * @return List of parsed criteria, or default criteria if none provided
         */

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
                } else {
                inputLines.add(input);
                }
            }

            for (String line : inputLines) {
                try {
                    String[] parts = line.trim().split(" ", 3);
                    if (parts.length != 3) {
                        System.err.println("Invalid format. Format: [+/-] [t/r] [search pattern]");
                        log.warning(() -> "Invalid format. Format: [+/-] [t/r] [search pattern]");
                        continue;
                    }

                    String flag = parts[0].trim();
                    String criteriaType = parts[1].trim();
                    String searchPattern = parts[2].trim();

                    if (!(flag.equals("+")) && !(flag.equals("-"))) {
                        log.warning(() -> "Invalid format: " + flag);
                        System.err.println("Invalid format: Must be start with '+' or '-'");
                        continue;
                    }

                    if (!(criteriaType.equals("t")) && !(criteriaType.equals("r"))) {
                        log.warning(() -> "Invalid format: " + criteriaType);
                        System.err.println("Invalid format: Second part must be start with 't' or 'r'");
                        continue;
                    }

                    if (searchPattern.trim().isEmpty()) {
                        log.warning(() -> "Invalid format: " + searchPattern);
                        System.err.println("Invalid format: Search pattern cannot be empty");
                        continue;
                    }

                    boolean isInclude = flag.equals("+");
                    Criterion criterion;

                    if (criteriaType.equals("t")) {
                        criterion = new TextCriterion(isInclude, searchPattern);
                    } else {
                        criterion = new RegexCriterion(isInclude, searchPattern);
                    }
                    criteriaList.add(criterion);
                    
                } catch (ArrayIndexOutOfBoundsException e) {
                    log.warning(() -> "Error parsing criteria line '" + line + "': " + e.getMessage());
                    System.err.println("Error parsing line " + line + ": " + e.getMessage());

                } catch (IllegalArgumentException e) {
                    log.warning(() -> "Invalid arguments for criterion in line '" + line + "': " + e.getMessage());
                    System.err.println("Invalid arguments for criterion in line '" + line + "': " + e.getMessage());
                }
            }

            if (criteriaList.isEmpty()) {
                System.out.println("No criteria entered. Use the default criteria");
                criteriaList = getDefaultCriterion();
                return criteriaList;
            }

            return criteriaList;
        }

        /**
         * Creates default search criteria that matches all lines.
         * 
         * @return List containing a single regex criterion matching everything
         */        
        public static List<Criterion> getDefaultCriterion () {
            List<Criterion> defaultCriterion = new ArrayList<>();
            Criterion criterion = new RegexCriterion(true, ".*");
            defaultCriterion.add(criterion);
            return defaultCriterion;
        }

        /**
        * Displays the main application menu options.
        */
        public static void printMenu() {
            System.out.println("Select one option in the following options");
            System.out.println("a. Set Criteria");
            System.out.println("b. Set Output Format");
            System.out.println("c. Report");
            System.out.println("d. Quit");
            }

    }
