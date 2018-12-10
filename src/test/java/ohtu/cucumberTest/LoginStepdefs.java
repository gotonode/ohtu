package ohtu.cucumberTest;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import static ohtu.cucumberTest.AbstractStepdefs.io;
import static org.junit.Assert.assertTrue;

public class LoginStepdefs extends AbstractStepdefs {

    @Given("^command to log in is selected$")
    public void command_to_log_in_is_selected() throws Throwable {
        inputs.add("L");
    }

    @When("^valid username \"([^\"]*)\" and password \"([^\"]*)\" are given$")
    public void valid_username_and_password_are_given(String username, String password) throws Throwable {
        inputs.add(username);
        inputs.add(password);
    }

    @Then("^login will succeed and system will respond with \"([^\"]*)\"$")
    public void login_will_succeed_and_system_will_respond_with(String expectedOutput) throws Throwable {
        runAndExit();
        assertTrue(io.getPrints().contains(expectedOutput));
    }

    @When("^wrong username \"([^\"]*)\" and right password \"([^\"]*)\" are given$")
    public void wrong_username_and_right_password_are_given(String wrongName, String rightPassword) throws Throwable {
        inputs.add(wrongName);
        inputs.add(rightPassword);
    }

    @When("^right username \"([^\"]*)\" but wrong password \"([^\"]*)\" are given$")
    public void right_username_but_wrong_password_are_given(String rightName, String wrongPassword) throws Throwable {
        inputs.add(rightName);
        inputs.add(wrongPassword);
    }

    @Then("^login will fail and system will respond with \"([^\"]*)\"$")
    public void login_will_fail_and_system_will_respond_with(String expectedOutput) throws Throwable {
        runAndExit();
        assertTrue(io.getPrints().contains(expectedOutput));
    }

}
