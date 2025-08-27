package edu.curtin.app;

import java.util.ArrayList;
import java.util.List;

public class Directory implements FileSystemItem {
    private final List<FileSystemItem> items = new ArrayList<>();
    private final String name;

    public Directory(String name) {
        this.name = name;
    }

    public void addItem(FileSystemItem item) {
        items.add(item);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void generateReport(String indent, List<Criterion> criteria, Report reportType) {

    }

    @Override
    public int calcLine() {
        int size = 0;
        for (FileSystemItem item: this.items) {
            size += item.calcLine();
        }
        return size;
    }
}
