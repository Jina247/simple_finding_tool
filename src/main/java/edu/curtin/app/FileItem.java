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
        reportType.reportFile(this, indent, criteria);
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
        for (String line : this.content) {
            if (lineMatching(line, criteria)) {
                count++;
            }
        }
        return count;
    }

    @Override
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
            System.out.println(indent + "No matching lines found.");
        }
    }
}
