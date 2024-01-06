package application.tryingpr.Models;

import java.util.ArrayList;
import java.util.Objects;

public class Bill {

    private final ArrayList<Book> books;

    public Bill(ArrayList<Book> books, String billId, int quantity, double totalAmount, String dateOfTransaction) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity cannot be zero or negative");
        }
        if (quantity > 500000) {
            throw new IllegalArgumentException("Quantity cannot be a number this big");
        }
        if (totalAmount <= 0) {
            throw new IllegalArgumentException("Total amount cannot be less than or equal to 0");
        }
        if (billId == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        if (Objects.equals(dateOfTransaction, "")) {
            throw new IllegalArgumentException("Date of transaction cannot be empty");
        }

        this.books = books;
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public static Bill createBill(ArrayList<Book> books, String billId, int quantity, double totalAmount, String dateOfTransaction) {
        return new Bill(books, billId, quantity, totalAmount, dateOfTransaction);
    }
}
