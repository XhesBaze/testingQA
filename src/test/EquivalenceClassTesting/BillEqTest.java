package EquivalenceClassTesting;

import application.tryingpr.Models.Bill;
import application.tryingpr.Models.Book;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class BillEqTest {

    @Test
    public void test_EmptyBooksList() {
        Bill bill = new Bill(new ArrayList<>(), "10141", 2, 50.0, "2023-12-19");
        assertNotNull(bill);

    }

    @Test
    public void test_NonEmptyBooksList() {
        ArrayList<Book> books = new ArrayList<>();
        books.add(new Book());
        Bill bill = new Bill(books, "10422", 2, 50.0, "2023-12-19");
        assertNotNull(bill);

    }

    @Test
    public void test_NullBillId() {
        assertThrows(IllegalArgumentException.class, () -> new Bill(new ArrayList<>(), null, 2, 50.0, "2023-12-19"));
    }

    @Test
    public void test_ValidBillId() {
        Bill bill = new Bill(new ArrayList<>(), "B1234", 2, 50.0, "2023-12-19");
        assertNotNull(bill);
    }

    @Test
    public void test_ZeroQuantity() {
        assertThrows(IllegalArgumentException.class, () -> new Bill(new ArrayList<>(), "validID", 0, 50.0, "2023-12-19"));
    }

    @Test
    public void test_ZeroTotalAmount() {
        assertThrows(IllegalArgumentException.class, () -> new Bill(new ArrayList<>(), "validID", 2, 0.0, "2023-12-19"));
    }

    @Test
    public void test_NullDateOfTransaction() {
        assertThrows(IllegalArgumentException.class, () -> new Bill(new ArrayList<>(), "validID", 2, 50.0, ""));
    }

    @Test
    public void test_ValidDateOfTransaction() {
        Bill bill = new Bill(new ArrayList<>(), "validID", 2, 50.0, "2023-12-19");
        assertNotNull(bill);

    }
}
