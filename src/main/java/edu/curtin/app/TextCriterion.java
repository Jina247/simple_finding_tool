package edu.curtin.app;

/**
 * Criterion that performs simple text substring matching.
 * Matches if the pattern is contained anywhere in the line.
 */
public class TextCriterion extends Criterion {
    public TextCriterion(boolean include, String pattern) {
        super(include, pattern);
    }

    /**
     * Checks if the line contains the search pattern as a substring.
     * 
     * @param line The line to search within
     * @return true if pattern is found in the line
     */
    @Override
    protected boolean matched(String line) {
        if (line != null) {
            return line.contains(pattern);
        } else {
            System.out.println("No value found!");
            return false;
        }
    }
}
