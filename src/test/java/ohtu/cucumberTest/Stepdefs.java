package ohtu.cucumberTest;

import ohtu.io.StubIO;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import java.io.File;
import java.util.*;
import ohtu.database.Database;
import ohtu.main.App;
import static org.junit.Assert.assertTrue;

public class Stepdefs {

    StubIO io;
    App app;
    File databaseFile = new File(System.getProperty("user.dir") + "/test.db");
    Database db = new Database(databaseFile);
    List<String> inputs = new ArrayList<>();

    @Given("^command adding a blogpost is selected$")
    public void command_A_selected() throws Throwable {
        inputs.add("A");
    }

    @When("^title \"([^\"]*)\" and author \"([^\"]*)\" and url \"([^\"]*)\" are entered$")
    public void title_and_author_and_url_are_entered(String title, String author, String url) throws Throwable {
        inputs.add(title);
        inputs.add(author);
        inputs.add(url);
        inputs.add("E");
        io = new StubIO(inputs);
        app = new App(io, db);
        app.run();
    }

    @When("^title is empty$")
    public void title_is_empty() throws Throwable {
        inputs.add("");
        inputs.add("title1");
        inputs.add("author1");
        inputs.add("url1");
        inputs.add("E");
        io = new StubIO(inputs);
        app = new App(io, db);
        app.run();
    }

    @Then("^system will respond with \"([^\"]*)\"$")
    public void system_will_respond_with(String expectedOutput) throws Throwable {
        assertTrue(io.getPrints().contains(expectedOutput));
    }

    @Then("^system will ask the user to enter something by responding with \"([^\"]*)\"$")
    public void system_will_ask_the_user_to_enter_something_by_responding_with(String expectedOutput) throws Throwable {
        assertTrue(io.getPrints().contains(expectedOutput));
    }

    @Given("^command listing all bookmarks is selected$")
    public void command_L_is_selected() throws Throwable {
        inputs.add("L");
        inputs.add("E");
        io = new StubIO(inputs);
        app = new App(io, db);
        app.run();
    }

    @Then("^system will start to list all bookmarks and respond with \"([^\"]*)\"$")
    public void system_will_start_to_list_all_bookmarks_and_respond_with(String expectedOutput) throws Throwable {
        assertTrue(io.getPrints().contains(expectedOutput));
    }

}
