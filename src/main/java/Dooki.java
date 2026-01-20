import java.util.Scanner;

/**
 * Entrypoint to chatting with Dooki.
 */

public class Dooki {
    // Adapted output format from https://nus-cs2103-ay2526-s2.github.io/website/schedule/week2/project.html
    private static final String LONG_LINE = "_".repeat(30);

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        greetUser();
        while (scanner.hasNextLine()) {
            String inp = scanner.nextLine();
            /*
            Technically it was never specified whether non-lower-case
            would be accepted or not. So...
             */
            if (inp.equals("bye")) {
                sayGoodbye();
                break;
            }
            saySomething(inp);
        }
    }

    private static void greetUser() {
        System.out.println(LONG_LINE);
        System.out.println("Hello! I'm Dooki");
        System.out.println("What can I do for you?");
        System.out.println(LONG_LINE);
    }

    private static void saySomething(String msg) {
        System.out.println(LONG_LINE);
        System.out.println(msg);
        System.out.println(LONG_LINE);
    }

    private static void sayGoodbye() {
        saySomething("Bye. Hope to see you again soon!");
    }

}
