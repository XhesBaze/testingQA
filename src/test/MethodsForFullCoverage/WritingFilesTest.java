package MethodsForFullCoverage;

import static org.junit.jupiter.api.Assertions.*;

import application.tryingpr.Models.Book;
import application.tryingpr.Models.Person;
import application.tryingpr.helperClasses.writingToFiles;

import java.io.*;

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
            writingToFiles.readCredentials("validUsername", "validPassword", null);

            fail("Expected RuntimeException but no exception was thrown");

        } catch (RuntimeException ex) {

            assertNull(ex.getMessage());
        }
    }

    @Test
    public void testGetBooks(){
        // Test Case 1: Valid file with correct data
        File validFile = createTestFile("ValidFile.csv",
                "Book1,Author1,20.0,4.5,300,Fiction,Publisher1,2022-01-01,10,2021-01-01\n" +
                        "Book2,Author2,15.0,3.8,250,Non-Fiction,Publisher2,2022-02-01,5,2021-02-01");

        ObservableList<Book> result1;
        result1 = writingToFiles.getBooks(validFile.getAbsolutePath());
        assertEquals(2, result1.size());

        File invalidFile = createTestFile("InvalidFile.csv",
                "Book1,Author1,20.0,4.5,300,Fiction,Publisher1,2022-01-01,10,2021-01-01\n" +
                        "Book2,Author2,15.0,InvalidRating,250,Non-Fiction,Publisher2,2022-02-01,5,2021-02-01");

        assertThrows(RuntimeException.class, () -> writingToFiles.getBooks(invalidFile.getAbsolutePath()));

       
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

    @Test
    public void testWriteRolesIOException() {

        String invalidFilePath = "invalidDirectory/invalidFile.txt";

        assertThrows(IOException.class, () -> writingToFiles.writeRoles(invalidFilePath));
    }

    @Test
    public void testGetBooks_FileNotFound() {
        String nonExistentFilePath = "nonexistent.txt";

        assertThrows(IllegalArgumentException.class, () -> writingToFiles.getBooks(nonExistentFilePath));
    }

    @Test
    public void testGetNumberOfBills_DirectoryDoesNotExist() {
        String nonExistentDirPath = "nonExistentDir";

        int result = writingToFiles.getNumberOfBills(nonExistentDirPath);

        assertEquals(0, result);
    }


    @Test
    public void testWriteBooksSold_OutputStreamCreationFails() {
        String nonExistentFilePath = "nonexistent/directory/nonexistentFile.txt";
        RuntimeException exception = assertThrows(RuntimeException.class, () -> writingToFiles.writeBooksSold(nonExistentFilePath, 42));
        assertEquals("java.io.FileNotFoundException: nonexistent\\directory\\nonexistentFile.txt (The system cannot find the path specified)", exception.getMessage());
    }

    @Test
    public void testWriteTotalCostFileCreation() {
        // Define a temporary file path for testing
        String tempFilePath = "temp_test_file.txt";

        // Ensure the file does not exist initially
        File tempFile = new File(tempFilePath);
        if (tempFile.exists()) {
            assertTrue(tempFile.delete(), "Failed to delete existing file before the test");
        }

        // Call the method that should create the file
        try {
            writingToFiles.writeTotalCost(tempFilePath, 123.45);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Verify that the file has been created
        assertTrue(tempFile.exists(), "File should have been created");

        // Clean up: delete the temporary file
        assertTrue(tempFile.delete(), "Failed to delete the temporary file after the test");
    }

    @Test
    public void testNormalWriteTotalCost() {
        String filePath = "res/totalCost1.bin";
        double total = 100.0;
        try {
            writingToFiles.writeTotalCost(filePath, total);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testWriteTotalBill() throws IOException {

        String testFilePath = "testFile.txt";
        double testTotal = 100.0;

        writingToFiles.writeTotalBill(testFilePath, testTotal);

        File testFile = new File(testFilePath);
        assertTrue(testFile.exists(), "File should be created");

    }

    @Test
    public void testGetBooksFileNotFound() {
        String randomFilePath = "random_file.txt";

        RuntimeException exception = assertThrows(RuntimeException.class, () -> writingToFiles.getBooks(randomFilePath));

        String expectedErrorMessage = "File not found: " + randomFilePath;
        String actualErrorMessage = exception.getMessage();

        assertEquals(expectedErrorMessage, actualErrorMessage);
    }

    @Test
    public void testNonExistingFile() {
        String filePath = "path/to/non_existing_file.bin";
        double result = writingToFiles.getTotalBill(filePath);
        assertEquals(0, result, 0.001);
    }

    @Test
    public void testIOExceptionDuringReading() throws IOException {
        File tempFile = File.createTempFile("test", ".bin");
        String filePath = tempFile.getAbsolutePath();
        double result = writingToFiles.getTotalBill(filePath);
        assertEquals(0, result, 0.001);
    }

    @Test
    void testGetTotalCostFileDoesNotExist() {
        String filePath = "nonexistent_file.bin";
        double result = writingToFiles.getTotalCost(filePath);
        assertEquals(0, result, 0.0001);
    }

    @Test
    public void testGetBooksFileNotFoundException() {
        String nonExistentFilePath = "nonExistentFilePath.txt";

        assertThrows(RuntimeException.class, () -> writingToFiles.getBooks(nonExistentFilePath));
    }

    @Test
    public void testGetBooksSold_FileDoesNotExist() {
        String filePath = "file.txt";
        int result = writingToFiles.getBooksSold(filePath);
        assertEquals(0, result);
    }

    @Test
    public void testGetBooksSold_FileIsEmpty() throws IOException {
        File emptyFile = File.createTempFile("emptyFile", ".txt");
        String filePath = emptyFile.getAbsolutePath();

        int result = writingToFiles.getBooksSold(filePath);

        assertEquals(0, result);
    }

    @Test
    public void testGetTotalCost_FileIsEmpty() throws IOException {
        File emptyFile = File.createTempFile("emptyFile", ".txt");
        String filePath = emptyFile.getAbsolutePath();

        double result = writingToFiles.getTotalCost(filePath);

        assertEquals(0, result);
    }

}

