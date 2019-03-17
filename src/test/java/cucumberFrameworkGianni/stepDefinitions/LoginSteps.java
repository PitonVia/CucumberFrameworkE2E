package cucumberFrameworkGianni.stepDefinitions;

import static org.testng.Assert.assertEquals;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import utils.BaseClass;

// This class will not have its own page object unlike the other two stepDefinitions classes. 
// However, it will be inheriting some generic methods available in the parent BaseClass.

public class LoginSteps extends BaseClass {
	
	// This is to apply to concept of polymorphism as they will be coming from the parent class. 
	WebDriver driver;
	WebDriverWait wait;
	JavascriptExecutor js;
		
	@Given("^User navigates to the StackOverflow website$")
	public void user_navigates_to_the_StackOverflow_website() throws Throwable {
		
		// This is to initialize the driver as a driver from the BaseClass
		this.driver = getDriver();

		driver.get("https://stackoverflow.com/");
	}

	@Given("^User clicks on the Login button on Home page$")
	public void user_clicks_on_the_Login_button_on_Home_page() throws Throwable {
	    
		// Calling the jsClick method from the BaseClass
		jsClick(driver.findElement(By.xpath("//a[contains(text(),'Log In')]")));
/*		driver.findElement(By.xpath("//a[contains(text(),'Log In')]")).click();*/
	}

	@Given("^User enters a valid username$")
	public void user_enters_a_valid_username() throws Throwable {
		
		// Calling the sendKeys method from the BaseClass
		sendKeysToWebElement(driver.findElement(By.id("email")), "piton.via@gmail.com");
/*		driver.findElement(By.id("email")).sendKeys("piton.via@gmail.com");*/
//	    Thread.sleep(500);
	}

	@Given("^User enters a valid password$")
	public void user_enters_a_valid_password() throws Throwable {
		driver.findElement(By.id("password")).sendKeys("Qwerty001");
//		Thread.sleep(500);
	}

	@When("^User clicks on the Login button$")
	public void user_clicks_on_the_Login_button() throws Throwable {
		driver.findElement(By.id("submit-button")).click();
	}

	@Then("^User should be taken to the successful Landing page$")
	public void user_should_be_taken_to_the_succcessful_Landing_page() throws Throwable {
		
		// We will verify that we are on landing page by checking availability of the 'Ask Question' button:
		// assertEquals(int expected, int actual) is comparing the number of the buttons on the page to '1'
		assertEquals(driver.findElements(By.xpath("//a[contains(text(),'   Ask Question')]")).size(), 1); 
		
		// Taking a screenshot of the last screen
		getScreenshot("LoginLanding");
		
		// Capturing a screenshot for the Extent Report
		captureScreenshot();
		
//		driver.manage().deleteAllCookies();
		driver.quit();
	}	
}
