package ohtu.cucumberTest;

import cucumber.api.java.en.Given;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import ohtu.database.Database;
import ohtu.io.StubIO;
import ohtu.main.App;
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

    protected void runAndExit() {
        inputs.add("E");
        io = new StubIO(inputs);
        App app = new App(io, db);
        app.run();
    }
    
    protected void addNewBlogpost(String title,String author,String url){
        inputs.add("A");
        inputs.add("B");
        inputs.add(title);
        inputs.add(author);
        inputs.add(url);
    }
    
    protected void addNewVideo(String title,String url){
        inputs.add("A");
        inputs.add("V");
        inputs.add(title);
        inputs.add(url);
    }

}
