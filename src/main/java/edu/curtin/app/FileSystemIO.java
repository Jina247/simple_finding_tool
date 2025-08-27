package edu.curtin.app;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class FileSystemIO {
    private static final Logger log = Logger.getLogger(FileSystemIO.class.getName());

    public FileSystemItem readFileSystem(String dirPath) {
        File dir = new File(dirPath);
        if (!dir.exists()) {
            System.out.println("Directory path not found");
            return null;
        }

        if (!dir.isDirectory()) {
            System.out.println("Path isn't a directory");
            return null;
        }

        File[] files = dir.listFiles();

        if (files == null || files.length == 0) {
            System.out.println("No files found in: " + dirPath);
            return null;
        }
        return null;
    }

    public FileItem buildFile(File file) {
        String name = file.getName();
        List<String> content = new ArrayList<>();
        try (var reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();

            while (line != null) {
                content.add(line);
            }
        } catch (IOException e) {
            log.info("Failed to to read file" + name);
        }
        return new FileItem(name, content);
    }

    public Directory buildDirStructure(Directory directory) {

        return null;
    }
}
