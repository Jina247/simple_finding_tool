package edu.curtin.app;

import java.nio.file.FileSystems;
import java.nio.file.Path;

/**
 * Entry point into the application. To change the package, and/or the name of this class, make
 * sure to update the 'mainClass = ...' line in build.gradle.
 */
public class App {
    public static void main(String[] args) {
        if (args.length < 1) {
            Path path = FileSystems.getDefault().getPath(".").toAbsolutePath();
            System.out.println("Current directory...");

        } else if (args.length == 1) {
            System.out.println("Reading directory...");
            String path = args[0];
        }
    }
}
