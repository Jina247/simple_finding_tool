package edu.curtin.app;

import java.util.List;

/**
 * Report that shows the count of matching lines for each file and directory.
 */
public class CountReport implements Report {
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
        int count = file.countLineMatching(criteria);
        System.out.println(indent + file.getName() + " :" + count + " lines");
    }

    @Override
    public void reportDirectory(Directory dir, String indent, List<Criterion> criteria) {
        int totalLines = dir.countLineMatching(criteria);
        System.out.println(indent + dir.getName() + ": " + totalLines + " lines");
    }
}
