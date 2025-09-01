package edu.curtin.app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Handles reading and building file system structure from disk.
 * Creates a tree of FileSystemItem objects representing directories and files.
 */
public class FileSystemIO {
    private static final Logger log = Logger.getLogger(FileSystemIO.class.getName());
    private Map<String, Directory> directoryMap = new HashMap<>();

    /**
     * Reads a directory structure and creates a FileSystemItem tree.
     * 
     * @param rootPath Path to the root directory to read
     * @return Root FileSystemItem or null if path is invalid
     */
    public FileSystemItem readFileSystem(String rootPath) {
        File file = new File(rootPath);

        if (!file.exists()) {
            log.severe(() -> "Directory is not found: " + rootPath);
            System.err.println("Directory is not found");
            return null;
        }

        if (!file.isDirectory()) {
            log.severe(() -> "Path is not a directory: " + rootPath);
            System.err.println("This is not found");
            return null;
        }

        Directory root = new Directory(file.getName());
        directoryMap.put(rootPath, root);
        buildStructure(file, root);
        log.info(() -> "Buiding directory structure successfully.");
        return root;
    }

    /**
     * Recursively builds the directory structure.
     * 
     * @param file Current file/directory being processed
     * @param directory Parent directory to add items to
     * @param directoryMap Map tracking created directories
     */
    public void buildStructure(File file, Directory directory) {
        File[] children = file.listFiles();

        if (children != null) {
            for (File child : children) {
                if (child.isDirectory()) {
                    Directory subDirectory = new Directory(child.getName());
                    directoryMap.put(child.getAbsolutePath(), subDirectory);
                    directory.addItem(subDirectory);
                    buildStructure(child, subDirectory);
                } else {
                    FileItem fileItem = buildFile(child);
                    directory.addItem(fileItem);
                }
            }
        } else {
            log.warning(() -> "Could not list files in directory: " + file.getName());
        }
    }

    /**
     * Builds a FileItem by reading the content of a file.
     * 
     * @param file File to read
     * @return FileItem containing the file's content
     */
    public FileItem buildFile(File file) {
        String name = file.getName();
        List<String> content = new ArrayList<>();

        try (var reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                content.add(line);
            }
        } catch (IOException e) {
            log.severe(() -> "Failed to read file " + name + ": " + e.getMessage());
            System.err.println("Failed to read file" + name);
        }
        return new FileItem(name, content);
    }
}
