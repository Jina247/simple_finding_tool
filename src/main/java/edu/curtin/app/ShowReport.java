package edu.curtin.app;

import java.util.List;

public class ShowReport implements Report {
    @Override
    public void generate(FileSystemItem item, List<Criterion> criteria) {
        if (item != null) {
            item.generateReport("", criteria, this);
        } else {
            throw new IllegalArgumentException("File is null");
        }
    }

    @Override
    public void reportFile(FileItem file, String indent, List<Criterion> criteria) {
        file.showLineMatching(indent, criteria);
    }

    @Override
    public void reportDirectory(Directory dir, String indent, List<Criterion> criteria) {
        System.out.println(indent + dir.getName());
    }
}
