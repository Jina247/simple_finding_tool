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

    @Override
    public String getName() {
        return this.name;
    }

    public List<String> getContent() {
        return content;
    }


    @Override
    public void generateReport(String indent, List<Criterion> criteria, Report reportType) {

    }

    @Override
    public int calcLine() {
        return content.size();
    }
}
