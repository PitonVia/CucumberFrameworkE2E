package cucumberFrameworkGianni.stepDefinitions;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import pageObject.Products_Page;
import utils.BaseClass;

public class ProductsSteps extends BaseClass {
	
	WebDriver driver;
	Products_Page page;
	// This is polymorphism at work!!!
	// These two fields are actually called from the parent class - BaseClass!
	WebDriverWait wait;
	JavascriptExecutor js;
	// This is to create an object of the SoftAssert class - it cannot be inherited from the Base Class!
	SoftAssert softAssertion = new SoftAssert();
	

	@Given("^user goes to \"([^\"]*)\" website$")
	public void user_goes_to_website(String arg1) throws Throwable {
		
//		if (driver == null) {
			// This is to initialize the driver of the ProductsPage as driver from the Base Class
			page = new Products_Page(getDriver());
			this.driver = page.getDriver();
//		}


		// Here we have specified the URL inside the Products.feature file, but in the other script - refer
		// to ContactUsSteps we are actually calling the URL constant from the object page class!
		driver.get(arg1); // in this case arg1 is the url in the Products.feature file
	}

	@When("^user clicks on Special Offers button$")
    public void user_clicks_on_special_offers_button() throws Throwable {
		
		// Calling the wait & click method from the parent BaseClass to eliminate Thread.sleep()
		// while using a WebElement from the Products_Page class!
		waitAndClickElement(page.specialOffersBtn);
/*		Thread.sleep(1000);
		driver.findElement(By.xpath("//p[contains(text(),'Special Offers')]")).click();*/
	}

	@Then("^user should be presented with a promo alert$")
	public void user_should_be_presented_with_a_promo_alert() throws Throwable {
		
		// The pop-up window is implemented on the current page so no need to switch to it
		// Identifying the bolded discount code element of the pop-up window:
		WebElement discountCode = page.discountCodeTxt;
/*		WebElement discountCode = driver.findElement(By.xpath("//b[contains(text(),'NEWCUSTOMER')]"));*/
		
		// This is to remove the Thread.sleep(1000) code
		WaitUntilWebElementIsVisible(page.discountCodeTxt);
/*		Thread.sleep(1000);*/
		
		// Hard assert - to ensure that alert text contains the following discount code: NEWCUSTOMER773322
		Assert.assertTrue(discountCode.getText().toString().contains("NEWCUSTOMER773322"));
		
		// 'Soft' assert, which, if it is false, won't stop code execution until the assertAll() method is called!!! 
		softAssertion.assertTrue(discountCode.getText().toString().contains("NEWCUSTOMER773322"));
		
		System.out.println("Printing the discount code: " + discountCode.getText().toString());
		
		// Taking a screenshot of the pop up window
		getScreenshot("ProductsPopup");
		
		// Capturing a screenshot for the Extent Report
		captureScreenshot();
		
		// Calling the wait & click method from the parent Base Class to eliminate Thread.sleep()
		// while using a WebElement from the Products_Page class!
		waitAndClickElement(page.proceedBtn);
/*		getDriver().findElement(By.xpath("//button[contains(text(),'Proceed')]")).click(); // press Proceed
		Thread.sleep(1000);*/
		
//		driver.manage().deleteAllCookies();
		driver.quit();
		
		// assertAll() ensures that the method will fail at this point if any soft assert is false
		// Code execution will be aborted after this method!
		softAssertion.assertAll(); 
	}
}
