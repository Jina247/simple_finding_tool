package edu.curtin.app;

import java.util.List;

public interface FileSystemItem {
    String getName();
    void generateReport(String indent, List<Criterion> criteria, Report reportType);
    int calcLine();

}
