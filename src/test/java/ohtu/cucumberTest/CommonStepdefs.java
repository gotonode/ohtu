package ohtu.cucumberTest;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import java.sql.SQLException;
import static ohtu.cucumberTest.AbstractStepdefs.io;
import static org.junit.Assert.assertTrue;

public class CommonStepdefs extends AbstractStepdefs {
    
    @Given("^logged out$")
    public void logged_out() throws SQLException {
        runAndExit();
        inputs.clear();
    }

    @Then("^system will remind user of unvaluable id by responding with \"([^\"]*)\"$")
    public void system_will_remind_user_of_unvaluable_id_by_responding_with(String expectedOutput) throws Throwable {
        runAndExit();
        assertTrue(io.getPrints().contains(expectedOutput));
    }

    @Then("^system will report about the empty database by responding with \"([^\"]*)\"$")
    public void system_will_report_nothing_can_be_deleted_by_responding_with(String expectedOutput) throws Throwable {
        runAndExit();
        assertTrue(io.getPrints().contains(expectedOutput));
    }

    @Then("^sysytem will ask user to enter a valid url by responding with \"([^\"]*)\"$")
    public void sysytem_will_ask_user_to_enter_a_valid_url_by_responding_with(String expectedOutput) throws SQLException {
        runAndExit();
        assertTrue(io.getPrints().contains(expectedOutput));
    }
}
