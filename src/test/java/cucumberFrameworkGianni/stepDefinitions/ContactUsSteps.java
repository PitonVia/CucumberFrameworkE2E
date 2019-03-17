package cucumberFrameworkGianni.stepDefinitions;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import com.cucumber.listener.Reporter;
import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import pageObject.ContactUs_Page;
import utils.BaseClass;

public class ContactUsSteps extends BaseClass {
	
	WebDriver driver;
	ContactUs_Page page;
	// This is polymorphism at work!!!
	// These fields are actually called from the parent class!
	WebDriverWait wait;
	JavascriptExecutor js;
	// This is to create an object of the SoftAssert class - it cannot be inherited from the Base Class!
	SoftAssert softAssertion = new SoftAssert();

	
	@Given("^I access webdriveruniversity and click on the contact button$")
	public void i_access_webdriveruniversity_and_click_on_the_contact_button() throws Throwable {
		
		// Adding text to clarify this test step in the Extent Report
		Reporter.addStepLog("I access webdriveruniversity");
		
		// This is to initialize the driver of the ContactUs_Page as driver from the BaseClass
		page = new ContactUs_Page(getDriver());
		this.driver = page.getDriver();

		// Here we are calling a custom method of the ContactUs_Page that gets the constant URL field! 
		page.getHomePage();
		//driver.get("http://www.webdriveruniversity.com/");
		
		// Calling a custom click method of the ContactUs_Page
		page.clickContactUsBtn();

		// Another option would be to call a JS click method from the BaseClass with the WebElement from the page object 
//		jsClick(page.contactUsBtn);
/*		driver.findElement(By.xpath("//h1[contains(text(),'CONTACT US')]")).click();*/
	    
	    // New tab opens so need to switch to the new handle (last handle in the list)
		for (String handle : driver.getWindowHandles()) {
			driver.switchTo().window(handle);
		}
	}

	@When("^I enter a valid first name$")
	public void i_enter_a_valid_first_name() throws Throwable {
		
		// Adding text to clarify this test step in the Extent Report
		Reporter.addStepLog("I enter a valid first name");
		
		// Calling sendKeys method of the BaseClass class with ContactUs_Page firstName element!
		sendKeysToWebElement(page.firstName, "John");
		//Thread.sleep(1000);
		//driver.findElement(By.name("first_name")).sendKeys("John");
	}

	@And("^I enter a valid last name$")
	public void i_enter_a_valid_last_name(DataTable dataTable) throws Throwable {
	    // Change DataTable to one of List<YourType>, List<List<E>>, List<Map<K,V>> or Map<K,V>.
	    // E,K,V must be a scalar (String, Integer, Date, enum etc)
		
		// I'm keeping the original code here (not calling the page class)
		// .raw() converts the table to a List of Map. 
		// The top row is used as keys in the maps - <K> key type, <V> value type
		List<List<String>> lastNames = dataTable.raw();
		
		driver.findElement(By.name("last_name")).sendKeys(lastNames.get(0).get(1));
	}

	@And("^I enter a valid email address$")
	public void i_enter_a_valid_email_address() throws Throwable {
		page.email.sendKeys("webdriveruniversity@outlook.com");
		//driver.findElement(By.name("email")).sendKeys("webdriveruniversity@outlook.com");
	}

	@And("^I enter comments$")
	public void i_enter_comments(DataTable arg1) throws Throwable {
		
		// Calling a custom method from the page class that sends keys and deals with Data table
		page.enterComment(arg1, 0, 0);
		page.enterComment(arg1, 0, 1);
		
/*		List<List<String>> comments = arg1.raw(); 
		
		driver.findElement(By.name("message")).sendKeys(comments.get(0).get(0));
		driver.findElement(By.name("message")).sendKeys(comments.get(0).get(1));	*/
	}

	@When("^I click on the submit button$")
	public void i_click_on_the_submit_button() throws Throwable {
		
		// Calling a custom click method of the ContactUs_Page
		page.clickSubmitBtn();
/*		driver.findElement(By.cssSelector("input[value='SUBMIT']")).click();	*/
	}

	@Then("^The information should successfully be submitted via the contact us form$")
	public void the_information_should_successfully_be_submitted_via_the_contact_us_form() throws Throwable {
	
		WebElement message = page.popupMsg;
/*		WebElement message = driver.findElement(By.id("contact_reply"));	*/
		
		System.out.println("The actual message was: " + message.getText());
		System.out.println("The lower-case message without spaces was: " + message.getText().toLowerCase().replaceAll("\\s", ""));
		
		// Examples of the regular 'Hard' Asserts:
		// Comparing the actual String value to the expected value copied from the browser.
		Assert.assertEquals(message.getText(), "Thank You for your Message!");
		// Comparing values in lower case and with spaces removed:
		Assert.assertEquals(message.getText().toLowerCase().replaceAll("\\s", ""), "thankyouforyourmessage!");
		
		// 'Soft' assert, which, if it is false, won't stop code execution until the assertAll() method is called!!! 
		softAssertion.assertEquals(message.getText().toLowerCase().replaceAll("\\s", ""), "thankyouforyourmessage!");
		
		// Taking a screenshot of the last screen
		getScreenshot("ContactUs");
		
		// Capturing a screenshot for the Extent Report
		captureScreenshot();
		
//		driver.manage().deleteAllCookies();
		driver.quit();	
		
		// assertAll() ensures that the method will fail at this point if any soft assert is false
		// Code execution will be aborted after this method!
		softAssertion.assertAll(); 
	}
}
