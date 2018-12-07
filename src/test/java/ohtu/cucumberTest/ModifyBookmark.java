package ohtu.cucumberTest;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;


import static org.junit.Assert.assertTrue;

/**
 * Cucumber tests for modifying features
 */
public class ModifyBookmark extends AbstractStepdefs {

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

    @When("^new title \"([^\"]*)\" and new url \"([^\"]*)\" are entered$")
    public void new_title_and_new_url_are_entered(String newTitle, String newUrl) throws Throwable {
        inputs.add(newTitle);
        inputs.add(newUrl);
    }

    @Then("^bookmark with given id will be modified with given data and system will respond with \"([^\"]*)\"$")
    public void bookmark_with_given_id_will_be_modified_with_given_data_and_system_will_respond_with(String expectedOutput) throws Throwable {
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

    @When("^new title \"([^\"]*)\" and new author \"([^\"]*)\" and new ISBN \"([^\"]*)\" are entered$")
    public void new_title_and_new_author_and_new_ISBN_are_entered(String title, String author, String isbn) throws Throwable {
        inputs.add(title);
        inputs.add(author);
        inputs.add(isbn);
    }

    @When("^a new but invalid url \"([^\"]*)\" is given$")
    public void a_new_but_invalid_url_is_given(String invalidUrl) throws Throwable {
        inputs.add("");
        inputs.add(invalidUrl);
        inputs.add("");
    }

}
