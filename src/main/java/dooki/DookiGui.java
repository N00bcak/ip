package dooki;

import java.io.IOException;

import display.MainWindow;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Sets up and launches the JavaFX UI.
 */
public class DookiGui extends Application {
    private final DookiBot dookiBot = new DookiBot();

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(DookiGui.class.getResource("/view/MainWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        MainWindow controller = fxmlLoader.getController();
        controller.setDooki(dookiBot);

        stage.setTitle("Dooki");
        stage.setScene(scene);
        stage.show();
    }
}
