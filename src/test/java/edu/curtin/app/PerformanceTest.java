package edu.curtin.app;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class PerformanceTest {

    @Test
    @DisplayName("Should handle large directory structures efficiently")
    void testLargeDirectoryPerformance(@TempDir Path tempDir) throws IOException {
        System.out.println("=== Performance Test: Large Directory Structure ===");
        
        // Create many files with realistic Java content
        createLargeJavaProject(tempDir);
        
        FileSystemIO io = new FileSystemIO();
        
        // Measure load time
        long loadStart = System.currentTimeMillis();
        FileSystemItem root = io.readFileSystem(tempDir.toString());
        long loadTime = System.currentTimeMillis() - loadStart;
        
        assertNotNull(root, "Should load large structure");
        System.out.println("Load time: " + loadTime + "ms");
        System.out.println("Total lines: " + root.calcLine());
        
        // Measure search time with complex criteria
        List<Criterion> complexCriteria = new ArrayList<>();
        complexCriteria.add(new RegexCriterion(true, "\\b(public|private|protected)\\b"));
        complexCriteria.add(new TextCriterion(false, "//"));
        complexCriteria.add(new RegexCriterion(true, "\\w+\\s*\\("));
        
        long searchStart = System.currentTimeMillis();
        int matches = root.countLineMatching(complexCriteria);
        long searchTime = System.currentTimeMillis() - searchStart;
        
        System.out.println("Search time: " + searchTime + "ms");
        System.out.println("Matching lines: " + matches);
        
        // Performance assertions (adjust thresholds as needed)
        assertTrue(loadTime < 5000, "Load should complete within 5 seconds");
        assertTrue(searchTime < 2000, "Search should complete within 2 seconds");
        assertTrue(matches > 0, "Should find some matches");
        
        System.out.println("✓ Performance test passed");
    }

    private void createLargeJavaProject(Path projectRoot) throws IOException {
        System.out.println("Creating large Java project for performance testing...");
        
        // Create multiple packages
        for (int pkg = 0; pkg < 5; pkg++) {
            Path packageDir = projectRoot.resolve("src/main/java/com/example/pkg" + pkg);
            Files.createDirectories(packageDir);
            
            // Create multiple classes per package
            for (int cls = 0; cls < 15; cls++) {
                Path classFile = packageDir.resolve("Class" + cls + ".java");
                List<String> content = generateClassContent(pkg, cls);
                Files.write(classFile, content);
            }
        }
        
        // Create test directory
        Path testDir = projectRoot.resolve("src/test/java/com/example");
        Files.createDirectories(testDir);
        
        for (int test = 0; test < 10; test++) {
            Path testFile = testDir.resolve("Test" + test + ".java");
            Files.write(testFile, generateTestContent(test));
        }
        
        System.out.println("✓ Large project structure created");
    }

    private List<String> generateClassContent(int pkg, int cls) {
        return Arrays.asList(
            "package com.example.pkg" + pkg + ";",
            "",
            "import java.util.logging.Logger;",
            "import java.util.*;",
            "",
            "/**",
            " * Class" + cls + " in package " + pkg,
            " */",
            "public class Class" + cls + " {",
            "    private static final Logger log = Logger.getLogger(Class" + cls + ".class.getName());",
            "    private int value" + cls + ";",
            "",
            "    public Class" + cls + "() {",
            "        log.info(\"Creating Class" + cls + "\");", 
            "        this.value" + cls + " = " + (cls * 10) + ";",
            "    }",
            "",
            "    public void process() {",
            "        log.debug(\"Processing in Class" + cls + "\");",
            "        // Implementation details",
            "        if (value" + cls + " > 50) {",
            "            log.warning(\"High value detected: \" + value" + cls + ");",
            "        }",
            "    }",
            "",
            "    private void internalMethod() {",
            "        // Private implementation",
            "    }",
            "",
            "    public int getValue() { return value" + cls + "; }",
            "    public void setValue(int value) { this.value" + cls + " = value; }",
            "}"
        );
    }

    private List<String> generateTestContent(int testNum) {
        return Arrays.asList(
            "package com.example;",
            "",
            "import org.junit.jupiter.api.Test;",
            "import org.junit.jupiter.api.BeforeEach;",
            "import static org.junit.jupiter.api.Assertions.*;",
            "",
            "class Test" + testNum + " {",
            "",
            "    @BeforeEach",
            "    void setUp() {",
            "        // Test setup for " + testNum,
            "    }",
            "",
            "    @Test",
            "    void testMethod" + testNum + "() {",
            "        // Test implementation " + testNum,
            "        assertTrue(true, \"Test " + testNum + " should pass\");",
            "    }",
            "",
            "    @Test",
            "    void testEdgeCase" + testNum + "() {",
            "        // Edge case testing",
            "        assertNotNull(new Object());",
            "    }",
            "}"
        );
    }
}