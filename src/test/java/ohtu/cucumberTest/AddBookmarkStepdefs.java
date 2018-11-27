package ohtu.cucumberTest;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import ohtu.io.StubIO;
import ohtu.main.App;
import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class AddBookmarkStepdefs extends AbstractStepdefs {

    @Before
    public void before() throws IOException {
        initialize();
        inputs.clear(); // Maybe it's faster to just clear this than to re-initialize?
    }

    @Given("^command adding a bookmark is selected$")
    public void command_A_selected() throws Throwable {
        inputs.add("A");
    }

    @Given("^command adding a blogpost is selected$")
    public void command_B_selected() throws Throwable {
        inputs.add("B"); // 'B' is for Blogpost.
    }

    @When("^title \"([^\"]*)\" and author \"([^\"]*)\" and url \"([^\"]*)\" are entered$")
    public void title_and_author_and_url_are_entered(String title, String author, String url) throws Throwable {
        inputs.add(title);
        inputs.add(author);
        inputs.add(url);
    }

    @When("^title is empty$")
    public void title_is_empty() throws Throwable {
        String title = "Java TWO marks";
        String author = "navamani saravanan";
        String url = "http://notescompsci.blogspot.com/2013/04/java-two-marks.html";
        inputs.add("");
        inputs.add(title);
        inputs.add(author);
        inputs.add(url);
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

    @Given("^command adding a video is selected$")
    public void command_adding_a_video_is_selected() throws Throwable {
        inputs.add("V");
    }

    @When("^title \"([^\"]*)\" and url \"([^\"]*)\" are entered$")
    public void title_and_url_are_entered(String title, String url) throws Throwable {
        inputs.add(title);
        inputs.add(url);
    }

}
