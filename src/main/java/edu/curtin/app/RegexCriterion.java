package edu.curtin.app;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class RegexCriterion extends Criterion {
    public RegexCriterion(boolean include, String pattern) {
        super(include, pattern);
    }

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
