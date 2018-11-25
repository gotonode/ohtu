package ohtu.cucumberTest;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import ohtu.database.Database;
import ohtu.io.StubIO;
import org.junit.rules.TemporaryFolder;

public class AbstractStepdefs {

    protected TemporaryFolder tempFolder;
    protected StubIO io;
    protected Database db = null;
    protected List<String> inputs;

    protected void initialize() throws IOException {

        if (tempFolder == null) {
            tempFolder = new TemporaryFolder();
            tempFolder.create();
            File databaseFile = new File(tempFolder.getRoot() + "/test.db");
            db = new Database(databaseFile);
            inputs = new ArrayList<>();
        }

    }

}
