package ohtu.main;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import ohtu.io.ConsoleIO;
import java.util.Scanner;
import java.util.logging.Logger;

import ohtu.database.Database;

/**
 * The program starts here. Program logic is handled in App-object. This class is ignored in code coverage.
 */
public class Main {

	public static final Logger LOG = Logger.getLogger(Main.class.getName());

	// Declare final variables here.
	public static final String APP_NAME = "Bookmarks Database";

	// Only enable the following temporarily if needed, and then disable it before committing.
	public static final boolean DEBUG = false; // Will cause StubIO to print its contents, useful for tests.

	private static App app;

    public static void main(String[] args) throws ParseException {
   
        File databaseFile = new File(System.getProperty("user.dir") + "/bookmarks.db");
        Database db = new Database(databaseFile);

    	if (DEBUG) {
			Main.LOG.info("# App started in DEBUG mode");
		}

    	Scanner scanner = new Scanner(System.in);

    	ConsoleIO consoleIo = new ConsoleIO(scanner);

    	app = new App(consoleIo, db); // Dependency injection. For tests, pass a StubIO.
		app.run(); // Run the app. It contains a loop, and once it breaks, the app will terminate.
    }
}
