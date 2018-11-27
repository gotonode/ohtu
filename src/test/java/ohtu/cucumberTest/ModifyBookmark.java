package ohtu.cucumberTest;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import java.io.IOException;
import ohtu.io.StubIO;
import ohtu.main.App;
import static org.junit.Assert.assertTrue;

public class ModifyBookmark extends AbstractStepdefs {

    @Before
    public void before() throws IOException {
        initialize();
        inputs.clear(); // Maybe it's faster to just clear this than to re-initialize?
    }

    @Given("^a blogpost has been created and saved to the database$")
    public void a_blogpost_has_been_created_and_saved_to_the_database() throws Throwable {
        addNewBlogpost();
    }

    @Given("^command modifying is selected$")
    public void command_modifying_a_blogpost_is_selected() throws Throwable {
        inputs.add("M");
    }

    @When("^existed id (\\d+) is entered to be modified$")
    public void existed_id_is_entered_to_be_modified(int id) throws Throwable {
        inputs.add(Integer.toString(id));
    }

    @When("^new title \"([^\"]*)\" and new author\"([^\"]*)\" and new url \"([^\"]*)\" are entered$")
    public void new_title_and_new_author_and_new_url_are_entered(String newTitle, String newAuthor, String newUrl) throws Throwable {
        inputs.add(newTitle);
        inputs.add(newAuthor);
        inputs.add(newUrl);
    }

    @Then("^blogpost with given id will be modified with given data and system will respond with \"([^\"]*)\"$")
    public void blogpost_with_given_id_will_be_modified_with_given_data_and_system_will_respond_with(String expectedOutput) throws Throwable {
        runAndExit();
        assertTrue(io.getPrints().contains(expectedOutput));
    }

    @When("^all fields are left empty$")
    public void all_fields_are_left_empty() throws Throwable {
        int dataField = 3;
        for (int i = 0; i < dataField; i++) {
            inputs.add("");
        }
    }

    @Then("^data of the chosen blogpost won't be changed and system will respond with \"([^\"]*)\"$")
    public void data_of_the_chosen_blogpost_won_t_be_changed_and_system_will_respond_with(String expectedOutput) throws Throwable {
        runAndExit();
        assertTrue(io.getPrints().contains(expectedOutput));
    }

    @When("^unexisted id (\\d+) is entered to be modified$")
    public void unexisted_id_is_entered_to_be_modified(int unexistedId) throws Throwable {
        inputs.add(Integer.toString(unexistedId));
    }

    @Then("^system will ask user to enter a valuable id and respond with \"([^\"]*)\"$")
    public void system_will_ask_user_to_enter_a_valuable_id_and_respond_with(String expectedOutput) throws Throwable {
        runAndExit();
        assertTrue(io.getPrints().contains(expectedOutput));
    }

    @Then("^system will report there's nothing can be modified by responding with \"([^\"]*)\"$")
    public void system_will_report_there_s_nothing_can_be_modified_by_responding_with(String expectedOutput) throws Throwable {
        runAndExit();
        assertTrue(io.getPrints().contains(expectedOutput));
    }

    private void addNewBlogpost() {
        inputs.add("A");
        inputs.add("B");
        inputs.add("Data Mining");
        inputs.add("navamani saravanan");
        inputs.add("http://notescompsci.blogspot.com/2013/04/data-mining.html");
    }


}
