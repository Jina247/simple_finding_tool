package edu.curtin.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FileItemTest {
    private FileItem testFile;
    private List<String> testContent;

    @BeforeEach
    void setUp() {
        testContent = Arrays.asList(
            "public class TestClass {",
            "    private int value = 42;",
            "    public void method() {",
            "        System.out.println(\"test\");",
            "    }",
            "}"
        );
        testFile = new FileItem("TestClass.java", testContent);
    }

    @Test
    @DisplayName("FileItem should store content correctly")
    void testFileItemCreation() {
        assertEquals("TestClass.java", testFile.getName());
        assertEquals(6, testFile.calcLine());
        assertEquals(testContent, testFile.getContent());
    }

    @Test
    @DisplayName("FileItem should count matching lines correctly")
    void testCountLineMatching() {
        List<Criterion> criteria = new ArrayList<>();
        criteria.add(new TextCriterion(true, "public"));
        
        int matchingLines = testFile.countLineMatching(criteria);
        assertEquals(2, matchingLines); // "public class" and "public void"

        // Test with exclude criterion
        criteria.clear();
        criteria.add(new TextCriterion(false, "private"));
        matchingLines = testFile.countLineMatching(criteria);
        assertEquals(5, matchingLines); // All lines except the private field
    }

    @Test
    @DisplayName("FileItem should handle empty criteria")
    void testEmptyCriteria() {
        List<Criterion> emptyCriteria = new ArrayList<>();
        int matchingLines = testFile.countLineMatching(emptyCriteria);
        assertEquals(6, matchingLines); // All lines should match
    }

    @Test
    @DisplayName("FileItem should handle multiple criteria")
    void testMultipleCriteria() {
        List<Criterion> criteria = new ArrayList<>();
        criteria.add(new TextCriterion(true, "public")); // must contain "public"
        criteria.add(new TextCriterion(false, "class")); // must not contain "class"
        
        int matchingLines = testFile.countLineMatching(criteria);
        assertEquals(1, matchingLines); // Only "public void method()" line
    }
}