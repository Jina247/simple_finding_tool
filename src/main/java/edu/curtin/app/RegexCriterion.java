package edu.curtin.app;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Criterion that performs regular expression pattern matching.
 * Uses Java's regex module to find pattern matches in lines.
 */
public class RegexCriterion extends Criterion {
    public RegexCriterion(boolean include, String pattern) {
        super(include, pattern);
    }

    /**
     * Checks if the line matches the regular expression pattern.
     * 
     * @param line The line to test against the regex
     * @return true if pattern matches anywhere in the line
     */
    @Override
    protected boolean matched(String line) {
        try {
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(line);
            return m.find();
        } catch (PatternSyntaxException e) {
            System.err.println("Pattern: " + pattern + " not found!");
            return false;
        }
    }
}
