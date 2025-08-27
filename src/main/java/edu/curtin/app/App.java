package edu.curtin.app;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Entry point into the application. To change the package, and/or the name of this class, make
 * sure to update the 'mainClass = ...' line in build.gradle.
 */
public class App {
    private static final Logger log = Logger.getLogger(App.class.getName());

    public static void main(String[] args) {
        String dirPath = (args.length < 1)
                ? FileSystems.getDefault().getPath(".").toString()
                : args[0];

        FileSystemIO fileSystemIO = new FileSystemIO();
        fileSystemIO.readFileSystem(dirPath);
        System.out.println("Reading: " + dirPath);

        Report countReport = new CountReport();
        List<Criterion> criteria = new ArrayList<>();
        countReport.generate(fileSystemIO.readFileSystem(dirPath), criteria);

        Report showReport = new ShowReport();
        showReport.generate(fileSystemIO.readFileSystem(dirPath), criteria);
    }

}
