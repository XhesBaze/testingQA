package FXTesting;

import application.tryingpr.View.ManagerPanel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import application.tryingpr.Models.Librarian;
import application.tryingpr.helperClasses.Role;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ManagerPanelTest extends ApplicationTest {

    private ManagerPanel managerPanel;

    @Override
    public void start(Stage stage) {
        managerPanel = new ManagerPanel();

        Scene scene = new Scene(managerPanel, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    void testStatsPanelColumns() {

        ObservableList<Librarian> librarians = FXCollections.observableArrayList();
        librarians.add(new Librarian("John Doe", "john.doe", Role.Librarian));
        librarians.add(new Librarian("Jane Smith", "jane.smith", Role.Librarian));

        managerPanel.statsPanel(librarians);

        TableView<Librarian> tableView = managerPanel.getStatsPanelTableView(librarians);
        assertNotNull(tableView, "TableView should not be null");

        TableColumn<Librarian, String> nameColumn = getColumn(tableView, "Name");
        TableColumn<Librarian, String> usernameColumn = getColumn(tableView, "Username");
        TableColumn<Librarian, Role> roleColumn = getColumn(tableView, "Role");
        TableColumn<Librarian, Integer> totalBilledColumn = getColumn(tableView, "Total Billed");

        assertNotNull(nameColumn, "Name column should not be null");
        assertNotNull(usernameColumn, "Username column should not be null");
        assertNotNull(roleColumn, "Role column should not be null");
        assertNotNull(totalBilledColumn, "Total Billed column should not be null");

        assertEquals("Name", nameColumn.getText());
        assertEquals("Username", usernameColumn.getText());
        assertEquals("Role", roleColumn.getText());
        assertEquals("Total Billed", totalBilledColumn.getText());

        Librarian firstLibrarian = librarians.get(0);
        Librarian secondLibrarian = librarians.get(1);

        assertEquals(firstLibrarian.getName(), nameColumn.getCellData(0));
        assertEquals(firstLibrarian.getUserName(), usernameColumn.getCellData(0));
        assertEquals(firstLibrarian.getRole(), roleColumn.getCellData(0));

        assertEquals(secondLibrarian.getName(), nameColumn.getCellData(1));
        assertEquals(secondLibrarian.getUserName(), usernameColumn.getCellData(1));
        assertEquals(secondLibrarian.getRole(), roleColumn.getCellData(1));
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
