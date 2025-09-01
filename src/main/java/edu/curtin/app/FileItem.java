package edu.curtin.app;

import java.util.ArrayList;
import java.util.List;

public class FileItem implements FileSystemItem {
    private final String name;
    private final List<String> content = new ArrayList<>();

    /**
     * Represents a file in the file system.
     * Contains the file's content as a list of strings (lines).
     */
    public FileItem(String name, List<String> content) {
        this.name = name;
        this.content.addAll(content);
    }

    @Override
    public String getName() { return this.name; }
    
    /**
     * Generates a report for this file based on the criteria and report type.
     */
    @Override
    public void generateReport(String indent, List<Criterion> criteria, Report reportType) {
        reportType.reportFile(this, indent, criteria);
    }

    @Override
    public int calcLine() {
        return content.size();
    }

    /**
     * Counts the number of lines in this file that match all criteria.
     */
    @Override
    public int countLineMatching(List<Criterion> criteria) {
        int count = 0;
        for (String line : this.content) {
            if (lineMatching(line, criteria)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Displays all lines that match the criteria with line numbers.
     * @param indent Indicates the amount of spacing
     * @param criteria A list of criteria to apply
     */
    public void showLineMatching(String indent, List<Criterion> criteria) {
        int lineNumber = 0;
        boolean isMatching = false;

        System.out.println(indent + getName());
        for (String line : this.content) {
            lineNumber++;
            if (lineMatching(line, criteria)) {
                System.out.println(indent + "   " + lineNumber + " " + line);
                isMatching = true;
            }
        }
        if (!isMatching) {
            System.out.println(indent + "   " + "No matching lines found.");
        }
    }

    /**
     * Checks if a line matches all the given criteria.
     * All include criteria must match and no exclude criteria can match.
     * 
     * @param line The line to test
     * @param criteria List of criteria to apply
     * @return true if line matches all criteria
     */
    private boolean lineMatching(String line, List<Criterion> criteria) {
        if (criteria.isEmpty()) {
            return true;
        }

        for (Criterion criterion : criteria) {
            boolean lineMatches = criterion.matching(line);
            if (criterion.isInclude() && !lineMatches) {
                return false;

            } else if (criterion.isExclude() && lineMatches) {
                return false;
            }
        }
        /* Passes all the requirements */
        return true;
    }
}