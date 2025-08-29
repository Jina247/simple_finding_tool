package edu.curtin.app;

import java.util.List;

public class CountReport implements Report {
    @Override
    public void generate(FileSystemItem item, List<Criterion> criteria) {
        if (item != null) {
            item.generateReport("", criteria, this);
        } else {
            throw new IllegalArgumentException("File is null");
        }
    }
}
