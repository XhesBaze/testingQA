package application.tryingpr.View;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Callback;
import javafx.util.Duration;
import application.tryingpr.Controllers.Controller;
import application.tryingpr.Models.*;
import application.tryingpr.helperClasses.Role;
import application.tryingpr.helperClasses.writingToFiles;

@SuppressWarnings("ALL")
public class ManagerPanel extends BorderPane {
    MenuBar bar;
    public BooksView view = new BooksView(Controller.books);
    Background background;
    public ManagerPanel() {

        ObservableList<Librarian> librarians = FXCollections.observableArrayList();

        for (Person person: Controller.people){
            if (person instanceof Librarian){
                librarians.add((Librarian) person);
            }
        }

        background = new Background(new BackgroundFill(new LinearGradient(
                0, 0, 1, 1, true,                      //sizing
                CycleMethod.REPEAT,                  //cycling
                new Stop(0, Color.web("#FFFFFF")),
                new Stop(1, Color.web("#6C6C6CFF")))   //colors
                ,null,null));

        setBackground(background);

        setCenter(homePage());

        Menu men = new Menu("Home");
        men.setStyle("-fx-font-size: 9pt; -fx-font-family: 'Arial'; -fx-font-weight: bold;");
        MenuItem men1 = new MenuItem("Home");
        men1.setOnAction(actionEvent -> {
            setCenter(homePage());
        });
        men.getItems().add(men1);

        Menu menu1 = new Menu("Books");
        menu1.setStyle("-fx-font-size: 9pt; -fx-font-family: 'Arial'; -fx-font-weight: bold;");
        MenuItem item2 = new MenuItem("New Book");
        item2.setOnAction(event -> {
            // Perform action for Menu Item 1
            setCenter(addNewBook());
        });
        MenuItem item3 = new MenuItem("Manage Books");
        item3.setOnAction(event -> {
            setCenter(view);
        });

        Menu menu2 = new Menu("Stats");
        menu2.setStyle("-fx-font-size: 9pt; -fx-font-family: 'Arial'; -fx-font-weight: bold;");
        MenuItem item4 = new MenuItem("Stats");
        item4.setOnAction(actionEvent -> {
            setCenter(statsPanel(librarians));
        });
        menu1.getItems().addAll(item2,item3);
        menu2.getItems().addAll(item4);

        bar = new MenuBar();
        bar.setCursor(Cursor.HAND);
        bar.getMenus().addAll(men,menu1,menu2);
        bar.setStyle("-fx-background-color: lightgray;");
        VBox vBox = new VBox(bar);
        setTop(vBox);
    }
    private GridPane addNewBook(){
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.setVgap(8);
        grid.setHgap(10);
        grid.setAlignment(Pos.CENTER);
        grid.setBackground(background);

        // ISBN of the book
        Label isbnLabel = new Label("ISBN:");
        GridPane.setConstraints(isbnLabel, 0, 0);

        TextField isbnInput = new TextField();
        GridPane.setConstraints(isbnInput, 1, 0);

        // Title of the book
        Label titleLabel = new Label("Title:");
        GridPane.setConstraints(titleLabel, 0, 1);

        TextField titleInput = new TextField();
        GridPane.setConstraints(titleInput, 1, 1);

        // Category of the book
        Label categoryLabel = new Label("Category:");
        GridPane.setConstraints(categoryLabel, 0, 2);

        TextField categoryInput = new TextField();
        GridPane.setConstraints(categoryInput, 1, 2);

        // Supplier
        Label supplierLabel = new Label("Supplier:");
        GridPane.setConstraints(supplierLabel, 0, 3);

        TextField supplierInput = new TextField();
        GridPane.setConstraints(supplierInput, 1, 3);

        // Purchased date
        Label purchasedDateLabel = new Label("Purchased Date:");
        GridPane.setConstraints(purchasedDateLabel, 0, 4);

        DatePicker purchasedDateInput = new DatePicker();
        GridPane.setConstraints(purchasedDateInput, 1, 4);

        // Purchased price
        Label purchasedPriceLabel = new Label("Purchased Price:");
        GridPane.setConstraints(purchasedPriceLabel, 0, 5);

        TextField purchasedPriceInput = new TextField();
        GridPane.setConstraints(purchasedPriceInput, 1, 5);

        // Original price
        Label originalPriceLabel = new Label("Original Price:");
        GridPane.setConstraints(originalPriceLabel, 0, 6);
        TextField originalPriceInput = new TextField();
        GridPane.setConstraints(originalPriceInput, 1, 6);

        // Selling price
        Label sellingPriceLabel = new Label("Selling Price:");
        GridPane.setConstraints(sellingPriceLabel, 0, 7);

        TextField sellingPriceInput = new TextField();
        GridPane.setConstraints(sellingPriceInput, 1, 7);

        // Author
        Label authorLabel = new Label("Author:");
        GridPane.setConstraints(authorLabel, 0, 8);

        TextField authorInput = new TextField();
        GridPane.setConstraints(authorInput, 1, 8);

        // Stock
        Label stockLabel = new Label("Stock:");
        GridPane.setConstraints(stockLabel, 0, 9);

        TextField stockInput = new TextField();
        GridPane.setConstraints(stockInput, 1, 9);

        Button submitButton = new Button("Submit");
        final Color startColor = Color.web("#6C6C6CFF");
        final Color endColor = Color.web("#FFFFFF");

        final ObjectProperty<Color> color = new SimpleObjectProperty<>(Color.web("#FFFFFF"));

        // String that represents the color above as a JavaFX CSS function:
        // -fx-body-color: rgb(r, g, b);
        // with r, g, b integers between 0 and 255
        final StringBinding cssColorSpec = Bindings.createStringBinding(() -> String.format("-fx-body-color: rgb(%d, %d, %d);",
                (int) (256*color.get().getRed()),
                (int) (256*color.get().getGreen()),
                (int) (256*color.get().getBlue())), color);

        // bind the button's style property
        submitButton.styleProperty().bind(cssColorSpec);
        submitButton.setCursor(Cursor.HAND);

        final Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(color, startColor)),
                new KeyFrame(Duration.seconds(1), new KeyValue(color, endColor)));
        GridPane.setConstraints(submitButton, 1, 10);
        submitButton.setOnAction(actionEvent -> {
            timeline.play();
            if (!(isbnInput.getText().isEmpty() || titleInput.getText().isEmpty() || purchasedPriceInput.getText().isEmpty() ||
                    sellingPriceInput.getText().isEmpty() || originalPriceInput.getText().isEmpty() || authorInput.getText().isEmpty() ||
                    categoryInput.getText().isEmpty() || supplierInput.getText().isEmpty() || stockInput.getText().isEmpty() || purchasedDateInput.isPressed())) {
                try {
                    Controller.books.add(new Book(isbnInput.getText(), titleInput.getText(),
                            Double.parseDouble(purchasedPriceInput.getText()), Double.parseDouble(originalPriceInput.getText()),
                            Double.parseDouble(sellingPriceInput.getText()), authorInput.getText(), categoryInput.getText(),
                            supplierInput.getText(), Integer.parseInt(stockInput.getText()), purchasedDateInput.getValue()));
                    Controller.totalCost += Integer.parseInt(stockInput.getText()) * Double.parseDouble(purchasedPriceInput.getText());
                    isbnInput.setText("");
                    titleInput.setText("");
                    purchasedPriceInput.setText("");
                    sellingPriceInput.setText("");
                    originalPriceInput.setText("");
                    authorInput.setText("");
                    categoryInput.setText("");
                    supplierInput.setText("");
                    stockInput.setText("");
                    purchasedDateInput.setValue(null);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Successful");
                    alert.setContentText("Book is added successfully!");
                    alert.showAndWait();
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Please make sure you don't enter any character in prices and stock fields");
                    alert.showAndWait();
                }
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("There is some error, please confirm all fields are filled!");
                alert.showAndWait();
            }
        });

        grid.getChildren().addAll(isbnLabel, isbnInput, titleLabel, titleInput, categoryLabel, categoryInput, supplierLabel,
                supplierInput, purchasedDateLabel, purchasedDateInput, purchasedPriceLabel, purchasedPriceInput,
                originalPriceLabel, originalPriceInput, sellingPriceLabel, sellingPriceInput, authorLabel, authorInput,
                stockLabel, stockInput, submitButton);
        return grid;
    }

    private VBox statsPanel(ObservableList<Librarian> librarians){

        VBox vBox = new VBox();

        TableView<Librarian> tableView = new TableView<>();

        TableColumn<Librarian, String> ISBNColumn = new TableColumn<>("Name");
        ISBNColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Librarian, String> titleColumn = new TableColumn<>("Username");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));

        TableColumn<Librarian, Role> roleColumn = new TableColumn<>("Role");
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));

        TableColumn<Librarian, Integer> totalBilledColumn = new TableColumn<>("Total Billed");
        totalBilledColumn.setCellValueFactory(new PropertyValueFactory<>("totalBilled"));


        tableView.getColumns().addAll(ISBNColumn, titleColumn, roleColumn, totalBilledColumn);
        tableView.setItems(librarians);

        Label lblHeading = new Label("List of Librarians");
        lblHeading.setStyle("-fx-font-size: 20px;");
        vBox.getChildren().addAll(lblHeading,tableView);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(10));
        return vBox;
    }

    private GridPane homePage(){
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Label welcomeLabel = new Label("Welcome to Manager Panel");
        welcomeLabel.setFont(Font.font("Tahoma",FontWeight.EXTRA_BOLD,22));
        grid.add(welcomeLabel,0,0);

        // Add labels and values for books sold, librarians, managers, and bill
        Label librarians = new Label("Number of Books Sold: ");
        librarians.setFont(Font.font("Tahoma", FontWeight.BOLD, 14));
        Label librariansValue = new Label(String.valueOf(writingToFiles.getBooksSold()));
        librariansValue.setFont(Font.font("Tahoma", FontWeight.BOLD, 14));
        Label managers = new Label("Total Earning: ");
        managers.setFont(Font.font("Tahoma", FontWeight.BOLD, 14));
        Label managersValue = new Label("$" + writingToFiles.getTotalBill());
        managersValue.setFont(Font.font("Tahoma", FontWeight.BOLD, 14));

        // Add the labels to the grid
        grid.add(librarians, 0, 1);
        grid.add(librariansValue, 1, 1);
        grid.add(managers, 0, 2);
        grid.add(managersValue, 1, 2);
        return grid;
    }

}
