package FXTesting;

import application.tryingpr.Controllers.Controller;
import javafx.stage.Stage;
import application.tryingpr.UI.mainApplication;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MainApplicationTest extends ApplicationTest {

    @Override
    public void start(Stage stage) {
        new mainApplication().start(stage);
    }
    @Test
    public void should_perform_close_actions() throws TimeoutException {

        FxToolkit.hideStage();
        FxToolkit.cleanupStages();

        assertTrue(Files.exists(Path.of("res/bookInfo.txt")));
        assertTrue(Files.exists(Path.of("res/PEOPLE1.txt")));
        assertTrue(Files.exists(Path.of("res/ROLES.txt")));
        assertTrue(Files.exists(Path.of("res/totalBill1.bin")));
        assertTrue(Files.exists(Path.of("res/totalCost1.bin")));
        assertTrue(Files.exists(Path.of("res/booksSold1.bin")));


        assertTrue(Controller.people.isEmpty());
        Assertions.assertEquals(0, Controller.totalCost);
        Assertions.assertNotEquals(0, Controller.totalBill);

    }

}
