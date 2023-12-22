package EquivalenceClassTesting;

import application.tryingpr.Models.Book;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class BookEqTest {

    @Test
    public void test_ValidConstructorWithRequiredAttributes() {

        Book book = new Book("1234567890", "Sample Book", 20.0, 18.0, 25.0, "Author", "Fiction", " Author1", 10, LocalDate.parse("2023-02-09"));
        assertEquals("1234567890", book.getIsbn());
        assertEquals("Sample Book", book.getTitle());
        assertEquals(20.0, book.getPurchasePrice());
        assertEquals(25.0, book.getSellPrice());
        assertEquals("Fiction", book.getCategory());
    }


    @Test
    public void test_InvalidConstructorWithMissingAttributes() {

        Book book = new Book(null, "Missing Info", -5.0, 18.0, 25.0,
                null, "Fiction", "Publisher", -10, null);
        assertNull(book.getIsbn());
        assertEquals("Missing Info", book.getTitle());
        assertEquals(-5.0, book.getPurchasePrice());
        assertEquals(18.0, book.getOriginalPrice());
        assertEquals(25.0, book.getSellPrice());
        assertNull(book.getAuthor());
        assertEquals("Fiction", book.getCategory());
        assertEquals("Publisher", book.getSupplier());
        assertEquals(-10, book.getStock());
        assertNull(book.getPurchaseDate());
    }


    @Test
    public void test_ToString() {

        Book book = new Book("1234567890", "Title", 10.0, 12.0, 15.0,
                "Author", "Category", "Supplier", 50, LocalDate.parse("2023-05-04"));
        String expected = "1234567890,Title,10.0,12.0,15.0,Author,Category,Supplier,50,2023-05-04";
        assertEquals(expected, book.toString());

    }

    @Test
    public void test_ToStringBill() {

        Book book = new Book("1234567890", "Title", 10.0, 12.0, 15.0,
                "Author", "Category", "Supplier", 50, LocalDate.now());
        String expected = "ISBN: 1234567890 ,Title: Title ,Author: Author";
        assertEquals(expected, book.toStringBill());


        Book book2 = new Book("1234567890", null, 10.0, 12.0, 15.0,
                "Author", "Category", "Supplier", 50, LocalDate.now());
        String expected2 = "ISBN: 1234567890 ,Title: null ,Author: Author";
        assertEquals(expected2, book2.toStringBill());
    }

    @Test
    public void test_StockMethods() {

        Book book = new Book("1234567890", "Title", 10.0, 12.0, 15.0,
                "Author", "Category", "Supplier", 50, LocalDate.now());
        book.setStock(100);
        assertEquals(100, book.getStock());


        book.setStock(-5);
        assertEquals(-5, book.getStock());
    }


    @Test
    public void test_InvalidSetters() {

        Book book = new Book();


        book.setIsbn(null);
        assertNull(book.getIsbn());


        book.setTitle(null);
        assertNull(book.getTitle());


        book.setPurchasePrice(-5.0);
        assertEquals(-5.0, book.getPurchasePrice());


        book.setSellPrice(0.0);

        assertEquals(0.0, book.getSellPrice());


        book.setCategory(null);
        assertNull(book.getCategory());


        book.setSupplier(null);
        assertNull(book.getSupplier());


        book.setStock(-5);
        assertEquals(-5, book.getStock());


        book.setOriginalPrice(0.0);

        assertEquals(0.0, book.getOriginalPrice());


        book.setPurchaseDate(null);
        assertNull(book.getPurchaseDate());

    }

}
