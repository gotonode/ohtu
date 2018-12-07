package ohtu.cucumberTest;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import java.io.IOException;
import ohtu.user.UserController;
import static org.junit.Assert.assertTrue;

public class registerStepdefs extends AbstractStepdefs {

    @Before
    public void before() throws IOException {
        initialize();
        inputs.clear();
    }

    @Given("^command to register is selected$")
    public void command_to_register_is_selected() throws Throwable {
        inputs.add("R");
    }

    @Given("^valid username \"([^\"]*)\" and password \"([^\"]*)\" and matched password confirmation are given$")
    public void valid_username_and_password_and_matched_password_confirmation_are_given(String username, String password) throws Throwable {
        inputs.add(username);
        inputs.add(password);
        inputs.add(password);//password confirmation
    }

    @Then("^register will success and systen will respond with \"([^\"]*)\"$")
    public void register_will_success_and_systen_will_respond_with(String expectedOutput) throws Throwable {
        runAndExit();
        assertTrue(io.getPrints().contains(expectedOutput));
    }

    @Given("^too short username \"([^\"]*)\" is given$")
    public void too_short_username_and_valid_password_and_matched_password_confirmation_is_given(String tooShortUsername) throws Throwable {
        inputs.add(tooShortUsername);
        String validUsername = "user";
        String password = "password";
        inputs.add(validUsername);
        inputs.add(password);
        inputs.add(password);
    }

    @Given("^valid username \"([^\"]*)\" and too short password \"([^\"]*)\" are given$")
    public void valid_username_and_too_short_password_are_given(String username, String tooShortPassword) throws Throwable {
        inputs.add(username);
        inputs.add(tooShortPassword);
        String validPassword = "password";
        inputs.add(validPassword);
        inputs.add(validPassword);

    }

    @Given("^valid username \"([^\"]*)\" and password \"([^\"]*)\" but unmatched password confirmation \"([^\"]*)\" are given$")
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

}
