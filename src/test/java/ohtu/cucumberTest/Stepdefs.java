package ohtu.cucumberTest;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import ohtu.database.Database;
import ohtu.io.StubIO;
import ohtu.main.App;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class Stepdefs {

    private TemporaryFolder tempFolder;
    private StubIO io;
    private Database db = null;
    private List<String> inputs;

    @Before
    public void before() throws IOException {
        if (tempFolder == null) {
            tempFolder = new TemporaryFolder();
            tempFolder.create();
            File databaseFile = new File(tempFolder.getRoot() + "/test.db");
            db = new Database(databaseFile);
            inputs = new ArrayList<>();
        }

        inputs.clear(); // Maybe it's faster to just clear this than to re-initialize?
    }

    @Given("^command adding a blogpost is selected$")
    public void command_A_selected() throws Throwable {
        inputs.add("A");
    }

    @When("^title \"([^\"]*)\" and author \"([^\"]*)\" and url \"([^\"]*)\" are entered$")
    public void title_and_author_and_url_are_entered(String title, String author, String url) throws Throwable {
        inputs.add(title);
        inputs.add(author);
        inputs.add(url);
    }

    @When("^title is empty$")
    public void title_is_empty() throws Throwable {
        inputs.add("");
        inputs.add("title1");
        inputs.add("author1");
        inputs.add("url1");
    }

    @Then("^system will respond with \"([^\"]*)\"$")
    public void system_will_respond_with(String expectedOutput) throws Throwable {
        runAndExit();
        assertTrue(io.getPrints().contains(expectedOutput));
    }

    @Then("^system will ask the user to enter something by responding with \"([^\"]*)\"$")
    public void system_will_ask_the_user_to_enter_something_by_responding_with(String expectedOutput) throws Throwable {
        runAndExit();
        assertTrue(io.getPrints().contains(expectedOutput));
    }

    @Given("^two blogposts have been created and saved to the database$")
    public void two_blogposts_have_been_created_and_saved_to_the_database() throws Throwable {
        addNewBlogposts();
    }

    @Given("^command listing all bookmarks is selected$")
    public void command_L_is_selected() throws Throwable {
        inputs.add("L");
    }

    @Given("^command \"([^\"]*)\" is chosen to order the list by id$")
    public void command_is_chosen_to_list_bookmarks_by_id(String listById) throws Throwable {
        inputs.add(listById);
    }

    @Given("^command \"([^\"]*)\" is chosen to order the list by title$")
    public void command_is_chosen_to_order_the_list_by_title(String listByTitle) throws Throwable {
        inputs.add(listByTitle);
    }

    @Then("^system will start to list all bookmarks and respond with \"([^\"]*)\"$")
    public void system_will_start_to_list_all_bookmarks_and_respond_with(String expectedOutput) throws Throwable {
        runAndExit();
        assertTrue(io.getPrints().contains(expectedOutput));
    }

    @Then("^system will tell the user that the database is empty by responding with \"([^\"]*)\"$")
    public void system_will_tell_the_user_that_the_database_is_empty_by_responding_with(String expectedOutput) throws Throwable {
        runAndExit();
        assertTrue(io.getPrints().contains(expectedOutput));
    }

    @Given("^command deleting a blogpost is selected$")
    public void command_deleting_a_blogpost_is_selected() throws Throwable {
        inputs.add("D");
    }

    @When("^existed id (\\d+) is entered$")
    public void id_is_entered(int id) throws Throwable {
        inputs.add(Integer.toString(id));
    }
    
    @When("^unexisted id (\\d+) is entered$")
    public void unexisted_id_is_entered(int unexistedId) throws Throwable {
        inputs.add(Integer.toString(unexistedId));
    }


    @When("^confirmation \"([^\"]*)\" is entered$")
    public void confirmation_is_entered(String confirmation) throws Throwable {
        inputs.add(confirmation);
    }

    @When("^user cancells the deletion by entering \"([^\"]*)\"$")
    public void user_cancells_the_deletion_by_entering(String cancellation) throws Throwable {
        inputs.add(cancellation);
    }

    private void runAndExit() {
        inputs.add("E");
        io = new StubIO(inputs);
        App app = new App(io, db);
        app.run();
    }

    private void addNewBlogposts() {
        inputs.add("A");
        inputs.add("titleA");
        inputs.add("authorA");
        inputs.add("urlA");
            inputs.add("A"); // Should this be here? Maybe yes? When we created a blogpost the console print "Choose a command" again and we need to enter the A command again to create another blogpost
        inputs.add("titleB");
        inputs.add("authorB");
        inputs.add("urlB");
    }
}
