package edu.curtin.app;

import java.util.List;

/**
 * Represents any item in the File System Structure, including both directories and files.
 */
public interface FileSystemItem {
    
    /**
     * Returns name this FileSystemItem.
     * @return name of this FileSystemItem.
     */
    String getName();

    /**
     * Generates report whose content of the item contains a given list of searching patterns.
     * @param indent Indicates the amount of spacing to be output on the start of each lines.
     * @param criteria Contains a list of seaching patterns.
     * @param reportType Indicates type of report to generate.
     */
    void generateReport(String indent, List<Criterion> criteria, Report reportType);

    /**
     * Calculates total line all across the items in this FileSystemItem;
     */
    int calcLine();

    /**
     * Calculates lines matching to the given criteria all across the items.
     * @param criteria Contains a list of searching patterns.
     */
    int countLineMatching(List<Criterion> criteria);
}
