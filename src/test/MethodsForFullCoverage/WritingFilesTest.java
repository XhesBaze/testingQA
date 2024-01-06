package MethodsForFullCoverage;

import static org.junit.jupiter.api.Assertions.*;

import application.tryingpr.Models.Administrator;
import application.tryingpr.Models.Book;
import application.tryingpr.Models.Manager;
import application.tryingpr.Models.Person;
import application.tryingpr.helperClasses.writingToFiles;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;

public class WritingFilesTest {

    @Test
    void testFileNotFound() {
        try {
            writingToFiles.readCredentials("validUsername", "validPassword", "nonExistentFile.txt");
            fail("Expected RuntimeException but no exception was thrown");
        } catch (RuntimeException ex) {
            assertEquals("File not found", ex.getMessage());
        }
    }

    @Test
    void testInvalidFilePath() {
        try {
            writingToFiles.readCredentials("validUsername", "validPassword", "invalid/file/path/credentials.txt");
            fail("Expected RuntimeException but no exception was thrown");
        } catch (RuntimeException ex) {
            assertEquals("File not found", ex.getMessage());
        }
    }

    @Test
    void testNullFilePath() {
        try {
            // Assuming the readCredentials method is in YourClass
            writingToFiles.readCredentials("validUsername", "validPassword", null);

            // If the method doesn't throw an exception, fail the test
            fail("Expected RuntimeException but no exception was thrown");

        } catch (RuntimeException ex) {
            // Assert that the exception message is null
            assertNull(ex.getMessage());
        }
    }
    @Test
    public void testGetBooks() {
        // Test Case 1: Valid file with correct data
        File validFile = createTestFile("ValidFile.csv",
                "Book1,Author1,20.0,4.5,300,Fiction,Publisher1,2022-01-01,10,2021-01-01\n" +
                        "Book2,Author2,15.0,3.8,250,Non-Fiction,Publisher2,2022-02-01,5,2021-02-01");

        ObservableList<Book> result1 = writingToFiles.getBooks(validFile.getAbsolutePath());
        assertEquals(2, result1.size());

        // Test Case 2: File with invalid data causing NumberFormatException
        File invalidFile = createTestFile("InvalidFile.csv",
                "Book1,Author1,20.0,4.5,300,Fiction,Publisher1,2022-01-01,10,2021-01-01\n" +
                        "Book2,Author2,15.0,InvalidRating,250,Non-Fiction,Publisher2,2022-02-01,5,2021-02-01");

        assertThrows(RuntimeException.class, () -> writingToFiles.getBooks(invalidFile.getAbsolutePath()));

        // Note: No cleanup for files
    }
    private File createTestFile(String fileName, String content) {
        try {
            File file = new File(fileName);
            FileWriter writer = new FileWriter(file);
            writer.write(content);
            writer.close();
            return file;
        } catch (IOException e) {
            throw new RuntimeException("Unable to create test file", e);
        }
    }

    @Test
    void testGetPersonsRuntimeException() {

        String nonExistentFilePath = "nonExistentFile.txt";

        assertThrows(RuntimeException.class, () -> {
            ObservableList<Person> people = writingToFiles.getPersons(nonExistentFilePath);

            System.out.println(people.size());
        });
    }

}
