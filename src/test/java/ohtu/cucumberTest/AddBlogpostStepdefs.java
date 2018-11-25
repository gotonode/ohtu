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

public class AddBlogpostStepdefs extends AbstractStepdefs {

    @Before
    public void before() throws IOException {
        initialize();
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
//

    @When("^title is empty$")
    public void title_is_empty() throws Throwable {
        inputs.add("");
        inputs.add("title1");
        inputs.add("author1");
        inputs.add("url1");
    }
//

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

    private void runAndExit() {
        inputs.add("E");
        io = new StubIO(inputs);
        App app = new App(io, db);
        app.run();
    }

}
