package edu.curtin.app;

import java.util.List;

public interface Report {
    void generate(FileSystemItem item, List<Criterion> criteria);
    void reportFile(FileItem file, String indent, List<Criterion> criteria);
    void reportDirectory(Directory dir, String indent, List<Criterion> criteria);
}
