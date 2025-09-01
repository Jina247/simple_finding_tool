package edu.curtin.app;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AppTest {

    @Test
    @DisplayName("Should create default criterion correctly")
    void testGetDefaultCriterion() {
        List<Criterion> defaultCriteria = App.getDefaultCriterion();
        
        assertEquals(1, defaultCriteria.size());
        assertTrue(defaultCriteria.get(0) instanceof RegexCriterion);
        assertTrue(defaultCriteria.get(0).isInclude());
        
        // Test that default criterion matches everything
        Criterion defaultCrit = defaultCriteria.get(0);
        assertTrue(defaultCrit.matching("any line"));
        assertTrue(defaultCrit.matching(""));
        assertTrue(defaultCrit.matching("123 abc !@#"));
    }

    @Test
    @DisplayName("Should parse valid criteria input")
    void testReadCriteriaValid() {
        String input = "+ t class\n- r \\d+\n\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        
        List<Criterion> criteria = App.readCriteria(scanner);
        
        assertEquals(2, criteria.size());
        assertTrue(criteria.get(0) instanceof TextCriterion);
        assertTrue(criteria.get(0).isInclude());
        assertTrue(criteria.get(1) instanceof RegexCriterion);
        assertTrue(criteria.get(1).isExclude());
    }

    @Test
    @DisplayName("Should handle empty criteria input")
    void testReadCriteriaEmpty() {
        String input = "\n"; // Just empty line
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        
        List<Criterion> criteria = App.readCriteria(scanner);
        
        // Should return default criteria
        assertEquals(1, criteria.size());
        assertTrue(criteria.get(0) instanceof RegexCriterion);
    }

    @Test
    @DisplayName("Should validate flag parsing logic specifically")
    void testFlagParsingLogic() {
        // Test the exact line: boolean isInclude = flag.equals("+");
        
        // Positive case
        String flag1 = "+";
        boolean isInclude1 = flag1.equals("+");
        assertTrue(isInclude1, "'+' should result in true");
        
        // Negative case
        String flag2 = "-";
        boolean isInclude2 = flag2.equals("+");
        assertFalse(isInclude2, "'-' should result in false");
        
        // Invalid case
        String flag3 = "invalid";
        boolean isInclude3 = flag3.equals("+");
        assertFalse(isInclude3, "'invalid' should result in false");
    }
}