package application.tryingpr.Models;

import java.util.ArrayList;

// Model class for representing a bill
public class Bill {

    // List of books in the bill
    private ArrayList<Book> books;

    // Unique id of the bill
    private String billId;

    // Quantity of books in the bill
    private int quantity;

    // Total amount of the bill
    private double totalAmount;

    // Date of the transaction
    private String dateOfTransaction;

    // Constructor to initialize the bill object with the given details
    public Bill(ArrayList<Book> books, String billId, int quantity, double totalAmount, String dateOfTransaction) {
        this.books = books;
        this.billId = billId;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
        this.dateOfTransaction = dateOfTransaction;
    }

    // Getter method for books
    public ArrayList<Book> getBooks() {
        return books;
    }

    // Setter method for books
    public void setBooks(ArrayList<Book> books) {
        this.books = books;
    }

    // Getter method for bill id
    public String getBillId() {
        return billId;
    }

    // Setter method for bill id
    public void setBillId(String billId) {
        this.billId = billId;
    }

    // Getter method for quantity
    public int getQuantity() {
        return quantity;
    }

    // Setter method for quantity
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Getter method for total amount
    public double getTotalAmount() {
        return totalAmount;
    }

    // Setter method for total amount
    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    // Getter method for date of transaction
    public String getDateOfTransaction() {
        return dateOfTransaction;
    }

    // Setter method for date of transaction
    public void setDateOfTransaction(String dateOfTransaction) {
        this.dateOfTransaction = dateOfTransaction;
    }

}
