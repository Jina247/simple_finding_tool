package edu.curtin.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DirectoryTest {
    private Directory testDirectory;
    private FileItem file1;
    private FileItem file2;
    private Directory subDirectory;

    @BeforeEach
    void setUp() {
        testDirectory = new Directory("testDir");
        
        file1 = new FileItem("file1.txt", Arrays.asList("line 1", "line 2 test", "line 3"));
        file2 = new FileItem("file2.txt", Arrays.asList("test line", "another line"));
        subDirectory = new Directory("subDir");
        
        FileItem subFile = new FileItem("subfile.txt", Arrays.asList("nested test", "nested line"));
        subDirectory.addItem(subFile);
        
        testDirectory.addItem(file1);
        testDirectory.addItem(file2);
        testDirectory.addItem(subDirectory);
    }

    @Test
    @DisplayName("Directory should manage items correctly")
    void testDirectoryStructure() {
        assertEquals("testDir", testDirectory.getName());
        assertEquals(3, testDirectory.getItems().size());
        assertTrue(testDirectory.getItems().contains(file1));
        assertTrue(testDirectory.getItems().contains(file2));
        assertTrue(testDirectory.getItems().contains(subDirectory));
    }

    @Test
    @DisplayName("Directory should calculate total lines correctly")
    void testCalcLine() {
        int totalLines = testDirectory.calcLine();
        assertEquals(7, totalLines); // 3 + 2 + 2 lines from all files
    }

    @Test
    @DisplayName("Directory should count matching lines recursively")
    void testCountLineMatching() {
        List<Criterion> criteria = new ArrayList<>();
        criteria.add(new TextCriterion(true, "test"));
        
        int matchingLines = testDirectory.countLineMatching(criteria);
        assertEquals(3, matchingLines); // "line 2 test", "test line", "nested test"
    }

    @Test
    @DisplayName("Directory should handle empty directory")
    void testEmptyDirectory() {
        Directory emptyDir = new Directory("empty");
        assertEquals(0, emptyDir.calcLine());
        assertEquals(0, emptyDir.countLineMatching(new ArrayList<>()));
    }
}