package edu.curtin.app;

/**
 * Abstract base class for search criteria.
 * Defines whether lines should be included or excluded based on searching patterns.
 */
public abstract class Criterion {
    protected boolean include;
    protected String pattern;

    public Criterion(boolean include, String pattern) {
        this.include = include;
        this.pattern = pattern;
    }

    /**
     * Public method to check if a line matches this criterion.
     * 
     * @param line The line to check
     * @return true if the line matches the pattern
     */
    public final boolean matching(String line) {
        return matched(line);
    }

    /**
     * Abstract method implemented by subclasses to define matching logic.
     * 
     * @param line The line to test against the pattern
     * @return true if the line matches the pattern
     */
    protected abstract boolean matched(String line);
    
    public boolean isInclude() { return include;}
    public boolean isExclude() { return !(include);}
}
