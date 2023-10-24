package application.tryingpr.View;


import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;
import application.tryingpr.helperClasses.Role;

public class LoginPage extends GridPane {

    public Button btn = new Button("Sign In");
    public TextField userTextField;
    public PasswordField pwBox;
    public LoginPage() {
        Background background = new Background(new BackgroundFill(new LinearGradient(
                0, 0, 1, 1, true,                      //sizing
                CycleMethod.REPEAT,                  //cycling
                new Stop(0, Color.web("#FFFFFF")),
                new Stop(1, Color.web("#6C6C6CFF")))   //colors
                ,null,null));

        setBackground(background);
        setAlignment(Pos.CENTER);
        setHgap(10);
        setVgap(10);
        setPadding(new Insets(25, 25, 25, 25));

        Text sceneTitle = new Text("Welcome");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.EXTRA_BOLD, 20));
        sceneTitle.setFill(Color.rgb(80, 80, 80));
        add(sceneTitle, 0, 0, 2, 1);

        userTextField = new TextField();
        userTextField.setPrefWidth(250);
        userTextField.setPromptText("Username");
        userTextField.setFocusTraversable(false);
        userTextField.setStyle("-fx-background-radius: 5, 5, 4, 3;" +
                "-fx-background-color: linear-gradient(to bottom, derive(#989898,60%) 5%, derive(#989898,40%) 100%), linear-gradient(to bottom, #E6E6E6 0%, #FFFFFF 100%);" +
                "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );");
        add(userTextField, 0, 1);

        pwBox = new PasswordField();
        pwBox.setPromptText("Password");
        pwBox.setPrefWidth(250);
        pwBox.setFocusTraversable(false);
        pwBox.setStyle("-fx-background-radius: 5, 5, 4, 3;" +
                "-fx-background-color: linear-gradient(to bottom, derive(#989898,60%) 5%, derive(#989898,40%) 100%), linear-gradient(to bottom, #E6E6E6 0%, #FFFFFF 100%);" +
                "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );");
        add(pwBox,0, 2);
        btn.setCursor(Cursor.HAND);
        btn.setPrefWidth(100);
        btn.setDefaultButton(true);

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
        btn.styleProperty().bind(cssColorSpec);

        final Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(color, startColor)),
                new KeyFrame(Duration.seconds(1), new KeyValue(color, endColor)));

        btn.setOnAction(event -> timeline.play());

        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        add(hbBtn, 0, 3);
        setPrefSize(700,475);
    }
}