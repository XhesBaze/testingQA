package application.tryingpr.Controllers;

import javafx.collections.ObservableList;
import application.tryingpr.Models.Book;
import application.tryingpr.Models.Person;
import application.tryingpr.helperClasses.writingToFiles;

// Controller class for managing books and persons data
public class Controller {

    // ObservableList to store the books data
    public static ObservableList<Book> books = writingToFiles.getBooks();

    // ObservableList to store the people data
    public static ObservableList<Person> people = writingToFiles.getPersons();

    // Total cost of books
    public static double totalCost = writingToFiles.getTotalCost();

    // Total bill amount for the books
    public static double totalBill = writingToFiles.getTotalBill();

    // Number of books sold
    public static int numberOfBooksSold = writingToFiles.getBooksSold();

}
