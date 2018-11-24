package ohtu.main;

import java.io.File;

import ohtu.io.ConsoleIO;

import java.util.Scanner;
import java.util.logging.Logger;

import ohtu.database.Database;

/**
 * The program starts here. Program logic is handled in App-object. This class is ignored in code coverage.
 */
public class Main {

    // Use the logger for error messages and other such important topics.
    public static final Logger LOG = Logger.getLogger(Main.class.getName());

    // Declare final variables here.
    public static final String APP_NAME = "Bookmarks Database";

    private static App app;

    public static void main(String[] args) {

            File databaseFile = new File(System.getProperty("user.dir") + "/bookmarks.db");
            Database db = new Database(databaseFile);

            Scanner scanner = new Scanner(System.in);

            ConsoleIO consoleIo = new ConsoleIO(scanner);

            app = new App(consoleIo, db); // Dependency injection. For tests, pass a StubIO.
            app.run(); // Run the app. It contains a loop, and once it breaks, the app will terminate.
    }
}
