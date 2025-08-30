package edu.curtin.app;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

public class Directory implements FileSystemItem {
    private final List<FileSystemItem> items = new ArrayList<>();
    private final String name;

    public Directory(String name) {
        this.name = name;
    }

    public void addItem(FileSystemItem item) {
        items.add(item);
    }

    public List<FileSystemItem> getChildren() {
        return new ArrayList<>(items);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void generateReport(String indent, List<Criterion> criteria, Report reportType) {
        if (reportType instanceof CountReport) {
            int totalLines = countLineMatching(criteria);
            System.out.println(indent + getName() + ": " + totalLines + " lines");
        } else if (reportType instanceof ShowReport) {
            System.out.println(indent + getName());
            showLineMatching(indent, criteria);
        }
        for (FileSystemItem item : this.items) {
            item.generateReport(indent + "    ", criteria, reportType);
        }
    }

    @Override
    public int calcLine() {
        int size = 0;
        for (FileSystemItem item: this.items) {
            size += item.calcLine();
        }
        return size;
    }

    @Override
    public int countLineMatching(List<Criterion> criteria) {
        int count = 0;
        for (FileSystemItem item : this.items) {
            count += item.countLineMatching(criteria);
        }
        return count;
    }

    @Override
    public void showLineMatching(String indent, List<Criterion> criteria) {
    }
}
