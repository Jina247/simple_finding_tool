package edu.curtin.app;

public abstract class Criterion {
    protected boolean include;
    protected String pattern;

    public Criterion(boolean include, String pattern) {
        this.include = include;
        this.pattern = pattern;
    }

    public final boolean matching(String line) {
        return matched(line);
    }

    protected abstract boolean matched(String line);
    public boolean isInclude() { return include;}
    public boolean isExclude() { return !(include);}
}
