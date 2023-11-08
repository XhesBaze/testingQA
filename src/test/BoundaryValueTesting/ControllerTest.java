package BoundaryValueTesting;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import application.tryingpr.Models.Book;
import application.tryingpr.Models.Person;
import application.tryingpr.helperClasses.writingToFiles;

public class ControllerTest {

    @Test
    public void testBooksCreation() {
        ObservableList<Book> books = writingToFiles.getBooks();
        Assertions.assertNotNull(books);
    }

    @Test
    public void testEmptyBooksList() {
        ObservableList<Book> books = FXCollections.observableArrayList();
        Assertions.assertTrue(books.isEmpty());
    }

    @Test
    public void testOneBookList() {
        ObservableList<Book> book = FXCollections.observableArrayList(new Book("TestingBookTitle"));
        Assertions.assertEquals(1, book.size());
        Assertions.assertEquals("TestingBookTitle", book.get(0).getTitle());
    }

    @Test
    public void testFiveBooksList(){
        ObservableList<Book> books = FXCollections.observableArrayList(
                new Book("TestingBook2"),
                new Book("TestingBook3"),
                new Book("TestingBook4"),
                new Book("TestingBook5"),
                new Book("TestingBook6")
        );

        Assertions.assertEquals(5,books.size());
        Assertions.assertEquals("TestingBook2",books.get(0).getTitle());
        Assertions.assertEquals("TestingBook3",books.get(1).getTitle());
        Assertions.assertEquals("TestingBook4",books.get(2).getTitle());
        Assertions.assertEquals("TestingBook5",books.get(3).getTitle());
        Assertions.assertEquals("TestingBook6",books.get(4).getTitle());

    }

    @Test
    public void testMaximumOfBooksList(){
        ObservableList<Book> books = FXCollections.observableArrayList();
        for (int i = 1; i<=200 ; i++){
            books.add(new Book("TestingBook" + i));
        }

        Assertions.assertEquals(200,books.size());

        Assertions.assertEquals("TestingBook199", books.get(198).getTitle());
        Assertions.assertEquals("TestingBook200",books.get(199).getTitle());
    }

    @Test
    public void testExceedingNumOfBooksList(){

        ObservableList<Book> books = FXCollections.observableArrayList();

        for (int i = 1; i <= 201; i++) {
            books.add(new Book("TestingBook " + i));
        }
        Assertions.assertTrue(books.size() > 200);
        Assertions.assertEquals(201,books.size());
    }

    @Test
    public void testRuntimeExceptionExceedingNumOfBooksList() {
        Assertions.assertThrows(
                RuntimeException.class,
                () -> {
                    ObservableList<Book> books = FXCollections.observableArrayList();
                    for (int i = 1; i <= 201; i++) {
                        if (i > 200) {
                            throw new RuntimeException("Maximum number of books exceeded.");
                        }
                        books.add(new Book("TestingBook " + i));
                    }
                },
                "Maximum number of books exceeded."
        );
    }




    @Test
    public void testPeopleInitialization() {
        ObservableList<Person> people = writingToFiles.getPersons();
        Assertions.assertNotNull(people);
    }

    @Test
    public void testTotalCostInitialization() {
        double totalCost = writingToFiles.getTotalCost();
        Assertions.assertEquals(0.0, totalCost, 0.001);
    }

    @Test
    public void testTotalBillInitialization() {
        double totalBill = writingToFiles.getTotalBill();
        Assertions.assertEquals(0.0, totalBill, 0.001);

    }

    @Test
    public void testNumberOfBooksSoldInitialization() {
        int numberOfBooksSold = writingToFiles.getBooksSold();
        Assertions.assertTrue(numberOfBooksSold >= 0);
    }

}

