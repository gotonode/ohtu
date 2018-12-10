package ohtu.cucumberTest;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import java.io.IOException;
import java.sql.SQLException;
import static org.junit.Assert.assertTrue;

public class RegisterStepdefs extends AbstractStepdefs {

    @Before
    public void before() throws IOException {
        initialize();
        inputs.clear();
    }

    @Given("^command to register is selected$")
    public void command_to_register_is_selected() throws Throwable {
        inputs.add("R");
    }

    @When("^valid username \"([^\"]*)\" and password \"([^\"]*)\" and matched password confirmation are given$")
    public void valid_username_and_password_and_matched_password_confirmation_are_given(String username, String password) throws Throwable {
        successfulRegister(username, password);
    }

    @Then("^register will success and systen will respond with \"([^\"]*)\"$")
    public void register_will_success_and_systen_will_respond_with(String expectedOutput) throws Throwable {
        runAndExit();
        assertTrue(io.getPrints().contains(expectedOutput));
    }

    @When("^too short username \"([^\"]*)\" is given$")
    public void too_short_username_and_valid_password_and_matched_password_confirmation_is_given(String tooShortUsername) throws Throwable {
        inputs.add(tooShortUsername);
        String validUsername = "user";
        String password = "password";
        successfulRegister(validUsername, password);
    }

    @When("^valid username \"([^\"]*)\" and too short password \"([^\"]*)\" are given$")
    public void valid_username_and_too_short_password_are_given(String username, String tooShortPassword) throws Throwable {
        inputs.add(username);
        inputs.add(tooShortPassword);
        String validPassword = "password";
        inputs.add(validPassword);
        inputs.add(validPassword);

    }

    @When("^valid username \"([^\"]*)\" and password \"([^\"]*)\" but unmatched password confirmation \"([^\"]*)\" are given$")
    public void valid_username_and_password_but_unmatched_password_confirmation_are_given(String username, String password, String unmatchedConfirm) throws Throwable {
        inputs.add(username);
        inputs.add(password);
        inputs.add(unmatchedConfirm);

        inputs.add(password);
        inputs.add(password);//right password cofirmation
    }

    @Then("^register will fail and system will respond with \"([^\"]*)\"$")
    public void register_will_fail_and_system_will_respond_with(String expectedOutput) throws Throwable {
        runAndExit();
        System.out.println(io.getPrints());
        assertTrue(io.getPrints().contains(expectedOutput));
    }

    @Given("^a user has registerd with username \"([^\"]*)\"$")
    public void a_user_has_registerd_with_username(String username) throws Throwable {
        String password = "password";
        successfulRegister(username, password);
    }

    @When("^another user tries to register existed with username \"([^\"]*)\"")
    public void existed_username_is_given(String existedUsername) throws Throwable {
        inputs.add("R");
        inputs.add(existedUsername);
        String newUsername = "user123";
        String password = "password123";
        successfulRegister(newUsername, password);
    }

    private void successfulRegister(String username, String password) {
        inputs.add(username);
        inputs.add(password);
        inputs.add(password);
    }

}
