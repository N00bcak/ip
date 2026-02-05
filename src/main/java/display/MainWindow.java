package display;

import dooki.DookiBot;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * Controller for the main chat window.
 */
public class MainWindow {
    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox dialogContainer;

    @FXML
    private TextField userInput;

    @FXML
    private Button sendButton;

    private DookiBot dooki;

    @FXML
    private void initialize() {
        this.scrollPane.vvalueProperty().bind(this.dialogContainer.heightProperty());
    }

    /**
     * Injects the chatbot instance and shows the welcome message.
     *
     * @param dookiBot chatbot instance.
     */
    public void setDooki(DookiBot dookiBot) {
        this.dooki = dookiBot;
        this.dialogContainer.getChildren().add(DialogBox.getDookiDialog(this.dooki.getWelcomeMessage()));
    }

    /**
     * Handles user input from the text field or send button.
     */
    @FXML
    private void handleUserInput() {
        String input = this.userInput.getText();
        if (input == null || input.isBlank()) {
            return;
        }
        String response = this.dooki.getResponse(input);
        this.dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input),
                DialogBox.getDookiDialog(response)
        );
        this.userInput.clear();

        if (this.dooki.shouldExit()) {
            this.sendButton.setDisable(true);
            this.userInput.setDisable(true);
            Platform.exit();
        }
    }
}
