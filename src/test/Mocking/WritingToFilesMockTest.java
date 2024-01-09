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
    
        FileWriterFactory fileWriterFactoryMock = mock(FileWriterFactory.class);

   
        FileWriter fileWriterMock = mock(FileWriter.class);

       
        File fileMock = mock(File.class);
        when(fileMock.exists()).thenReturn(false);

        Calendar calendarMock = mock(Calendar.class);
        when(calendarMock.getTime()).thenReturn(Calendar.getInstance().getTime());

        String billId = "123";
        double totalBill = 100.0;
        ObservableList<Book> books = FXCollections.observableArrayList();
        books.add(new Book("Book1", "Author1", 10.0, 200, 150, "Genre1", "Description1", "Publisher1", 5, LocalDate.parse("2023-01-01")));

        try {
          
            when(fileMock.createNewFile()).thenReturn(true);

      
            when(FileWriterFactory.create(fileMock)).thenReturn(fileWriterMock);

      
            writingToFiles.writeBill(billId, totalBill, books, fileWriterFactoryMock);

        
            verify(fileWriterMock).write("Bill Id: " + billId);
            verify(fileWriterMock).write("\n1: Book1 by Author1 - $10.0");
            verify(fileWriterMock).write("\nTotal: " + totalBill);
            verify(fileWriterMock).close();
        } catch (IOException | RuntimeException e) {
          
            e.printStackTrace();
        }
    }


    @Test
    public void testWriteRolesIOException() {
      
        File tempDir;
        try {
            tempDir = File.createTempFile("temp", Long.toString(System.nanoTime()));
            tempDir.delete(); // Delete the file to make it a directory
            tempDir.mkdir();
        } catch (IOException e) {
            throw new RuntimeException("Unable to create temporary directory", e);
        }

   
        String invalidFilePath = tempDir.getAbsolutePath();
        Assertions.assertThrows(RuntimeException.class, () -> writingToFiles.writeRoles(invalidFilePath));

        tempDir.delete();
    }
}
