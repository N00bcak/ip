package display;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

/**
 * A single message bubble in the chat window.
 */
public class DialogBox extends HBox {
    private DialogBox(String message, boolean isUser) {
        Label label = new Label(message);
        label.setWrapText(true);
        label.setPadding(new Insets(10));
        label.setBackground(new Background(new BackgroundFill(
                isUser ? Color.LIGHTBLUE : Color.LIGHTGRAY,
                new CornerRadii(8),
                Insets.EMPTY
        )));

        this.setAlignment(isUser ? Pos.TOP_RIGHT : Pos.TOP_LEFT);
        this.setSpacing(10);
        this.getChildren().add(label);
    }

    /**
     * Creates a dialog box for user messages.
     *
     * @param message text to display.
     * @return dialog box.
     */
    public static DialogBox getUserDialog(String message) {
        return new DialogBox(message, true);
    }

    /**
     * Creates a dialog box for chatbot messages.
     *
     * @param message text to display.
     * @return dialog box.
     */
    public static DialogBox getDookiDialog(String message) {
        return new DialogBox(message, false);
    }
}
