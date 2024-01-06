package MethodsForFullCoverage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import application.tryingpr.Models.Book;
import application.tryingpr.Models.Bill;

public class BillCreationUnitTest {

    @Test
    void createBillWithInvalidValues() {
        ArrayList<Book> books = new ArrayList<>();
        String validBillId = "123";
        int invalidQuantity = -1;
        double invalidTotalAmount = -50.0;

        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new Bill(books, validBillId, invalidQuantity, invalidTotalAmount, null));
    }

    @Test
    void testGetBooks() {
        ArrayList<Book> sampleBooks = new ArrayList<>();
        sampleBooks.add(new Book("Book1"));
        sampleBooks.add(new Book("Book2"));
        sampleBooks.add(new Book("Book3"));

        Bill bill = new Bill(sampleBooks, "123", 2, 50.0, "2024-01-06");

        Assertions.assertEquals(sampleBooks, bill.getBooks());
    }
}
