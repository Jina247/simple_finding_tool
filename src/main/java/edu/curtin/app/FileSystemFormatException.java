package edu.curtin.app;

public class FileSystemFormatException extends Exception {
    public FileSystemFormatException(String msg) {
        super(msg);
    }
    public FileSystemFormatException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}
