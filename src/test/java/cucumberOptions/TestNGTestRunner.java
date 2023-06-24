package cucumberOptions;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(features = "src/test/resources/features", glue = "stepDefinitions", monochrome = true, tags = "@PlaceOrder or @OffersPage", plugin = {
		"html:target/cucumber.html", "json:target/cucumber.xml",
		"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:" })
public class TestNGTestRunner extends AbstractTestNGCucumberTests {

}
