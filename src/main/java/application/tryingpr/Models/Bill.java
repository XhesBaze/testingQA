package application.tryingpr.Models;

import java.util.ArrayList;

/**
 * @param books             List of books in the bill
 * @param billId            Unique id of the bill
 * @param quantity          Quantity of books in the bill
 * @param totalAmount       Total amount of the bill
 * @param dateOfTransaction Date of the transaction
 */ // Model class for representing a bill
public record Bill(ArrayList<Book> books, String billId, int quantity, double totalAmount, String dateOfTransaction) {

    // Constructor to initialize the bill object with the given details
}
