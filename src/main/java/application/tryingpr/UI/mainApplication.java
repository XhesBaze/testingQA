package application.tryingpr.UI;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import application.tryingpr.Controllers.Controller;
import application.tryingpr.Models.Librarian;
import application.tryingpr.Models.Person;
import application.tryingpr.helperClasses.writingToFiles;
import application.tryingpr.View.*;

public class mainApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Login"); // Set the title of the stage to "Login"
        LoginPage page = new LoginPage(); // Create an instance of the LoginPage class
        AdminPanel panel = new AdminPanel(); // Create an instance of the AdminPanel class
        ManagerPanel panel1 = new ManagerPanel(); // Create an instance of the ManagerPanel class
        LibrarianPanel panel2 = new LibrarianPanel(); // Create an instance of the LibrarianPanel class
        Scene scene = new Scene(page); // Create a new Scene object and set its root to the LoginPage object
        page.btn.setOnAction(new EventHandler<ActionEvent>() { // Add an action to the button on the LoginPage
            @Override
            public void handle(ActionEvent actionEvent) {
                String username = page.userTextField.getText(); // Get the entered username
                String password = page.pwBox.getText(); // Get the entered password
                String Role = writingToFiles.readCredentials(username, password); // Get the user's role based on the entered credentials
                if (Role.equalsIgnoreCase("Administrator")) {
                    scene.setRoot(panel); // Set the scene's root to the AdminPanel if the user is an Administrator
                } else if (Role.equalsIgnoreCase("Manager")) {
                    scene.setRoot(panel1); // Set the scene's root to the ManagerPanel if the user is a Manager
                } else if (Role.equalsIgnoreCase("Librarian")) {
                    for (Person person : Controller.people) { // Loop through the list of people
                        if (person instanceof Librarian) { // If the person is a Librarian
                            if (person.getUserName().equalsIgnoreCase(username) && person.getPassword().equalsIgnoreCase(password)) {
                                panel2.setPerson(person); // Set the Librarian object for the LibrarianPanel
                            }
                        }
                    }
                    scene.setRoot(panel2); // Set the scene's root to the LibrarianPanel if the user is a Librarian
                }
            }
        });
        primaryStage.setScene(scene); // Set the stage's scene to the Scene object created earlier
        primaryStage.setOnCloseRequest(event -> {
            // Write the data to files when the application is closed
            writingToFiles.writeBooks();
            writingToFiles.writePersons();
            writingToFiles.writeRoles();
            writingToFiles.writeTotalBill(BillView.total);
            writingToFiles.writeTotalCost(Controller.totalCost);
            writingToFiles.writeBooksSold(BillView.booksSold);
        });
        primaryStage.show(); // Show the stage
    }
}

