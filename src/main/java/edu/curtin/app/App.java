package edu.curtin.app;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.logging.Logger;

/**
 * Entry point into the application. To change the package, and/or the name of this class, make
 * sure to update the 'mainClass = ...' line in build.gradle.
 */
public class App {
    private static final Logger log = Logger.getLogger(App.class.getName());

    public static void main(String[] args) {
        if (args.length < 1) {
            Path path = FileSystems.getDefault().getPath(".").toAbsolutePath();
            System.out.println("Current directory...");
            search(path.toString());
        } else if (args.length == 1) {
            System.out.println("Reading directory...");
            String path = args[0];
            search(path);
        }
    }

    public static void search(String directoryPath) {
        File dir = new File(directoryPath);
        if (!dir.exists()) {
            System.out.println("Directory path not found");
            return;
        }

        if (!dir.isDirectory()) {
            System.out.println("Path isn't a directory");
            return;
        }

        File[] files = dir.listFiles();

        if (files == null || files.length == 0) {
            System.out.println("No files found in: " + directoryPath);
            return;
        }

        for (File file : files) {
            System.out.println(file);

            if (file.isDirectory()) {
                search(file.getAbsolutePath());
                log.info("This is a recursive call to read files.");
            }
        }
    }
}
