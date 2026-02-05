package display;

/**
 * Minimal text-only renderer for CLI output. Responsible only for formatting lines; all message content
 * lives in the backend (e.g., {@code DookiBot}).
 */

public class UiText {
    // Adapted output format from https://nus-cs2103-ay2526-s2.github.io/website/schedule/week2/project.html
    private static final String LONG_LINE = "_".repeat(30);

    /**
     * Constructor for Dooki's text renderer.
     */
    public UiText() {
    }

    /**
     * Prints an arbitrary message wrapped with separator lines.
     * @param message message to print.
     */
    public void showPlain(String message) {
        this.showLine();
        System.out.println(message);
        this.showLine();
    }

    private void showLine() {
        System.out.println(LONG_LINE);
    }

}
