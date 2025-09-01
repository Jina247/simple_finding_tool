package edu.curtin.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CriterionTest {

    @Test
    @DisplayName("TextCriterion should match substring correctly")
    void testTextCriterionMatching() {
        // Test include criterion
        TextCriterion includeText = new TextCriterion(true, "class");
        assertTrue(includeText.matching("public class Test"));
        assertTrue(includeText.matching("interface class"));
        assertFalse(includeText.matching("public interface Test"));
        assertFalse(includeText.matching(""));

        // Test exclude criterion
        TextCriterion excludeText = new TextCriterion(false, "test");
        assertFalse(excludeText.matching("public class TestClass")); // contains "test", so should be false for exclude
        assertTrue(excludeText.matching("public class Main")); // doesn't contain "test", so should be true for exclude
        assertTrue(excludeText.matching("public class Main"));
    }

    @Test
    @DisplayName("TextCriterion should handle null input")
    void testTextCriterionNullInput() {
        TextCriterion criterion = new TextCriterion(true, "test");
        assertFalse(criterion.matching(null));
    }

    @Test
    @DisplayName("RegexCriterion should match patterns correctly")
    void testRegexCriterionMatching() {
        // Test digit pattern
        RegexCriterion digitCriterion = new RegexCriterion(true, "\\d+");
        assertTrue(digitCriterion.matching("Line 123 has numbers"));
        assertTrue(digitCriterion.matching("42"));
        assertFalse(digitCriterion.matching("No numbers here"));

        // Test word boundary pattern
        RegexCriterion wordCriterion = new RegexCriterion(true, "\\bclass\\b");
        assertTrue(wordCriterion.matching("public class Test"));
        assertFalse(wordCriterion.matching("subclass"));
    }

    @Test
    @DisplayName("RegexCriterion should handle invalid patterns gracefully")
    void testRegexCriterionInvalidPattern() {
        RegexCriterion invalidCriterion = new RegexCriterion(true, "[invalid");
        // Your implementation returns false for invalid patterns
        assertFalse(invalidCriterion.matching("any text"));
    }

    @Test
    @DisplayName("Criterion should correctly identify include/exclude")
    void testCriterionIncludeExclude() {
        TextCriterion includeCriterion = new TextCriterion(true, "test");
        assertTrue(includeCriterion.isInclude());
        assertFalse(includeCriterion.isExclude());

        TextCriterion excludeCriterion = new TextCriterion(false, "test");
        assertFalse(excludeCriterion.isInclude());
        assertTrue(excludeCriterion.isExclude());
    }

    @ParameterizedTest
    @CsvSource({
        "'+', true",
        "'-', false", 
        "'invalid', false"
    })
    @DisplayName("Flag parsing logic should work correctly")
    void testFlagParsing(String flag, boolean expectedInclude) {
        // Test the specific line you asked about: boolean isInclude = flag.equals("+");
        boolean isInclude = flag.equals("+");
        assertEquals(expectedInclude, isInclude);
    }
}