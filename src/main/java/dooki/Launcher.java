package dooki;

import javafx.application.Application;

/**
 * Main entry point to launch either the GUI or text UI.
 */
public class Launcher {
    /**
     * Launches Dooki. Use first CLI argument "text" to run the CLI; otherwise starts the GUI.
     * @param args command line arguments.
     */
    public static void main(String[] args) {
        if (args.length > 0 && "text".equalsIgnoreCase(args[0])) {
            new DookiText().start();
            return;
        }
        Application.launch(DookiGui.class, args);
    }
}
