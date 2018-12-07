package ohtu.cucumberTest;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import java.io.IOException;

import static org.junit.Assert.assertTrue;

/**
 * Cucumber tests for deleting features
 */
public class DeleteBookmarkStepdefs extends AbstractStepdefs {

	@Given("^command deleting is selected$")
	public void command_deleting_a_blogpost_is_selected() throws Throwable {
		inputs.add("D");
	}

	@When("^existed id (\\d+) is entered to be deleted$")
	public void id_is_entered(int id) throws Throwable {
		inputs.add(Integer.toString(id));
	}

	@When("^unexisted id (\\d+) is entered to be deleted$")
	public void unexisted_id_is_entered(int unexistedId) throws Throwable {
		inputs.add(Integer.toString(unexistedId));
	}

	@When("^confirmation \"([^\"]*)\" is entered$")
	public void confirmation_is_entered(String confirmation) throws Throwable {
		inputs.add(confirmation);
	}

	@When("^user cancells the deletion by entering \"([^\"]*)\"$")
	public void user_cancells_the_deletion_by_entering(String cancellation) throws Throwable {
		inputs.add(cancellation);
	}

	@Then("^bookmark with given id will be deleted and system will respond with \"([^\"]*)\"$")
	public void bookmark_with_given_id_will_be_deleted_and_system_will_respond_with(String expectedOutput) throws Throwable {
		runAndExit();
		assertTrue(io.getPrints().contains(expectedOutput));
	}

	@Then("^blogpost with given id will not be deleted and system will respond with \"([^\"]*)\"$")
	public void blogpost_with_given_id_will_not_be_deleted_and_system_will_respond_with(String expectedOutput) throws Throwable {
		runAndExit();
		assertTrue(io.getPrints().contains(expectedOutput));
	}

	


}
