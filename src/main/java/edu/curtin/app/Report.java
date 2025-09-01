package edu.curtin.app;

import java.util.List;

/**
 * Interface for generating report.
 */
public interface Report {
    /**
     * Generates either Count report or Show report
     * @param item Root file system file to generate report for
     * @param criteria List of applied criteria
     */
    void generate(FileSystemItem item, List<Criterion> criteria);

    /**
     * Generates report for FileItem.
     * @param file File to generate report for
     * @param indent The amount of spacing to display
     * @param criteria List of applied criteria
     */
    void reportFile(FileItem file, String indent, List<Criterion> criteria);

    /**
     * Generates report for Directory.
     * @param dir Directory to generate report for
     * @param indent The amount of spacing to display
     * @param criteria List of applied criteria
     */
    void reportDirectory(Directory dir, String indent, List<Criterion> criteria);
}
