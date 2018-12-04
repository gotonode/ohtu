package ohtu.cucumberTest;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import ohtu.io.StubIO;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class ListBookmarksStepdefs extends AbstractStepdefs {

    @Before
    public void before() throws IOException {
        initialize();
        inputs.clear(); // Maybe it's faster to just clear this than to re-initialize?
    }

    @Given("^new bookmarks have been created and saved to the database$")
    public void new_bookmarks_have_been_created_and_saved_to_the_database() throws Throwable {
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

    @Given("^commmand searching bookmarks is selected$")
    public void commmand_searching_bookmarks_is_selected() throws Throwable {
        inputs.add("S");
    }

    @Given("^command \"([^\"]*)\" is chosen to search bookmarks by title$")
    public void command_is_chosen_to_search_bookmarks_by_title(String searchByTitle) throws Throwable {
        inputs.add(searchByTitle);
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

    @Given("^command \"([^\"]*)\" is chosen to search bookmarks by url$")
    public void command_is_chosen_to_search_bookmarks_by_url(String searchByUrl) throws Throwable {
        inputs.add(searchByUrl);
    }

    @Then("^bookmarks whose urls contain the given keywords will be listed$")
    public void bookmarks_whose_urls_contain_the_given_keywords_will_be_listed() throws Throwable {
        runAndExit();
        assertTrue(containKeyword(io, "URL", "http"));

    }

    private void addNewBookmarks() { //other types will be added soon
        addNewBlogpost("Data Mining in computer science", "navamani saravanan", "http://notescompsci.blogspot.com/2013/04/data-mining.html");
        addNewVideo("Map of Computer Science", "https://www.youtube.com/watch?v=SzJ46YA_RaA");
        addNewBook("Learning Python", "Mark Lutz", "9-781-593-2760-34");
    }

    private boolean containKeyword(StubIO io, String column, String keyword) {
        return io.getPrints().stream().anyMatch((text) -> (text.contains(column) && text.contains(keyword)));
    }
}
