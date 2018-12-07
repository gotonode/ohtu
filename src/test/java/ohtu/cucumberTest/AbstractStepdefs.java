package ohtu.cucumberTest;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ohtu.database.Database;
import ohtu.io.StubIO;
import ohtu.main.App;
import ohtu.user.UserController;
import org.junit.rules.TemporaryFolder;

/**
 * An abstract class including methods of initialisation before each scenario
 * and some other methods that are commonly used in cucumber tests
 */
public abstract class AbstractStepdefs{
    
    private TemporaryFolder tempFolder;
    protected static StubIO io;
    protected static Database db = null;
    protected static List<String> inputs;

    protected void initialize() throws IOException {

        if (tempFolder == null) {
            tempFolder = new TemporaryFolder();
            tempFolder.create();
            File databaseFile = new File(tempFolder.getRoot() + "/test.db");
            db = new Database(databaseFile);
            inputs = new ArrayList<>();
        }
    }

    protected void runAndExit() {
        inputs.add("E");
        io = new StubIO(inputs);
        UserController.setLogInStatusAsFalse();
        App app = new App(io, db, true);
        app.run();
    }

    void addNewBlogpost(String title, String author, String url) {
        inputs.add("A");
        inputs.add("B");
        inputs.add(title);
        inputs.add(author);
        inputs.add(url);
    }

    void addNewVideo(String title, String url) {
        inputs.add("A");
        inputs.add("V");
        inputs.add(title);
        inputs.add(url);
    }

    void addNewBook(String title, String author, String isbn) {
        inputs.add("A");
        inputs.add("K");
        inputs.add(title);
        inputs.add(author);
        inputs.add(isbn);
    }
}
