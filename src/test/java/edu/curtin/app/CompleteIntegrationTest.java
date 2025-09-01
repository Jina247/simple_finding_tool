package edu.curtin.app;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class CompleteIntegrationTest {
    
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;

    @BeforeEach
    void setUpStreams() {
        outputStream = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    @DisplayName("End-to-end test with realistic Java project")
    void testRealisticJavaProject(@TempDir Path tempDir) throws IOException {
        System.out.println("=== Testing Complete Workflow ===");
        
        // Create realistic test structure
        createTestFileStructure(tempDir);
        
        // Load file system
        FileSystemIO io = new FileSystemIO();
        FileSystemItem root = io.readFileSystem(tempDir.toString());
        assertNotNull(root, "Should successfully load directory");
        
        System.out.println("Total lines in project: " + root.calcLine());
        
        // Test 1: Find public methods (using simpler regex)
        testPublicMethods(root);
        
        // Test 2: Find specific text patterns
        testTextPatterns(root);
        
        System.out.println("✓ Complete workflow test passed");
    }

    private void testPublicMethods(FileSystemItem root) {
        System.out.println("\n--- Testing: Find Public Methods ---");
        
        List<Criterion> criteria = new ArrayList<>();
        criteria.add(new TextCriterion(true, "public"));
        criteria.add(new TextCriterion(true, "("));  // Methods have parentheses
        
        ShowReport report = new ShowReport();
        outputStream.reset(); // Clear previous output
        report.generate(root, criteria);
        
        String output = outputStream.toString();
        System.out.println("Public methods found:");
        System.out.println(output);
        
        // Check that we found some public methods
        assertTrue(output.contains("public") && output.contains("("), 
            "Should find lines with 'public' and '('");
    }

    private void testTextPatterns(FileSystemItem root) {
        System.out.println("\n--- Testing: Find Class Declarations ---");
        
        List<Criterion> criteria = new ArrayList<>();
        criteria.add(new TextCriterion(true, "class"));
        
        CountReport countReport = new CountReport();
        outputStream.reset();
        countReport.generate(root, criteria);
        
        String output = outputStream.toString();
        System.out.println("Class count report:");
        System.out.println(output);
        
        assertTrue(output.contains("lines"), "Should show line counts");
    }

    private void createTestFileStructure(Path tempDir) throws IOException {
        // Create src directory
        Path srcDir = tempDir.resolve("src");
        Files.createDirectory(srcDir);
        
        // Main.java - simple and clear content
        Path mainFile = srcDir.resolve("Main.java");
        Files.write(mainFile, Arrays.asList(
            "public class Main {",
            "    public static void main(String[] args) {",
            "        System.out.println(\"Hello\");",
            "    }",
            "}"
        ));
        
        // Utils.java
        Path utilsFile = srcDir.resolve("Utils.java");
        Files.write(utilsFile, Arrays.asList(
            "public class Utils {",
            "    private int value;",
            "    public void helper() {",
            "        // implementation",
            "    }",
            "}"
        ));
        
        // Test file
        Path testFile = srcDir.resolve("Test.java");
        Files.write(testFile, Arrays.asList(
            "import org.junit.Test;",
            "public class Test {",
            "    private void setup() {}",
            "}"
        ));
    }
}