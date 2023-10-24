package application.tryingpr.View;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import application.tryingpr.Controllers.Controller;
import application.tryingpr.Models.Book;
import application.tryingpr.Models.Librarian;
import application.tryingpr.Models.Person;
import application.tryingpr.helperClasses.writingToFiles;

import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class BillView extends BorderPane{
    private TableView<Book> table = new TableView<>();
    private AtomicInteger quantity = new AtomicInteger();
    private double totalPrice = 0.0;
    private int billId = writingToFiles.getNumberOfBills();
    private ObservableList<Book> books1 = FXCollections.observableArrayList();
    private Label totalPriceLabel = new Label("Total: $" + totalPrice);

    public static double total = Controller.totalBill;
    public static int booksSold = Controller.numberOfBooksSold;

    private Person person;
    private Label billIdLabel;

    public BillView(ObservableList<Book> books) {

        Button addBookButton = new Button("Add Book");
        Button generateBillButton = new Button("Generate Bill");
        generateBillButton.setCursor(Cursor.HAND);
        generateBillButton.setPrefSize(100,20);
        generateBillButton.setOnAction(e -> {
            if (books1.size() > 0) {
// Call writeBill method of UtilityHelper class to write bill details into a file
                writingToFiles.writeBill(String.valueOf(billId), totalPrice, books1);
                // Show an information alert to the user that the bill has been generated
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Bill Generated");
                alert.setHeaderText("Bill has been generated in the res folder named by Bill Id.");
                alert.showAndWait();

                // Increase the total billed amount by the current bill amount
                total += totalPrice;

                // Update the total billed amount for the librarian if the user is a librarian
                if (getUser() instanceof Librarian) {
                    for (Person person1: Controller.people) {
                        if (person1 instanceof Librarian) {
                            if (person1.equals(getUser())) {
                                ((Librarian) person1).setTotalBilled(((Librarian) getUser()).getTotalBilled() + totalPrice);
                            }
                        }
                    }
                    ((Librarian) getUser()).setTotalBilled(((Librarian) getUser()).getTotalBilled() + totalPrice);
                }

                // Clear the books in the bill and refresh the table
                books1.clear();
                table.refresh();

                // Reset the total price and bill id label
                totalPrice = 0;
                totalPriceLabel.setText("Total: $" + totalPrice);
                billIdLabel.setText(("Bill ID: " + ++billId));
            } else {
                // Show an error alert to the user if the bill is empty
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("An error has occurred");
                alert.setContentText("Bill is Empty. Kindly add books in the bill first.");
                alert.showAndWait();
            }
        });
        addBookButton.setPrefSize(100, 20);
        totalPriceLabel.setFont(Font.font("Tahoma", FontWeight.BOLD,15));
        addBookButton.setOnAction(event -> bookTable(books));
        setCenter(billTable());
        billIdLabel = new Label("Bill ID: " + ++billId);
        billIdLabel.setFont(Font.font("Tahoma", FontWeight.BOLD,15));
        GridPane bottomBox = new GridPane();
        bottomBox.setPadding(new Insets(15, 12, 15, 12));
        bottomBox.setVgap(20);
        bottomBox.add(billIdLabel,0,0);
        bottomBox.add(totalPriceLabel,0,1);
        bottomBox.add(addBookButton,0,2);
        bottomBox.add(generateBillButton,0,3);
        bottomBox.setAlignment(Pos.CENTER);
        setRight(bottomBox);
    }

    private TableView<Book> billTable(){
        // Create a TableColumn object for ISBN
        TableColumn<Book, String> isbnColumn = new TableColumn<>("ISBN");
        // Bind the ISBN value from the Book object to the ISBN column
        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("isbn"));

        // Create a TableColumn object for Title
        TableColumn<Book, String> titleColumn = new TableColumn<>("Title");
        // Bind the Title value from the Book object to the Title column
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        // Create a TableColumn object for Category
        TableColumn<Book, String> categoryColumn = new TableColumn<>("Category");
        // Bind the Category value from the Book object to the Category column
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));

        // Create a TableColumn object for Sell Price
        TableColumn<Book, Double> sellPriceColumn = new TableColumn<>("Sell Price");
        // Bind the Sell Price value from the Book object to the Sell Price column
        sellPriceColumn.setCellValueFactory(new PropertyValueFactory<>("sellPrice"));

        // Create a TableColumn object for Quantity
        TableColumn<Book, Void> quantityColumn = new TableColumn<>("Quantity");
        // Define a callback function to create cells for the Quantity column
        Callback<TableColumn<Book, Void>, TableCell<Book, Void>> cellFactory1 = new Callback<>() {
            @Override
            public TableCell<Book, Void> call(final TableColumn<Book, Void> param) {
                return new TableCell<>() {
                    // Override the updateItem method
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            // Set the text of the cell to the value of quantity
                            setText(String.valueOf(quantity.get()));
                        }
                    }
                };
            }
        };
        // Set the cell factory for the Quantity column
        quantityColumn.setCellFactory(cellFactory1);

        // Add all columns to the table
        table.getColumns().addAll(isbnColumn, titleColumn, categoryColumn, sellPriceColumn, quantityColumn);
        // Set the items of the table to books1
        table.setItems(books1);
        return table;
    }


    private void bookTable(ObservableList<Book> books){
        Stage bookStage = new Stage();
        bookStage.initModality(Modality.APPLICATION_MODAL);
        bookStage.setTitle("Add Book");
        bookStage.setMinWidth(250);
        Label label = new Label();
        label.setText("Select a book to add to the bill:");

        TableView<Book> bookTable = new TableView<>();
        bookTable.setItems(books);

        TableColumn<Book, String> isbnColumn = new TableColumn<>("ISBN");
        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        TableColumn<Book, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        TableColumn<Book, String> categoryColumn = new TableColumn<>("Category");
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        TableColumn<Book, Double> sellPriceColumn = new TableColumn<>("Sell Price");
        sellPriceColumn.setCellValueFactory(new PropertyValueFactory<>("sellPrice"));
        TableColumn<Book, Integer> stockColumn = new TableColumn<>("Stock");
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));

        bookTable.getColumns().addAll(isbnColumn, titleColumn, categoryColumn, sellPriceColumn, stockColumn);

        // Handle the double-click event on a row in the book table
        bookTable.setOnMouseClicked(e -> {
            // Check if the event is a double-click
            if (e.getClickCount() == 2) {
                // Get the selected book from the table
                Book selectedBook = bookTable.getSelectionModel().getSelectedItem();
                // Check if the selected book is in stock
                if (selectedBook.getStock() > 0) {
                    // Show a text input dialog to enter the quantity of books to add
                    TextInputDialog dialog = new TextInputDialog("1");
                    dialog.setTitle("Enter Quantity");
                    dialog.setHeaderText("Enter the number of books you want to add:");
                    dialog.setContentText("Quantity:");
                    Optional<String> result = dialog.showAndWait();
                    // If the user enters a value
                    if (result.isPresent()) {
                        // Update the quantity of books to add
                        quantity.set(Integer.parseInt(result.get()));
                        // Keep track of the total number of books sold
                        booksSold += quantity.get();
                        // Check if the entered quantity is valid (between 1 and the stock of the selected book)
                        if (quantity.get() > 0 && quantity.get() <= selectedBook.getStock()) {
                            // Update the total price
                            totalPrice += selectedBook.getSellPrice() * quantity.get();
                            totalPriceLabel.setText("Total: $" + totalPrice);
                            // Decrease the stock of the selected book
                            for (Book book: books) {
                                if (book.equals(selectedBook)){
                                    book.setStock(book.getStock()- quantity.get());
                                }
                            }
                            // Add the selected book to the bill
                            books1.add(selectedBook);
                            // Close the book stage
                            bookStage.fireEvent(new WindowEvent(bookStage, WindowEvent.WINDOW_CLOSE_REQUEST));
                            // Close the text input dialog
                            dialog.close();
                        } else {
                            // Show an error message if the entered quantity is invalid
                            label.setText("Invalid quantity. Please enter a value between 1 and " + selectedBook.getStock());
                        }
                    }
                } else {
                    // Show an error message if the selected book is out of stock
                    label.setText("The selected book is out of stock. Please select another book.");
                }
            }
        });


        BorderPane bookPane = new BorderPane();
        bookPane.setTop(label);
        bookPane.setCenter(bookTable);

        Scene bookScene = new Scene(bookPane, 400, 400);
        bookStage.setScene(bookScene);
        bookStage.showAndWait();
    }

    public Person getUser() {
        return person;
    }

    public void setUser(Person user) {
        this.person = user;
    }
}
