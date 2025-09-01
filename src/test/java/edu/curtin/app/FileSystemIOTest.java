package edu.curtin.app;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class FileSystemIOTest {
    private FileSystemIO fileSystemIO;

    @BeforeEach
    void setUp() {
        fileSystemIO = new FileSystemIO();
    }

    @Test
    @DisplayName("Should handle non-existent directory")
    void testNonExistentDirectory() {
        FileSystemItem result = fileSystemIO.readFileSystem("/non/existent/path");
        assertNull(result);
    }

    @Test
    @DisplayName("Should handle file instead of directory")
    void testFileInsteadOfDirectory(@TempDir Path tempDir) throws IOException {
        Path tempFile = tempDir.resolve("testfile.txt");
        Files.write(tempFile, "test content".getBytes());
        
        FileSystemItem result = fileSystemIO.readFileSystem(tempFile.toString());
        assertNull(result);
    }

    @Test
    @DisplayName("Should read directory structure correctly")
    void testReadDirectoryStructure(@TempDir Path tempDir) throws IOException {
        // Create test directory structure
        Path subDir = tempDir.resolve("subdir");
        Files.createDirectory(subDir);
        
        Path file1 = tempDir.resolve("file1.txt");
        Path file2 = subDir.resolve("file2.txt");
        
        Files.write(file1, Arrays.asList("line 1", "line 2"));
        Files.write(file2, Arrays.asList("nested line"));
        
        FileSystemItem root = fileSystemIO.readFileSystem(tempDir.toString());
        
        assertNotNull(root);
        assertTrue(root instanceof Directory);
        assertEquals(3, root.calcLine()); // 2 + 1 lines
    }
}