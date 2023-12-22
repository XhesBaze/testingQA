package application.tryingpr.Models;

import java.util.ArrayList;
import java.util.Objects;

/**
 * @param books             List of books in the bill
 * @param billId            Unique id of the bill
 * @param quantity          Quantity of books in the bill (non-negative)
 * @param totalAmount       Total amount of the bill
 * @param dateOfTransaction Date of the transaction
 */
public record Bill(ArrayList<Book> books, String billId, int quantity, double totalAmount, String dateOfTransaction) {


    public Bill {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity cannot be zero or negative");
        }
        if(quantity > 500000){
            throw new IllegalArgumentException("Quantity cannot be a number this big");
        }
        if(totalAmount <= 0){
            throw new IllegalArgumentException("Total amount cannot be less than or equal to 0");
        }
        if(billId == null){
            throw new IllegalArgumentException("ID cannot be null");
        }
        if(Objects.equals(dateOfTransaction, "")){
            throw new IllegalArgumentException("Date of transaction cannot be empty");
        }
    }


    public static void createBill(ArrayList<Book> books, String billId, int quantity, double totalAmount, String dateOfTransaction) {
        new Bill(books, billId, quantity, totalAmount, dateOfTransaction);
    }
}
