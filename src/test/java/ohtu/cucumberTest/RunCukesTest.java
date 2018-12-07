package ohtu.cucumberTest;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty"}, features = "src/test/resources/ohtu", tags = "@FunctionalTest")//added tags to test only the register feature,tags will be removed in the end
public class RunCukesTest {
    // This space intentionally left blank. Except for this message.
}
