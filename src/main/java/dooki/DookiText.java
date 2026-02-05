package dooki;

import java.util.Scanner;

import display.UiText;

/**
 * Text-based entrypoint to chatting with Dooki. Delegates all command processing to DookiBot.
 */

public class DookiText {
    private final DookiBot dookiBot;
    private final Scanner sc;
    private final UiText ui;

    /**
     * Constructor for Dooki's text UI.
     */
    public DookiText() {
        this.dookiBot = new DookiBot();
        this.sc = new Scanner(System.in);
        this.ui = new UiText();
    }

    /**
     * Starts the text UI loop and renders responses from DookiBot.
     */
    public void start() {
        this.ui.showPlain(this.dookiBot.getWelcomeMessage());
        while (true) {
            if (!this.sc.hasNextLine()) {
                break;
            }
            String inp = this.sc.nextLine();
            String response = this.dookiBot.getResponse(inp);
            this.ui.showPlain(response);
            if (this.dookiBot.shouldExit()) {
                break;
            }
        }
    }

    /**
     * Entrypoint for all Java programs
     * @param args Java CLI args (unused)
     */
    public static void main(String[] args) {
        DookiText dooki = new DookiText();
        dooki.start();
    }
}
