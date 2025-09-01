package edu.curtin.app;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a directory in the file system.
 * Contains other FileSystemItems (files and subdirectories).
 */
public class Directory implements FileSystemItem {
    private final List<FileSystemItem> items = new ArrayList<>();
    private final String name;

    public Directory(String name) {
        this.name = name;
    }

    public void addItem(FileSystemItem item) {
        items.add(item);
    }
    public List<FileSystemItem> getItems() {
        return new ArrayList<>(items);
    }
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Generates a report for this directory and all its contents.
     */
    @Override
    public void generateReport(String indent, List<Criterion> criteria, Report reportType) {
        reportType.reportDirectory(this, indent, criteria);

        // Generate reports for all contained items
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

    /**
     * Counts lines matching the given criteria across all contained files.
     */
    @Override
    public int countLineMatching(List<Criterion> criteria) {
        int count = 0;
        for (FileSystemItem item : this.items) {
            count += item.countLineMatching(criteria);
        }
        return count;
    }

}
