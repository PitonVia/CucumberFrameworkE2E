package cucumberFrameworkGianni.testRunners;

import java.io.File;
import java.io.IOException;

import org.junit.runner.RunWith;
import org.testng.annotations.AfterClass;
import com.cucumber.listener.Reporter;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import utils.BaseClass;

@RunWith(Cucumber.class) // Mandatory annotation @RunWith

@CucumberOptions (
	// package location of the Feature files
	features = {"src/test/java/cucumberFrameworkGianni/featureFiles"},
	// package location of the step definitions
	glue = {"cucumberFrameworkGianni/stepDefinitions"},
	// Ensures that output in the console is readable
	monochrome = true,
	//tags allow to pick specific feature files for execution
	tags = {}, 
	//plugin specifies parameters for test execution reports 
	plugin = {"pretty","html:target/cucumber","json:target/cucumber.json",
			"com.cucumber.listener.ExtentCucumberFormatter:Extent/report.html"}
	)

public class MainRunner extends AbstractTestNGCucumberTests {

	@AfterClass
	public void writeExtentReport() throws IOException {
		Reporter.loadXMLConfig(new File("ExtentConfig.xml"));
		BaseClass.copyLatestExtentReport(); // note how we are calling the static method!
	}
}
