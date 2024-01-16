package Integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import application.tryingpr.Controllers.Controller;


import application.tryingpr.Models.Administrator;
import application.tryingpr.Models.Bill;
import application.tryingpr.Models.Book;
import application.tryingpr.Models.Librarian;
import application.tryingpr.Models.Manager;
import application.tryingpr.Models.Person;
import application.tryingpr.helperClasses.Role;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import application.tryingpr.helperClasses.writingToFiles;

import org.junit.jupiter.api.Test;

class WritingToFilesIntegrationTest {

    @Test
    void testBookIntegrateWithBill() {
        Book book1 = new Book("1234567890", "Test Book 1", 10.0, 15.0, 20.0, "Author 1", "Category 1", "Supplier 1", 50, LocalDate.now());
        Book book2 = new Book("0987654321", "Test Book 2", 12.0, 18.0, 25.0, "Author 2", "Category 2", "Supplier 2", 30, LocalDate.now());
        ArrayList<Book> books = new ArrayList<>();
        books.add(book1);
        books.add(book2);

        Bill bill = Bill.createBill(books, "BILL123", 2, 45.0, "2024-01-10");
        assertEquals(2, bill.getBooks().size());
        assertNotNull(bill);
    }


    @Test
    void testIntegrationOfPersonCreation() {
        Person person = new Administrator("name", "Doe", "password", "1990-01-01", 50000, "1234567890", Role.Administrator);
        assertTrue(person instanceof Administrator);
        person = new Manager("name", "Doe", "password", "1990-01-01", 50000, "1234567890", Role.Manager);
        assertTrue(person instanceof Manager);
        person = new Librarian("name", "Doe", "password", "1990-01-01", 50000, "1234567890", Role.Librarian);
        assertTrue(person instanceof Librarian);

    }

    @Test
    public void testWritePersons_integrationTest() throws IOException {
        File tempFile = File.createTempFile("testPersons", ".txt");

        try {
            writingToFiles.writePersons(tempFile.getAbsolutePath());

            assertTrue(tempFile.exists());

            List<String> lines = Files.readAllLines(tempFile.toPath());
            assertEquals(Controller.people.size(), lines.size());
        } finally {
            boolean deleted = tempFile.delete();
            if (!deleted) {
                System.out.println("Failed to delete temporary file: " + tempFile.getAbsolutePath());
            }
        }
    }

}


