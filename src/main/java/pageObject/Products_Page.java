package pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Products_Page {

	WebDriver driver;
	
	// Constructor returns a WedDriver object!
	public Products_Page(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	// Define all the WebElements using @FindBy annotation:
	public @FindBy(xpath = "//p[contains(text(),'Special Offers')]") WebElement specialOffersBtn;
	public @FindBy(xpath = "//b[contains(text(),'NEWCUSTOMER')]") WebElement discountCodeTxt;
	public @FindBy(xpath = "//button[contains(text(),'Proceed')]") WebElement proceedBtn;

	// Method example for clicking on the Special Offers button
	// No need for methods here as will be using generic methods of the Base Class
	public void clickSpecialOffers() {
		specialOffersBtn.click();
	}
	
	// Getter for the driver:
	public WebDriver getDriver() {
		return driver;
	}
}
