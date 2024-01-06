package Mocking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import application.tryingpr.Models.*;
import application.tryingpr.helperClasses.FileWriterFactory;
import application.tryingpr.helperClasses.writingToFiles;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.*;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Scanner;

class WritingToFilesMockTest {

    @Test
    void testReadCredentials() {
        String username = "testUser";
        String password = "testPassword";
        String role = "admin";


        File fileMock = Mockito.mock(File.class);

        try (MockedStatic<Scanner> scannerMock = Mockito.mockStatic(Scanner.class)) {

            when(fileMock.exists()).thenReturn(true);

            String content = username + "," + password + "," + role + "\n";
            InputStream inputStream = new ByteArrayInputStream(content.getBytes());

            Scanner scanner = new Scanner(inputStream);
            scannerMock.when(() -> new Scanner(fileMock)).thenReturn(scanner);


            String result = writingToFiles.readCredentials(username, password,"res/ROLES.txt");


            assertEquals(role, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testWriteBill() {
        // Mock the FileWriterFactory
        FileWriterFactory fileWriterFactoryMock = mock(FileWriterFactory.class);

        // Mock the FileWriter class
        FileWriter fileWriterMock = mock(FileWriter.class);

        // Mock the File class
        File fileMock = mock(File.class);
        when(fileMock.exists()).thenReturn(false);

        // Mock the Calendar class
        Calendar calendarMock = mock(Calendar.class);
        when(calendarMock.getTime()).thenReturn(Calendar.getInstance().getTime());

        // Create sample data for testing
        String billId = "123";
        double totalBill = 100.0;
        ObservableList<Book> books = FXCollections.observableArrayList();
        books.add(new Book("Book1", "Author1", 10.0, 200, 150, "Genre1", "Description1", "Publisher1", 5, LocalDate.parse("2023-01-01")));

        try {
            // Mock file creation behavior
            when(fileMock.createNewFile()).thenReturn(true);

            // Mock FileWriterFactory behavior
            when(FileWriterFactory.create(fileMock)).thenReturn(fileWriterMock);

            // Call the method under test
            writingToFiles.writeBill(billId, totalBill, books, fileWriterFactoryMock);

            // Verify the interactions with FileWriter
            verify(fileWriterMock).write("Bill Id: " + billId);
            verify(fileWriterMock).write("\n1: Book1 by Author1 - $10.0");
            verify(fileWriterMock).write("\nTotal: " + totalBill);
            verify(fileWriterMock).close();
        } catch (IOException | RuntimeException e) {
            // Handle exceptions or re-throw if necessary
            e.printStackTrace();
        }
    }


    @Test
    public void testWriteRolesIOException() {
        // Create a temporary directory to simulate an invalid file path
        File tempDir;
        try {
            tempDir = File.createTempFile("temp", Long.toString(System.nanoTime()));
            tempDir.delete(); // Delete the file to make it a directory
            tempDir.mkdir();
        } catch (IOException e) {
            throw new RuntimeException("Unable to create temporary directory", e);
        }

        // Call the method with the invalid file path
        String invalidFilePath = tempDir.getAbsolutePath();
        Assertions.assertThrows(RuntimeException.class, () -> writingToFiles.writeRoles(invalidFilePath));

        // Cleanup: Delete the temporary directory
        tempDir.delete();
    }
}