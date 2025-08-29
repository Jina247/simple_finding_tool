package edu.curtin.app;

import java.util.ArrayList;
import java.util.List;

public class FileItem implements FileSystemItem {
    private final String name;
    private final List<String> content = new ArrayList<>();

    public FileItem(String name, List<String> content) {
        this.name = name;
        this.content.addAll(content);
    }

    public List<String> getContent() {
        return content;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void generateReport(String indent, List<Criterion> criteria, Report reportType) {
        if (reportType instanceof CountReport) {
            int matchingLines = countLineMatching(criteria);
            System.out.println(indent + getName()  + ": " + matchingLines + " lines");
        } else if (reportType instanceof ShowReport) {
            showLineMatching(indent, criteria);
        }
    }

    @Override
    public int calcLine() {
        return content.size();
    }

    private boolean lineMatching(String line, List<Criterion> criteria) {
        if (criteria.isEmpty()) {
            return true;
        }

        for (Criterion criterion : criteria) {
            boolean lineMatches = criterion.matching(line);
            if (criterion.isInclude()) {
                if (!lineMatches) {
                    return false;
                }
            } else {
                if (lineMatches) {
                    return false;
                }
            }
        }
        /* Pass all the requirements */
        return true;
    }
    @Override
    public int countLineMatching(List<Criterion> criteria) {
        int count = 0;
        for (String line : content) {
            if (lineMatching(line, criteria)) {
                count++;
            }
        }
        return count;
    }

    @Override
    public void showLineMatching(String indent, List<Criterion> criteria) {
        int count = 0;
        boolean found = false;
        
        System.out.println(getName());
        for (String line : this.content) {
            count++;
            if (lineMatching(line, criteria)) {
                found = true;
                System.out.println(indent + "   " + count + " " + line);
            }
        }
        if (!found) {
            System.out.println("No matching lines found.");
        }
    }
}
