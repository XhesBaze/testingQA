package Mocking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import application.tryingpr.Controllers.Controller;
import application.tryingpr.Models.Book;
import application.tryingpr.Models.Librarian;
import application.tryingpr.Models.Manager;
import application.tryingpr.Models.Person;
import application.tryingpr.helperClasses.FileWriterFactory;
import application.tryingpr.helperClasses.Role;
import application.tryingpr.helperClasses.writingToFiles;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.file.Path;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.*;

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


            String result = writingToFiles.readCredentials("res/ROLES.txt",username, password);


            assertEquals(role, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testCheckFileContent() {
        String filePath = "testFile.txt";
        String expectedContent = "Test Content";
        mockStatic(Path.class);
        when(Path.of(anyString())).thenReturn(any(Path.class));
        assertThrows(NullPointerException.class, () -> writingToFiles.checkFileContent(filePath, expectedContent));
    }

    @Test
    void testSerializeObjects() {
        // Create a list of mock objects
        ObservableList<Person> people = FXCollections.observableArrayList();
        people.add(mock(Person.class));
        people.add(mock(Person.class));

        // Mock the serializer function
        Function<Person, String> serializer = person -> "Serialized " + person.toString();

        List<String> result = writingToFiles.serializeObjects(people, serializer);

        assertEquals(2, result.size());
        assertTrue(result.get(0).startsWith("Serialized"));
        assertTrue(result.get(1).startsWith("Serialized"));
    }

    @Test
    public void testWriteRoles() {
        mockPeople(List.of(getPerson("user", "user")));
        try {
            writingToFiles.writeRoles("res/ROLES.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testGetNumberOfLibrariansWhenOne() {
        mockPeople(List.of(getLibrarian()));
        String actual = writingToFiles.getNumberOfLibrarians();
        String expected = "1";
        assertEquals(expected, actual);
    }

    @Test
    public void testGetNumberOfLibrariansWhenZero() {
        mockPeople(List.of(getPerson("person1", "password")));
        String actual = writingToFiles.getNumberOfLibrarians();
        String expected = "0";
        assertEquals(expected, actual);
    }


    @Test
    void getNumberOfManagersWhenZero() {
        String actual = writingToFiles.getNumberOfManagers();
        String expected = "0";
        assertEquals(expected, actual);
    }

    @Test
    void getNumberOfManagersWhenOne() {
        mockPeople(List.of(getManager()));
        String actual = writingToFiles.getNumberOfManagers();
        String expected = "1";
        assertEquals(expected, actual);
    }

    @Test
    void getNumberOfBills() {
        int actual = writingToFiles.getNumberOfBills("res/BILLS");
        int expected = 41;
        assertEquals(expected, actual);
    }

    @Test
    void writeBill() {
        FileWriterFactory fileWriterFactoryMock = mock(FileWriterFactory.class);

        String billId = "102";
        double totalBill = 50.0;
        ObservableList<Book> books = FXCollections.observableArrayList(List.of(new Book("Java Core")));
        writingToFiles.writeBill(billId, totalBill, books, fileWriterFactoryMock);
    }

    @Test
    void writeBillWhenTotalBillLessThanZero() {
        FileWriterFactory fileWriterFactoryMock = mock(FileWriterFactory.class);

        String billId = "22";
        double totalBill = -122;
        ObservableList<Book> books = FXCollections.observableArrayList(List.of(new Book("bb")));
        IllegalArgumentException thrownException = assertThrows(IllegalArgumentException.class, () -> writingToFiles.writeBill(billId, totalBill, books, fileWriterFactoryMock));
        assertException("Total cannot be less than zero", thrownException);
    }

    @Test
    void writeBillWhenNoBooks() {
        FileWriterFactory fileWriterFactoryMock = mock(FileWriterFactory.class);

        String billId = "102";
        double totalBill = 50.0;
        IllegalArgumentException thrownException = assertThrows(IllegalArgumentException.class, () -> writingToFiles.writeBill(billId, totalBill, null, fileWriterFactoryMock));
        assertException("Books cannot be null", thrownException);
    }

    @Test
    void writeBillWhenBillIdEmpty() {
        FileWriterFactory fileWriterFactoryMock = mock(FileWriterFactory.class);
        String billId = "";
        double totalBill = 50.0;
        ObservableList<Book> books = FXCollections.observableArrayList(List.of(new Book("bb")));
        IllegalArgumentException thrownException = assertThrows(IllegalArgumentException.class, () -> writingToFiles.writeBill(billId, totalBill, books, fileWriterFactoryMock));
        assertException("Bill ID cannot be null", thrownException);
    }


    @Test
    void writeBooks() {
        List<Book> books = new ArrayList<>();
        books.add(getBook());
        mockBooks(books);
        try {
            writingToFiles.writeBooks("res/bookInfo.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void writeBooksWhenMaximumNumberOfBooksExceeded() {
        List<Book> books = new ArrayList<>();
        for (int i = 0; i < Book.MAX_NUM_OF_BOOKS + 10; i++) {
            books.add(getBook());
        }
        mockBooks(books);
        RuntimeException thrownException = assertThrows(RuntimeException.class, () -> writingToFiles.writeBooks("res/bookInfo.txt"));

        assertException("Maximum number of books exceeded.", thrownException);
    }

    @Test
    void writePersons() {
        List<Person> people = new ArrayList<>();
        people.add(getPerson("person1", "password"));
        mockPeople(people);
        try {
            writingToFiles.writePersons("res/PEOPLE1.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void writePersonsWhenMaximumNumberOfPersonsExceeded() {
        List<Person> people = new ArrayList<>();
        for (int i = 0; i < Person.MAX_NUM_OF_PERSONS + 10; i++) {
            people.add(getPerson("person1", "password"));

        }
        mockPeople(people);
        IOException thrownException = assertThrows(IOException.class, () -> writingToFiles.writePersons("res/PEOPLE1.txt"));

        assertException("Error while writing persons to file", thrownException);
    }

    @Test
    void writeTotalBill() {
        mockTotalBill();
        try {
            writingToFiles.writeTotalBill("res/totalBill1.bin",200);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void writeTotalCost() {
        mockTotalCost();
        try {
            writingToFiles.writeTotalCost("res/totalCost1.bin",200);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void writeBooksSold() {
        mockTotalNumberOfBookSold();
        writingToFiles.writeBooksSold("res/booksSold1.bin",40);
    }

    @Test
    void createFileOutputStream() throws IOException {
        writingToFiles.createFileOutputStream(new File("path_not"));
    }

    private void assertException(String message, Exception exceptionThrown) {
        Assertions.assertEquals(message, exceptionThrown.getMessage());
    }

    private Librarian getLibrarian() {
        return new Librarian("person1", "person1", "password", "1990-01-01", 50000, "1234567890", Role.Librarian);
    }

    private Manager getManager() {
        return new Manager("person1", "person1", "password", "1990-01-01", 50000, "1234567890", Role.Librarian);
    }

    private Person getPerson(String name, String password) {
        return new Person(name, name, password, "1990-01-01", 50000, "1234567890", Role.Librarian);
    }

    private void mockPeople(List<Person> personList) {
        try (MockedStatic<Controller> controllerMockedStatic = mockStatic(Controller.class)) {
            Field field = Controller.class.getDeclaredField("people");
            field.setAccessible(true);
            field.set(null, FXCollections.observableArrayList(personList));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Book getBook() {
        return new Book("123456789", "Java Core", 20.0, 25.0, 30.0, "John Author", "Fiction", "Book Supplier", 50, LocalDate.now());
    }

    private void mockBooks(List<Book> bookList) {
        try (MockedStatic<Controller> controllerMockedStatic = mockStatic(Controller.class)) {
            Field field = Controller.class.getDeclaredField("books");
            field.setAccessible(true);
            field.set(null, FXCollections.observableArrayList(bookList));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void mockTotalCost() {
        try (MockedStatic<Controller> controllerMockedStatic = mockStatic(Controller.class)) {
            Field field = Controller.class.getDeclaredField("totalCost");
            field.setAccessible(true);
            field.set(null, (double) 100);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void mockTotalBill() {
        try (MockedStatic<Controller> controllerMockedStatic = mockStatic(Controller.class)) {
            Field field = Controller.class.getDeclaredField("totalBill");
            field.setAccessible(true);
            field.set(null, (double) 100);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void mockTotalNumberOfBookSold() {
        try (MockedStatic<Controller> controllerMockedStatic = mockStatic(Controller.class)) {
            Field field = Controller.class.getDeclaredField("numberOfBooksSold");
            field.setAccessible(true);
            field.set(null, 32);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testWriteBill() {
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


}

