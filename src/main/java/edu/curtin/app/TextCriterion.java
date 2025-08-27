package edu.curtin.app;

public class TextCriterion extends Criterion {
    public TextCriterion(boolean include, String pattern) {
        super(include, pattern);
    }

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
