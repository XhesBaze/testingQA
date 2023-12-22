package BoundaryValueTesting;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import application.tryingpr.Models.Book;
import application.tryingpr.Models.Bill;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class BillTest {

    @Test
    public void testValidBillCreation() {
        ArrayList<Book> books = new ArrayList<>();
        String billId = "B001";
        int quantity = 1;
        double totalAmount = 10.0;
        String dateOfTransaction = "2023-01-01";

        assertDoesNotThrow(() -> new Bill(books, billId, quantity, totalAmount, dateOfTransaction));
    }

    @Test
    public void testNegativeQuantity() {
        ArrayList<Book> books = new ArrayList<>();
        String billId = "B001";
        int quantity = -1;
        double totalAmount = 10.0;
        String dateOfTransaction = "2023-01-01";

        assertThrows(IllegalArgumentException.class, () -> new Bill(books, billId, quantity, totalAmount, dateOfTransaction));
    }

    @Test
    public void testExcessiveQuantity() {
        ArrayList<Book> books = new ArrayList<>();
        String billId = "B001";
        int quantity = 600000; // Choose a number exceeding the limit
        double totalAmount = 10.0;
        String dateOfTransaction = "2023-01-01";

        assertThrows(IllegalArgumentException.class, () -> new Bill(books, billId, quantity, totalAmount, dateOfTransaction));
    }

    @Test
    public void testNegativeTotalAmount() {
        ArrayList<Book> books = new ArrayList<>();
        String billId = "B001";
        int quantity = 1;
        double totalAmount = -0.1;
        String dateOfTransaction = "2023-01-01";

        assertThrows(IllegalArgumentException.class, () -> new Bill(books, billId, quantity, totalAmount, dateOfTransaction));
    }

    @Test
    public void testCreateBillStaticMethod() {
        ArrayList<Book> books = new ArrayList<>();
        String billId = "B001";
        int quantity = 1;
        double totalAmount = 10.0;
        String dateOfTransaction = "2023-01-01";

        assertDoesNotThrow(() -> Bill.createBill(books, billId, quantity, totalAmount, dateOfTransaction));
    }
}

