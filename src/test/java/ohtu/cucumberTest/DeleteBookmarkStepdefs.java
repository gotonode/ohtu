package ohtu.cucumberTest;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import ohtu.io.StubIO;
import ohtu.main.App;
import java.io.IOException;
import static org.junit.Assert.assertTrue;

public class DeleteBookmarkStepdefs extends AbstractStepdefs {

    @Before
    public void before() throws IOException {
        initialize();
        inputs.clear(); // Maybe it's faster to just clear this than to re-initialize?
    }

    @Given("^two blogposts have been created and saved to the database$")
    public void two_blogposts_have_been_created_and_saved_to_the_database() throws Throwable {
        addNewBlogposts();
    }

    @Given("^command deleting is selected$")
    public void command_deleting_a_blogpost_is_selected() throws Throwable {
        inputs.add("D");
    }

    @When("^existed id (\\d+) is entered to be deleted$")
    public void id_is_entered(int id) throws Throwable {
        inputs.add(Integer.toString(id));
    }

    @When("^unexisted id (\\d+) is entered to be deleted$")
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

    @Then("^blogpost with given id will be deleted and system will respond with \"([^\"]*)\"$")
    public void blogpost_with_given_id_will_be_deleted_and_system_will_respond_with(String expectedOutput) throws Throwable {
        runAndExit();
        assertTrue(io.getPrints().contains(expectedOutput));
    }

    @Then("^blogpost with given id will not be deleted and system will respond with \"([^\"]*)\"$")
    public void bolgpost_with_given_id_will_not_be_deleted_and_system_will_respond_with(String expectedOutput) throws Throwable {
        runAndExit();
        assertTrue(io.getPrints().contains(expectedOutput));
    }

    @Then("^system will remind user of unvaluable id by responding with \"([^\"]*)\"$")
    public void system_will_remind_user_of_unvaluable_id_by_responding_with(String expectedOutput) throws Throwable {
        runAndExit();
        assertTrue(io.getPrints().contains(expectedOutput));
    }

    @Then("^system will report there's nothing can be deleted by responding with \"([^\"]*)\"$")
    public void system_will_report_nothing_can_be_deleted_by_responding_with(String expectedOutput) throws Throwable {
        runAndExit();
        assertTrue(io.getPrints().contains(expectedOutput));
    }

    private void addNewBlogposts() {
        inputs.add("A");
        inputs.add("B");
        inputs.add("Data Mining");
        inputs.add("navamani saravanan");
        inputs.add("http://notescompsci.blogspot.com/2013/04/data-mining.html");
        inputs.add("A"); // Should this be here? Maybe yes? When we created a blogpost the console print "Choose a command" again and we need to enter the A command again to create another blogpost
        inputs.add("B");
        inputs.add("SoftWare Testing");
        inputs.add("navamani saravanan");
        inputs.add("http://notescompsci.blogspot.com/2013/04/software-testing.html");
    }
}
