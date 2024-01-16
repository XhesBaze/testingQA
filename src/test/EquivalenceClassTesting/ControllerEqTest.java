
package EquivalenceClassTesting;
import application.tryingpr.Controllers.Controller;
import application.tryingpr.Models.Book;
import javafx.collections.FXCollections;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ControllerEqTest {

    @Test
    public void test_EmptyObservableLists() {
        Controller.books = FXCollections.observableArrayList();
        Controller.people = FXCollections.observableArrayList();

        Assertions.assertTrue(Controller.books.isEmpty());
        Assertions.assertTrue(Controller.people.isEmpty());
    }

    @Test
    public void test_SingleBookFunctionality() {
        Book sampleBook = new Book();
        Controller.books = FXCollections.observableArrayList(sampleBook);

        Assertions.assertEquals(1, Controller.books.size());
        Assertions.assertTrue(Controller.books.contains(sampleBook));
    }

    @Test
    public void test_MultipleBooks() {
        Book book1 = new Book();
        Book book2 = new Book();
        Controller.books = FXCollections.observableArrayList(book1, book2);

        Assertions.assertEquals(2, Controller.books.size());
        Assertions.assertTrue(Controller.books.contains(book1));
        Assertions.assertTrue(Controller.books.contains(book2));
    }

    @Test
    public void test_ZeroTotalCostAndBill() {
        Controller.totalCost = 0.0;
        Controller.totalBill = 0.0;

        Assertions.assertEquals(0.0, Controller.totalCost);
        Assertions.assertEquals(0.0, Controller.totalBill);
    }

    @Test
    public void test_PositiveTotalBill() {
        Controller.totalBill = 100.0;

        Assertions.assertEquals(100.0, Controller.totalBill);
    }

    @Test
    public void test_NegativeTotalCostOrBill() {
        Controller.totalCost = -50.0;
        Controller.totalBill = -100.0;

        Assertions.assertEquals(-50.0, Controller.totalCost);
        Assertions.assertEquals(-100.0, Controller.totalBill);
    }

    @Test
    public void test_NoBooksSold() {
        Controller.numberOfBooksSold = 0;

        Assertions.assertEquals(0, Controller.numberOfBooksSold);
    }

    @Test
    public void test_ModerateBooksSold() {
        Controller.numberOfBooksSold = 50;

        Assertions.assertEquals(50, Controller.numberOfBooksSold);
    }

    @Test
    public void test_LargeNumberOfBooksSold() {
        Controller.numberOfBooksSold = 1000;

        Assertions.assertEquals(1000, Controller.numberOfBooksSold);
    }
}
