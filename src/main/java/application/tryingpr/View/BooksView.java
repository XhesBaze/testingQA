package application.tryingpr.View;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import application.tryingpr.Models.Book;

public class BooksView extends VBox{
    private TableView<Book> tableView;
    private ObservableList<Book> books;

    public BooksView(ObservableList<Book> book) {

        this.books = book;

        tableView = new TableView<>();

        TableColumn<Book, String> ISBNColumn = new TableColumn<>("ISBN");
        ISBNColumn.setCellValueFactory(new PropertyValueFactory<>("isbn"));

        TableColumn<Book, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Book, Double> purchasePriceColumn = new TableColumn<>("Purchase Price");
        purchasePriceColumn.setCellValueFactory(new PropertyValueFactory<>("purchasePrice"));

        TableColumn<Book, Double> salesPriceColumn = new TableColumn<>("Sales Price");
        salesPriceColumn.setCellValueFactory(new PropertyValueFactory<>("sellPrice"));

        TableColumn<Book, String> categoryColumn = new TableColumn<>("Category");
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));

        TableColumn<Book, Void> actionColumn = new TableColumn<>("Actions");

        Callback<TableColumn<Book, Void>, TableCell<Book, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Book, Void> call(final TableColumn<Book, Void> param) {
                return new TableCell<>() {
                    private final Button editButton = new Button("Edit");
                    private final Button deleteButton = new Button("Delete");

                    private ImageView editIcon = new ImageView("edit.png");
                    private ImageView deleteIcon = new ImageView("delete.png");

                    {

                        editIcon.setFitHeight(15);
                        editIcon.setFitWidth(15);
                        deleteIcon.setFitWidth(15);
                        deleteIcon.setFitHeight(15);
                        deleteButton.setGraphic(deleteIcon);
                        editButton.setGraphic(editIcon);
                        editButton.setStyle("-fx-background-color: transparent;");
                        deleteButton.setStyle("-fx-background-color: transparent;");

                        editButton.setOnAction((ActionEvent event) -> {
                            Book book = getTableView().getItems().get(getIndex());
                            showEditBookWindow(book);
                            BooksView.this.tableView.refresh();
                        });
                        deleteButton.setOnAction((ActionEvent event) -> {
                            // logic to handle delete action
                            Book book = getTableView().getItems().get(getIndex());
                            books.remove(book);
                            tableView.refresh();
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            HBox buttonsBox = new HBox(editButton, deleteButton);
                            setGraphic(buttonsBox);
                        }
                    }
                };
            }
        };

        actionColumn.setCellFactory(cellFactory);
        tableView.getColumns().addAll(ISBNColumn, titleColumn, purchasePriceColumn, salesPriceColumn, categoryColumn, actionColumn);
        tableView.setItems(books);

        Label lblHeading = new Label("List of Books");
        lblHeading.setStyle("-fx-font-size: 20px;");
        getChildren().addAll(lblHeading,tableView);
        setSpacing(10);
        setPadding(new Insets(10));
    }

    private void showEditBookWindow(Book book) {
        // create a new window to show the form to edit the book
        Stage stage = new Stage();
        stage.setTitle("Edit Book");

        // create the form to edit the book
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        // create text fields for the book properties
        TextField isbnField = new TextField();
        isbnField.setPromptText("ISBN");
        isbnField.setText(book.getIsbn());

        TextField titleField = new TextField();
        titleField.setPromptText("Title");
        titleField.setText(book.getTitle());

        TextField categoryField = new TextField();
        categoryField.setPromptText("Category");
        categoryField.setText(book.getCategory());

        DatePicker purchasedDate = new DatePicker();
        purchasedDate.setValue(book.getPurchaseDate());

        TextField supplierField = new TextField();
        supplierField.setPromptText("Supplier");
        supplierField.setText(book.getSupplier());

        TextField purchasedPriceField = new TextField();
        purchasedPriceField.setPromptText("Purchased Price");
        purchasedPriceField.setText(String.valueOf(book.getPurchasePrice()));

        TextField originalPriceField = new TextField();
        originalPriceField.setPromptText("Original Price");
        originalPriceField.setText(String.valueOf(book.getOriginalPrice()));

        TextField sellingPriceField = new TextField();
        sellingPriceField.setPromptText("Selling Price");
        sellingPriceField.setText(String.valueOf(book.getSellPrice()));

        TextField authorField = new TextField();
        authorField.setPromptText("Author");
        authorField.setText(book.getAuthor());

        TextField stockField = new TextField();
        stockField.setPromptText("Stock");
        stockField.setText(String.valueOf(book.getStock()));

// add the text fields to the grid
        grid.add(new Label("ISBN"), 0, 0);
        grid.add(isbnField, 1, 0);
        grid.add(new Label("Title"), 0, 1);
        grid.add(titleField, 1, 1);
        grid.add(new Label("Category"), 0, 2);
        grid.add(categoryField, 1, 2);
        grid.add(new Label("Supplier"), 0, 3);
        grid.add(supplierField, 1, 3);
        grid.add(new Label("Purchased Date"), 0, 4);
        grid.add(purchasedDate,1,4);
        grid.add(new Label("Purchased Price"), 0, 5);
        grid.add(purchasedPriceField, 1, 5);
        grid.add(new Label("Original Price"), 0, 6);
        grid.add(originalPriceField, 1, 6);
        grid.add(new Label("Selling Price"), 0, 7);
        grid.add(sellingPriceField, 1, 7);
        grid.add(new Label("Author"), 0, 8);
        grid.add(authorField, 1, 8);
        grid.add(new Label("Stock"), 0, 9);
        grid.add(stockField, 1, 9);

// create a save button to save the changes
        Button saveButton = new Button("Save");
        saveButton.setOnAction((ActionEvent event) -> {
            // validate the form
            if (isbnField.getText().isEmpty() || titleField.getText().isEmpty() || categoryField.getText().isEmpty()
                    || supplierField.getText().isEmpty() || purchasedPriceField.getText().isEmpty() || originalPriceField.getText().isEmpty()
                    || sellingPriceField.getText().isEmpty() || authorField.getText().isEmpty()
                    || stockField.getText().isEmpty()) {
                // show an error if any of the fields is empty
                System.out.println("Fill all fields");
            } else {
                // update the book with the new values
                book.setIsbn(isbnField.getText());
                book.setTitle(titleField.getText());
                book.setCategory(categoryField.getText());
                book.setSupplier(supplierField.getText());
                book.setPurchasePrice(Double.parseDouble(purchasedPriceField.getText()));
                book.setOriginalPrice(Double.parseDouble(originalPriceField.getText()));
                book.setSellPrice(Double.parseDouble(sellingPriceField.getText()));
                book.setAuthor(authorField.getText());
                book.setStock(Integer.parseInt(stockField.getText()));
                book.setPurchaseDate(purchasedDate.getValue());
                stage.close();
            }
        });
        grid.add(saveButton, 1, 10);
        grid.setAlignment(Pos.CENTER);
// create a scene and show the window
        Scene scene = new Scene(grid, 600, 400);
        stage.setScene(scene);
        stage.show();
    }
}
