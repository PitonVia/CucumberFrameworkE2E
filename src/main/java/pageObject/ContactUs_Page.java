package pageObject;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import cucumber.api.DataTable;

public class ContactUs_Page {

	WebDriver driver;
	// Url of the ContactUs web page
	private static final String URL = "http://www.webdriveruniversity.com";
	
	// Constructor returns a WedDriver object!
	public ContactUs_Page(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	// Define all the WebElements using @FindBy annotation:
	public @FindBy(xpath = "//h1[contains(text(),'CONTACT US')]") WebElement contactUsBtn;
	public @FindBy(name = "first_name") WebElement firstName;
	public @FindBy(name = "last_name") WebElement lastName;
	public @FindBy(name = "email") WebElement email;
	public @FindBy(name = "message") WebElement comments;
	public @FindBy(css = "input[value='SUBMIT']") WebElement submitBtn;
	public @FindBy(id = "contact_reply") WebElement popupMsg;
	
	
	// Gets the home page URL
	public void getHomePage() {
		driver.get(URL);
	}
	
	// Two custom methods for clicking on buttons
	public void clickContactUsBtn() {
		contactUsBtn.click();
	}
	public void clickSubmitBtn() {
		submitBtn.click();
	}

	// Method for sending keys that deals with a Data table
	public void enterComment(DataTable dataTable, int row, int column) throws Exception {
		
		// .raw() converts the table to a List of Map. 
		// The top row is used as keys in the maps - <K> key type, <V> value type
		List<List<String>> data = dataTable.raw();
		
		// Sending keys as required for the Data table
		comments.sendKeys(data.get(row).get(column));
	}
	
	
	// Getter for the driver:
	public WebDriver getDriver() {
		return driver;
	}
}
