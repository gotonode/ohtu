package ohtu.main;

import ohtu.io.ConsoleIO;

import java.util.Scanner;

/**
 * The program starts here. Program logic is handled in App-object. This class is ignored in code coverage.
 */
public class Main {

	// Declare final variables here.
	public static final String APP_NAME = "Bookmarks Database";
	public static final boolean DEBUG = true; // Will cause StubIO to print its contents, useful for tests.

	private static App app;

    public static void main(String[] args) {

    	if (DEBUG) {
			System.out.println("# App started in DEBUG mode");
		}

    	Scanner scanner = new Scanner(System.in);

    	ConsoleIO consoleIo = new ConsoleIO(scanner);

    	app = new App(consoleIo); // Dependency injection. For tests, pass a StubIO.
		app.run(); // Run the app. It contains a loop, and once it breaks, the app will terminate.
    }
}
