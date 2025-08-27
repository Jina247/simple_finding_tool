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
            int matchingLines = calcLine();
            System.out.println(indent + getName()  + ": " + matchingLines + " lines");
        } else if (reportType instanceof ShowReport) {
            List<String> content = getContent();
            System.out.println(indent + getName());
            for (int i = 0; i < content.size(); i++) {
                System.out.println(indent + " " + (i + 1) + " " + content.get(i));
            }
        }
    }

    @Override
    public int calcLine() {
        return content.size();
    }
}
