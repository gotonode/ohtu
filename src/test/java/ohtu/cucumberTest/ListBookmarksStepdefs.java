package ohtu.cucumberTest;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import ohtu.io.StubIO;

import static org.junit.Assert.assertTrue;

/**
 * Cucumber tests for listing and searching features
 */
public class ListBookmarksStepdefs extends AbstractStepdefs {


    @Given("^command listing all bookmarks is selected$")
    public void command_L_is_selected() throws Throwable {
        inputs.add("L");
    }

    @Given("^command to order the list by id is chosen$")
    public void command_is_chosen_to_list_bookmarks_by_id() throws Throwable {
        inputs.add("I");
    }

    @Given("^command to order the list by title is chosen$")
    public void command_is_chosen_to_order_the_list_by_title() throws Throwable {
        inputs.add("T");
    }

    @Then("^system will start to list all bookmarks and respond with \"([^\"]*)\"$")
    public void system_will_start_to_list_all_bookmarks_and_respond_with(String expectedOutput) throws Throwable {
        runAndExit();
        assertTrue(io.getPrints().contains(expectedOutput));
    }

    @Given("^commmand searching bookmarks is selected$")
    public void commmand_searching_bookmarks_is_selected() throws Throwable {
        inputs.add("S");
    }

    @Given("^command to search bookmarks by title is chosen$")
    public void command_is_chosen_to_search_bookmarks_by_title() throws Throwable {
        inputs.add("T");
    }

    @Given("^keyword \"([^\"]*)\" is entered$")
    public void keyword_is_entered(String keyword) throws Throwable {
        inputs.add(keyword);
    }

    @Then("^bookmarks whose titles contain the given keywords will be listed$")
    public void bookmarks_whose_titles_contain_the_given_keywords_will_be_listed() throws Throwable {
        runAndExit();
        assertTrue(containKeyword(io, "Title", "computer"));
    }

    @Then("^system will report no bookmark is found and respond with \"([^\"]*)\"$")
    public void system_will_report_no_bookmark_is_found_and_respond_with(String expectedOutput) throws Throwable {
        runAndExit();
        assertTrue(io.getPrints().contains(expectedOutput));
    }

    @Given("^command to search bookmarks by url is chosen$")
    public void command_is_chosen_to_search_bookmarks_by_url() throws Throwable {
        inputs.add("U");
    }

    @Then("^bookmarks whose urls contain the given keywords will be listed$")
    public void bookmarks_whose_urls_contain_the_given_keywords_will_be_listed() throws Throwable {
        runAndExit();
        assertTrue(containKeyword(io, "URL", "http"));

    }

    private boolean containKeyword(StubIO io, String column, String keyword) {
        return io.getPrints().stream().anyMatch((text) -> (text.contains(column) && text.contains(keyword)));
    }
}
