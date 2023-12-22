package BoundaryValueTesting;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import application.tryingpr.Models.Book;
import application.tryingpr.Models.Person;
import application.tryingpr.helperClasses.writingToFiles;

public class ControllerTest {

    @Test
    public void test_BooksListCreation() {
        ObservableList<Book> books = writingToFiles.getBooks();
        Assertions.assertNotNull(books);
    }

    @Test
    public void test_EmptyBooksList() {
        ObservableList<Book> books = FXCollections.observableArrayList();
        Assertions.assertTrue(books.isEmpty());
    }

    @Test
    public void test_OneBookList() {
        ObservableList<Book> book = FXCollections.observableArrayList(new Book("TestingBookTitle"));
        Assertions.assertEquals(1, book.size());
        Assertions.assertEquals("TestingBookTitle", book.get(0).getTitle());
    }

    @Test
    public void test_AverageNumberOfBooksList(){
        ObservableList<Book> books = FXCollections.observableArrayList();
        for (int i = 1; i<=100 ; i++){
            books.add(new Book("TestingBook" + i));
        }

        Assertions.assertEquals(100,books.size());
        Assertions.assertEquals("TestingBook34",books.get(33).getTitle());
        Assertions.assertEquals("TestingBook12",books.get(11).getTitle());
        Assertions.assertEquals("TestingBook80",books.get(79).getTitle());
        Assertions.assertEquals("TestingBook67",books.get(66).getTitle());
        Assertions.assertEquals("TestingBook99",books.get(98).getTitle());

    }

    @Test
    public void test_MaximumNumberOfBooksList(){
        ObservableList<Book> books = FXCollections.observableArrayList();
        for (int i = 1; i<=200 ; i++){
            books.add(new Book("TestingBook" + i));
        }

        Assertions.assertEquals(200,books.size());

        Assertions.assertEquals("TestingBook199", books.get(198).getTitle());
        Assertions.assertEquals("TestingBook200",books.get(199).getTitle());
    }

    @Test
    public void test_ExceedingNumberOfBooksList(){

        ObservableList<Book> books = FXCollections.observableArrayList();

        for (int i = 1; i <= 201; i++) {
            books.add(new Book("TestingBook " + i));
        }
        Assertions.assertTrue(books.size() > 200);
        Assertions.assertEquals(201,books.size());
    }

    @Test
    public void test_RuntimeExceptionExceedingNumberOfBooksList() {
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
    public void test_PeopleListCreation() {
        ObservableList<Person> people = writingToFiles.getPersons();
        Assertions.assertNotNull(people);
    }

    @Test
    public void test_EmptyPeopleList() {
        ObservableList<Person> people = FXCollections.observableArrayList();
        Assertions.assertTrue(people.isEmpty());
    }

    @Test
    public void test_MaximumNumberOfPeopleList(){
        ObservableList<Person> people = FXCollections.observableArrayList();
        for (int i = 1; i<=300 ; i++){
            people.add(new Person("TestingPerson" + i));
        }

        Assertions.assertEquals(300,people.size());

        Assertions.assertEquals("TestingPerson299", people.get(298).getName());
        Assertions.assertEquals("TestingPerson300",people.get(299).getName());
    }

    @Test
    public void test_ExceedingNumberOfPeopleList(){

        ObservableList<Person> people = FXCollections.observableArrayList();

        for (int i = 1; i <= 301; i++) {
            people.add(new Person("TestingPerson " + i));
        }
        Assertions.assertTrue(people.size() > 200);
        Assertions.assertEquals(301,people.size());
    }

    @Test
    public void test_RandomNumberOfPeopleList(){
        ObservableList<Person> people = FXCollections.observableArrayList();
        for (int i = 1; i<=150 ; i++){
            people.add(new Person("TestingPerson" + i));
        }

        Assertions.assertEquals(150,people.size());
        Assertions.assertEquals("TestingPerson74",people.get(73).getName());
        Assertions.assertEquals("TestingPerson125",people.get(124).getName());
        Assertions.assertEquals("TestingPerson80",people.get(79).getName());
        Assertions.assertEquals("TestingPerson149",people.get(148).getName());
        Assertions.assertEquals("TestingPerson3",people.get(2).getName());

    }

    @Test
    public void test_RuntimeExceptionExceedingNumberOfPeopleList() {
        Assertions.assertThrows(
                RuntimeException.class,
                () -> {
                    ObservableList<Person> people = FXCollections.observableArrayList();
                    for (int i = 1; i <= 301; i++) {
                        if (i > 300) {
                            throw new RuntimeException("Maximum number of people exceeded.");
                        }
                        people.add(new Person("TestingPerson " + i));
                    }
                },
                "Maximum number of people exceeded."
        );
    }

    @BeforeEach
    public void setUp() {

        clearTotalCost();
        initializeTotalBill();
    }

    @Test
    public void test_TotalCostListCreation() {
        double totalCost = writingToFiles.getTotalCost();
        Assertions.assertEquals(0.0, totalCost, 0.001);
    }

    @Test
    public void test_TotalBillListCreation() {
        double totalBill = writingToFiles.getTotalBill();
        Assertions.assertEquals(947.97, totalBill, 0.001);
    }


    private void clearTotalCost() {

        writingToFiles.writeTotalCost(0.0);
    }


    private void initializeTotalBill() {
        writingToFiles.writeTotalBill(947.97);
    }

    @Test
    public void test_NumberOfBooksSoldCreation() {
        int numberOfBooksSold = writingToFiles.getBooksSold();
        Assertions.assertTrue(numberOfBooksSold >= 0);
    }

}

