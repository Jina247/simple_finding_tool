package edu.curtin.app;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class FileSystemIO {
    private static final Logger log = Logger.getLogger(FileSystemIO.class.getName());

    public FileSystemItem readFileSystem(String rootPath) {
        File file = new File(rootPath);

        if (!file.exists()) {
            log.warning("Directory path not found");
            return null;
        }

        if (!file.isDirectory()) {
            log.warning("The path isn't a directory");
            return null;
        }

        Directory root = new Directory(file.getName());
        Map<String, Directory> directoryMap = new HashMap<>();
        directoryMap.put(rootPath, root);
        buildStructure(file, root, directoryMap);
        return root;
    }

    public void buildStructure(File file, Directory directory, Map<String, Directory> directoryMap) {
        File[] children = file.listFiles();

        if (children != null) {
            for (File child : children) {
                if (child.isDirectory()) {
                    Directory subDirectory = new Directory(child.getName());
                    directoryMap.put(child.getAbsolutePath(), subDirectory);
                    directory.addItem(subDirectory);
                    buildStructure(child, subDirectory, directoryMap);
                } else {
                    FileItem fileItem = buildFile(child);
                    directory.addItem(fileItem);
                }
            }
        }
    }

    public FileItem buildFile(File file) {
        String name = file.getName();
        List<String> content = new ArrayList<>();

        try (var reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                content.add(line);
            }
        } catch (IOException e) {
            log.info("Failed to to read file" + name);
        }
        return new FileItem(name, content);
    }
}
