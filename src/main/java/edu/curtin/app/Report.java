package edu.curtin.app;

import java.util.List;

public interface Report {
    void generate(FileSystemItem item, List<Criterion> criteria);
}
