package edu.curtin.app;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReportTest {
    private FileItem testFile;
    private List<Criterion> testCriteria;
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;

    @BeforeEach
    void setUp() {
        testFile = new FileItem("test.txt", Arrays.asList(
            "public class Test",
            "private int value",
            "public void method()"
        ));
        
        testCriteria = new ArrayList<>();
        testCriteria.add(new TextCriterion(true, "public"));
        
        outputStream = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    @DisplayName("CountReport should generate correct output")
    void testCountReport() {
        CountReport report = new CountReport();
        report.generate(testFile, testCriteria);
        
        String output = outputStream.toString().trim();
        // Your implementation outputs: "test.txt: 2 lines"
        assertTrue(output.contains("test.txt: 2 lines"), 
            "Expected 'test.txt: 2 lines' but got: '" + output + "'");
    }

    @Test
    @DisplayName("ShowReport should display matching lines with line numbers")
    void testShowReport() {
        ShowReport report = new ShowReport();
        report.generate(testFile, testCriteria);
        
        String output = outputStream.toString();
        assertTrue(output.contains("test.txt"), "Should contain filename");
        assertTrue(output.contains("1 public class Test"), "Should show line 1 with content");
        assertTrue(output.contains("3 public void method()"), "Should show line 3 with content");
        assertFalse(output.contains("private int value"), "Should not show non-matching line 2");
    }

    @Test
    @DisplayName("Reports should handle null FileSystemItem")
    void testNullFileSystemItem() {
        CountReport countReport = new CountReport();
        ShowReport showReport = new ShowReport();
        
        assertThrows(IllegalArgumentException.class, 
            () -> countReport.generate(null, testCriteria));
        assertThrows(IllegalArgumentException.class, 
            () -> showReport.generate(null, testCriteria));
    }
}