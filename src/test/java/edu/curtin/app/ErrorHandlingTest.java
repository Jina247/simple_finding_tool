package edu.curtin.app;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ErrorHandlingTest {
    
    private ByteArrayOutputStream errorStream;
    private PrintStream originalErr;

    @BeforeEach
    void setUpStreams() {
        errorStream = new ByteArrayOutputStream();
        originalErr = System.err;
        System.setErr(new PrintStream(errorStream));
    }

    @AfterEach
    void restoreStreams() {
        System.setErr(originalErr);
    }

    @Test
    @DisplayName("Should handle invalid criteria format gracefully")
    void testInvalidCriteriaFormat() {
        String invalidInput = 
            "invalid format\n" +           // Too few parts
            "+ invalid pattern\n" +        // Invalid criteria type
            "* t pattern\n" +              // Invalid flag
            "+ t\n" +                      // Missing pattern
            "\n";                          // End input
            
        Scanner scanner = new Scanner(new ByteArrayInputStream(invalidInput.getBytes()));
        List<Criterion> criteria = App.readCriteria(scanner);
        
        // Should fall back to default criteria
        assertEquals(1, criteria.size(), "Should use default criteria for invalid input");
        
        String errors = errorStream.toString();
        
        // Check for the actual error messages your implementation produces
        assertTrue(errors.contains("Invalid format"), "Should show format errors");
        assertTrue(errors.contains("Second part must be"), "Should show criteria type errors");
        assertTrue(errors.contains("Must be start with"), "Should show flag errors");
    }

    @Test
    @DisplayName("Should handle malformed regex patterns")
    void testMalformedRegexPatterns() {
        RegexCriterion badRegex1 = new RegexCriterion(true, "[unclosed");
        RegexCriterion badRegex2 = new RegexCriterion(true, "*invalid");
        
        assertFalse(badRegex1.matching("test line"), "Unclosed bracket should return false");
        assertFalse(badRegex2.matching("test line"), "Invalid quantifier should return false");
        
        String errors = errorStream.toString();
        // Your implementation shows: "Pattern: [pattern] not found!"
        assertTrue(errors.contains("not found!"), "Should show pattern error messages");
    }
}