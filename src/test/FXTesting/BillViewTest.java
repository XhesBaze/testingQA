package FXTesting;

import application.tryingpr.View.BillView;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import application.tryingpr.Models.Book;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BillViewTest extends ApplicationTest {

    private BillView billView;

    @Override
    public void start(Stage stage) {

        billView = new BillView();

        Scene scene = new Scene(billView,600,600);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    void testBillTableColumns() {

        billView.billTable();

        TableView<Book> tableView = billView.getTable();
        assertNotNull(tableView, "TableView should not be null");

        TableColumn<Book, String> isbnColumn = getColumn(tableView, "ISBN");
        TableColumn<Book, String> titleColumn = getColumn(tableView, "Title");
        TableColumn<Book, String> categoryColumn = getColumn(tableView, "Category");
        TableColumn<Book, Double> sellPriceColumn = getColumn(tableView, "Sell Price");
        TableColumn<Book, Void> quantityColumn = getColumn(tableView, "Quantity");


        assertNotNull(isbnColumn, "ISBN column should not be null");
        assertNotNull(titleColumn, "Title column should not be null");
        assertNotNull(categoryColumn, "Category column should not be null");
        assertNotNull(sellPriceColumn, "Sell Price column should not be null");
        assertNotNull(quantityColumn, "Quantity column should not be null");

        assertEquals("ISBN", isbnColumn.getText());
        assertEquals("Title", titleColumn.getText());
        assertEquals("Category", categoryColumn.getText());
        assertEquals("Sell Price", sellPriceColumn.getText());
        assertEquals("Quantity", quantityColumn.getText());
    }


    private <S, T> TableColumn<S, T> getColumn(TableView<S> tableView, String columnName) {
        for (TableColumn<S, ?> column : tableView.getColumns()) {
            if (column.getText().equals(columnName)) {
                return (TableColumn<S, T>) column;
            }
        }
        return null;
    }
}
