package edu.curtin.app;

import java.util.List;

public class CountReport implements Report {
    @Override
    public void generate(FileSystemItem item, List<Criterion> criteria) {
        item.generateReport("", criteria, this );
    }
}
