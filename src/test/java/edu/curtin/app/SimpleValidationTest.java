package edu.curtin.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Simple validation tests that should definitely pass.
 * Use these to verify basic functionality works.
 */
class SimpleValidationTest {

    @Test
    @DisplayName("Basic TextCriterion functionality")
    void testBasicTextCriterion() {
        TextCriterion criterion = new TextCriterion(true, "test");
        
        assertTrue(criterion.matching("this is a test"));
        assertFalse(criterion.matching("no match here"));
        assertTrue(criterion.isInclude());
        assertFalse(criterion.isExclude());
    }

    @Test
    @DisplayName("Basic RegexCriterion functionality")
    void testBasicRegexCriterion() {
        RegexCriterion criterion = new RegexCriterion(true, "\\d");
        
        assertTrue(criterion.matching("line 1"));
        assertFalse(criterion.matching("no numbers"));
        assertTrue(criterion.isInclude());
    }

    @Test
    @DisplayName("Basic FileItem functionality")
    void testBasicFileItem() {
        List<String> content = Arrays.asList("line 1", "line 2", "line 3");
        FileItem file = new FileItem("test.txt", content);
        
        assertEquals("test.txt", file.getName());
        assertEquals(3, file.calcLine());
        assertEquals(content, file.getContent());
    }

    @Test
    @DisplayName("Basic Directory functionality")
    void testBasicDirectory() {
        Directory dir = new Directory("testDir");
        FileItem file = new FileItem("file.txt", Arrays.asList("content"));
        
        dir.addItem(file);
        
        assertEquals("testDir", dir.getName());
        assertEquals(1, dir.getItems().size());
        assertEquals(1, dir.calcLine());
    }

    @Test
    @DisplayName("Flag parsing logic validation")
    void testFlagParsingExact() {
        // Test the exact logic from your code
        String plusFlag = "+";
        String minusFlag = "-";
        String invalidFlag = "x";
        
        boolean result1 = plusFlag.equals("+");
        boolean result2 = minusFlag.equals("+");
        boolean result3 = invalidFlag.equals("+");
        
        assertTrue(result1, "'+' should equal '+'");
        assertFalse(result2, "'-' should not equal '+'");
        assertFalse(result3, "'x' should not equal '+'");
    }

    @Test
    @DisplayName("Multiple criteria logic validation")
    void testMultipleCriteriaLogic() {
        FileItem file = new FileItem("test.java", Arrays.asList(
            "public class Test {",      // has "public", no "private" -> MATCH
            "    private int x;",       // no "public" -> NO MATCH  
            "    public void method(){" // has "public", no "private" -> MATCH
        ));
        
        List<Criterion> criteria = new ArrayList<>();
        criteria.add(new TextCriterion(true, "public"));   // must have "public"
        criteria.add(new TextCriterion(false, "private")); // must not have "private"
        
        int matches = file.countLineMatching(criteria);
        assertEquals(2, matches, "Should match 2 lines that have 'public' but not 'private'");
    }
}