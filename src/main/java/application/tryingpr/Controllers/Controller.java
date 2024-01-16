package application.tryingpr.Controllers;

import javafx.collections.ObservableList;
import application.tryingpr.Models.Book;
import application.tryingpr.Models.Person;
import application.tryingpr.helperClasses.writingToFiles;

public class Controller {

    // ObservableList to store the books data
    public static ObservableList<Book> books = writingToFiles.getBooks("res/bookInfo.txt");

    // ObservableList to store the people data
    public static ObservableList<Person> people = writingToFiles.getPersons("res/PERSONS.txt");

    // Total cost of books
    public static double totalCost = writingToFiles.getTotalCost("res/totalCost1.bin");

    // Total bill amount for the books
    public static double totalBill = writingToFiles.getTotalBill("res/totalBill1.bin");

    // Number of books sold
    public static int numberOfBooksSold = writingToFiles.getBooksSold("res/booksSold1.bin");

}
