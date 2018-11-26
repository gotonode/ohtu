
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
public class ListBookmarksStepdefs extends AbstractStepdefs{
    
   

    @Before
    public void before() throws IOException {
        initialize();
        inputs.clear(); // Maybe it's faster to just clear this than to re-initialize?
    }
    
@Given("^two bookmarks have been created and saved to the database$")
    public void two_bookmarks_have_been_created_and_saved_to_the_database() throws Throwable {
        addNewBookmarks();
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

    @Then("^system won't list anything and will respond with \"([^\"]*)\"$")
    public void system_will_tell_the_user_that_the_database_is_empty_by_responding_with(String expectedOutput) throws Throwable {
        runAndExit();
        assertTrue(io.getPrints().contains(expectedOutput));
    }
    
    private void runAndExit() {
        inputs.add("E");
        io = new StubIO(inputs);
        App app = new App(io, db);
        app.run();
    }

    private void addNewBookmarks() { //other types will be added soon
        inputs.add("A");
		inputs.add("B");
        inputs.add("titleA");
        inputs.add("authorA");
        inputs.add("urlA");
        inputs.add("A"); // Should this be here? Maybe yes? When we created a blogpost the console print "Choose a command" again and we need to enter the A command again to create another blogpost
		inputs.add("B");
        inputs.add("titleB");
        inputs.add("authorB");
        inputs.add("urlB");
    }
}
