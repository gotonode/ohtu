package ohtu.cucumberTest;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
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
public abstract class AbstractStepdefs {

    private TemporaryFolder tempFolder;
    protected static StubIO io;
    protected static Database db = null;
    protected static List<String> inputs;

    /**
     * Creates a temporary folder where the database which will be used in tests
     * is located, also initialise database here. This method will be used
     * before every scenario
     *
     * @throws IOException
     */
    protected void initialize() throws IOException {

        if (tempFolder == null) {
            tempFolder = new TemporaryFolder();
            tempFolder.create();
            File databaseFile = new File(tempFolder.getRoot() + "/test.db");
            db = new Database(databaseFile);
            inputs = new ArrayList<>();
        }
    }

    protected void runAndExit() throws SQLException {
        inputs.add("E");
        io = new StubIO(inputs);
        UserController.setLogInStatusAsFalse();
        App app = new App(io, db, true);
        app.run();
    }

}
