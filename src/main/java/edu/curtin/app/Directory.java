package edu.curtin.app;

import java.util.ArrayList;
import java.util.List;

public class Directory implements FileSystemItem {
    private final List<FileSystemItem> items;
    private final String name;

    public Directory(List<FileSystemItem> items, String name) {
        this.items = new ArrayList<>();
        this.name = name;
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
        return 0;
    }
}
