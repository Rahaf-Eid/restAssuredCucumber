package restAssured.runner;

import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = {"src/test/java/restAssured/features"},
        glue = {"restAssured/steps"},
        tags = "@Books"
)
public class GetPostRunner {


}
