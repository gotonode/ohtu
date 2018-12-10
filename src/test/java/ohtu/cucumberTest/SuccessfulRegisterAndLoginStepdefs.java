package ohtu.cucumberTest;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import java.io.IOException;

public class SuccessfulRegisterAndLoginStepdefs extends AbstractStepdefs {

    @Before
    public void before() throws IOException {
        initialize();
        inputs.clear();
    }

    @Given("^user has registered and logged in with username \"([^\"]*)\" and password \"([^\"]*)\"$")
    public void user_has_registered_and_logged_in_with_username_and_password(String username, String password) throws Throwable {
        inputs.add("R");
        inputs.add(username);
        inputs.add(password);
        inputs.add(password);
    }

}
