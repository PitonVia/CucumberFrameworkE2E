package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.cucumber.listener.Reporter;

public class BaseClass {

	// WebDriver is Not static!
	public WebDriver driver;
	public WebDriverWait wait;
	public JavascriptExecutor js;
	private String screenshotName; // for Extent Reports
	

	// Constructor
	public BaseClass() {
		this.driver = getDriver();
		this.wait = new WebDriverWait(driver, 15);
		this.js = ((JavascriptExecutor) driver);
	}

	// Get the WebDriver object:
	public WebDriver getDriver() {

		try {
			
			// Calling the config.properties file directly:
			Properties prop = new Properties();
			FileInputStream stream = new FileInputStream("src/main/java/properties/config.properties");
			prop.load(stream);
			
			String browserName = prop.getProperty("browserName");

			switch (browserName) {
			case "firefox":
				if (driver == null) {
					System.setProperty("webdriver.gecko.driver", Constant.GECKO_DRIVER_DIRECTORY);
					FirefoxOptions ffOptions = new FirefoxOptions();
					ffOptions.setCapability("marionette", true);
					driver = new FirefoxDriver(ffOptions);
				}
				break;

			case "chrome":
				if (driver == null) {
					ChromeOptions options = new ChromeOptions();
					options.setBinary("C:\\Program Files (x86)\\Google\\Chrome Dev\\Application\\chrome.exe");
					System.setProperty("webdriver.chrome.driver", Constant.CHROME_DRIVER_DIRECTORY);
					driver = new ChromeDriver(options);
				}
				break;

			case "ie":
				if (driver == null) {
					System.setProperty("webdriver.ie.driver", Constant.IE_DRIVER_DIRECTORY);
					InternetExplorerOptions ieOptions = new InternetExplorerOptions();
					ieOptions.setCapability("ignoreZoomSetting", true);
					driver = new InternetExplorerDriver(ieOptions);
				}
			}

		} catch (Exception e) {
			System.out.println("Unable to load browser " + e.getMessage());
		} finally {
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
		}
		return driver;
	}
	
	
	// ALL THE BASEPAGE METHODS
	/**********************************************************************************
	 **CLICK METHODS
	 **********************************************************************************/
	public void waitAndClickElement(WebElement element) {
		boolean clicked = false;
		int attempts = 0;
		while (!clicked && attempts < 10) {
			try {
				this.wait.until(ExpectedConditions.elementToBeClickable(element)).click();
				System.out.println("Successfully clicked on the WebElement: " + "<" + element.toString() + ">");
				clicked = true;
			} catch (Exception e) {
				System.out.println("Unable to wait and click on WebElement, Exception: " + e.getMessage());
				Assert.fail("Unable to wait and click on the WebElement, using locator: " + "<" + element.toString() + ">");
			}
			attempts++;
		}
	}

	public void waitAndClickElementsUsingByLocator(By locator) {
		boolean clicked = false;
		int attempts = 0;
		while (!clicked && attempts < 10) {
			try {
				this.wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
				System.out.println("Successfully clicked on the element using by locator: " + "<" + locator.toString() + ">");
				clicked = true;
			} catch (Exception e) {
				System.out.println("Unable to wait and click on the element using the By locator, Exception: " + e.getMessage());
				Assert.fail("Unable to wait and click on the element using the By locator, element: " + "<"+ locator.toString() + ">");
			}
			attempts++;
		}
	}

	public void clickOnTextFromDropdownList(WebElement dropDown, String textToSearchFor) throws Exception {
		Wait<WebDriver> tempWait = new WebDriverWait(driver, 30);
		try {
			tempWait.until(ExpectedConditions.elementToBeClickable(dropDown)).click();
			dropDown.sendKeys(textToSearchFor);
			dropDown.sendKeys(Keys.ENTER);
			System.out.println("Successfully sent the following keys: " + textToSearchFor + ", to the following WebElement: " + "<" + dropDown.toString() + ">");
		} catch (Exception e) {
			System.out.println("Unable to send the following keys: " + textToSearchFor + ", to the following WebElement: " + "<" + dropDown.toString() + ">");
			Assert.fail("Unable to select the required text from the dropdown menu, Exception: " + e.getMessage());
		}
	}

	public void clickOnElementUsingCustomTimeout(WebElement element, WebDriver driver, int timeout) {
		try {
			final WebDriverWait customWait = new WebDriverWait(driver, timeout);
			customWait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(element)));
			element.click();
			System.out.println("Successfully clicked on the WebElement, using locator: " + "<" + element + ">"+ ", using a custom Timeout of: " + timeout);
		} catch (Exception e) {
			System.out.println("Unable to click on the WebElement, using locator: " + "<" + element + ">" + ", using a custom Timeout of: " + timeout);
			Assert.fail("Unable to click on the WebElement, Exception: " + e.getMessage());
		}
	}
	
	/**********************************************************************************/
	/**********************************************************************************/
	
	
	 /**********************************************************************************
	 **ACTION METHODS
	 **********************************************************************************/

	public void actionMoveAndClick(WebElement element) throws Exception {
		Actions actions = new Actions(driver);
		try {
			this.wait.until(ExpectedConditions.elementToBeClickable(element)).isEnabled();
			actions.moveToElement(element).click().build().perform();
			System.out.println("Successfully Action Moved and Clicked on the WebElement, using locator: " + "<" + element.toString() + ">");
		} catch (StaleElementReferenceException elementUpdated) {
			WebElement elementToClick = element;
			Boolean elementPresent = wait.until(ExpectedConditions.elementToBeClickable(elementToClick)).isEnabled();
			if (elementPresent == true) {
				actions.moveToElement(elementToClick).click().build().perform();
				System.out.println("(Stale Exception) - Successfully Action Moved and Clicked on the WebElement, using locator: " + "<" + element.toString() + ">");
			}
		} catch (Exception e) {
			System.out.println("Unable to Action Move and Click on the WebElement, using locator: " + "<" + element.toString() + ">");
			Assert.fail("Unable to Action Move and Click on the WebElement, Exception: " + e.getMessage());
		}
	}

	public void actionMoveAndClickByLocator(By locator) throws Exception {
		Actions actions = new Actions(driver);
		try {
			Boolean elementPresent = wait.until(ExpectedConditions.elementToBeClickable(locator)).isEnabled();
			if (elementPresent == true) {
				WebElement elementToClick = driver.findElement(locator);
				actions.moveToElement(elementToClick).click().build().perform();
				System.out.println("Action moved and clicked on the following element, using By locator: " + "<" + locator.toString() + ">");
			}
		} catch (StaleElementReferenceException elementUpdated) {
			WebElement elementToClick = driver.findElement(locator);
			actions.moveToElement(elementToClick).click().build().perform();
			System.out.println("(Stale Exception) - Action moved and clicked on the following element, using By locator: "+ "<" + locator.toString() + ">");
		} catch (Exception e) {
			System.out.println("Unable to Action Move and Click on the WebElement using by locator: " + "<" + locator.toString() + ">");
			Assert.fail("Unable to Action Move and Click on the WebElement using by locator, Exception: " + e.getMessage());
		}
	}

	/**********************************************************************************/
	/**********************************************************************************/

	
	/**********************************************************************************
	 **SEND KEYS METHODS /
	 **********************************************************************************/
	public void sendKeysToWebElement(WebElement element, String textToSend) throws Exception {
		try {
			this.WaitUntilWebElementIsVisible(element);
			element.clear();
			element.sendKeys(textToSend);
			System.out.println("Successfully Sent the following keys: '" + textToSend + "' to element: " + "<"+ element.toString() + ">");
		} catch (Exception e) {
			System.out.println("Unable to locate WebElement: " + "<" + element.toString() + "> and send the following keys: " + textToSend);
			Assert.fail("Unable to send keys to WebElement, Exception: " + e.getMessage());
		}
	}

	/**********************************************************************************/
	/**********************************************************************************/

	
	/**********************************************************************************
	 **JS METHODS & JS SCROLL
	 **********************************************************************************/
	public void scrollToElementByWebElementLocator(WebElement element) {
		try {
			this.wait.until(ExpectedConditions.visibilityOf(element)).isEnabled();
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
			((JavascriptExecutor) driver).executeScript("window.scrollBy(0, -400)");
			System.out.println("Succesfully scrolled to the WebElement, using locator: " + "<" + element.toString() + ">");
		} catch (Exception e) {
			System.out.println("Unable to scroll to the WebElement, using locator: " + "<" + element.toString() + ">");
			Assert.fail("Unable to scroll to the WebElement, Exception: " + e.getMessage());
		}
	}

	public void jsPageScroll(int intX, int intY) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("scroll(" + intX + "," + intY + ")");
			System.out.println("Succesfully scrolled to the correct position! using locators: " + intX + ", " + intY);
		} catch (Exception e) {
			System.out.println("Unable to scroll to element using locators: " + "<" + intX + "> " + " <" + intY + ">");
			Assert.fail("Unable to manually scroll to WebElement, Exception: " + e.getMessage());
		}
	}

	public void waitAndclickElementUsingJS(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		try {
			wait.until(ExpectedConditions.elementToBeClickable(element));
			js.executeScript("arguments[0].click();", element);
			System.out.println("Successfully JS clicked on the following WebElement: " + "<" + element.toString() + ">");
		} catch (StaleElementReferenceException elementUpdated) {
			WebElement staleElement = element;
			Boolean elementPresent = wait.until(ExpectedConditions.elementToBeClickable(staleElement)).isEnabled();
			if (elementPresent == true) {
				js.executeScript("arguments[0].click();", elementPresent);
				System.out.println("(Stale Exception) Successfully JS clicked on the following WebElement: " + "<" + element.toString() + ">");
			}
		} catch (NoSuchElementException e) {
			System.out.println("Unable to JS click on the following WebElement: " + "<" + element.toString() + ">");
			Assert.fail("Unable to JS click on the WebElement, Exception: " + e.getMessage());
		}
	}

	public void jsClick(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", element);
	}

	/**********************************************************************************/
	/**********************************************************************************/

	
	/**********************************************************************************
	 ** WAIT METHODS
	 **********************************************************************************/
	public boolean WaitUntilWebElementIsVisible(WebElement element) {
		try {
			this.wait.until(ExpectedConditions.visibilityOf(element));
			System.out.println("WebElement is visible using locator: " + "<" + element.toString() + ">");
			return true;
		} catch (Exception e) {
			System.out.println("WebElement is NOT visible, using locator: " + "<" + element.toString() + ">");
			Assert.fail("WebElement is NOT visible, Exception: " + e.getMessage());
			return false;
		}
	}

	public boolean WaitUntilWebElementIsVisibleUsingByLocator(By locator) {
		try {
			this.wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			System.out.println("Element is visible using By locator: " + "<" + locator.toString() + ">");
			return true;
		} catch (Exception e) {
			System.out.println("WebElement is NOT visible, using By locator: " + "<" + locator.toString() + ">");
			Assert.fail("WebElement is NOT visible, Exception: " + e.getMessage());
			return false;
		}
	}

	public boolean isElementClickable(WebElement element) {
		try {
			this.wait.until(ExpectedConditions.elementToBeClickable(element));
			System.out.println("WebElement is clickable using locator: " + "<" + element.toString() + ">");
			return true;
		} catch (Exception e) {
			System.out.println("WebElement is NOT clickable using locator: " + "<" + element.toString() + ">");
			return false;
		}
	}

	public boolean waitUntilPreLoadElementDissapears(By by) {
		return this.wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
	}

	/**********************************************************************************/
	/**********************************************************************************/

	
	/**********************************************************************************
	 ** PAGE METHODS
	 **********************************************************************************/
	
/*	// Since we've copied all methods to this class - the method below is useless.
   //Returns a new instance of the BasePage
	public BasePage loadUrl(String url) throws Exception {
		driver.get(url);
		return new BasePage();
	}
*/
	public String getCurrentURL() {
		try {
			String url = driver.getCurrentUrl();
			System.out.println("Found(Got) the following URL: " + url);
			return url;
		} catch (Exception e) {
			System.out.println("Unable to locate (Get) the current URL, Exception: " + e.getMessage());
			return e.getMessage();
		}
	}

	public String waitForSpecificPage(String urlToWaitFor) {
		try {
			String url = driver.getCurrentUrl();
			this.wait.until(ExpectedConditions.urlMatches(urlToWaitFor));
			System.out.println("The current URL was: " + url + ", " + "navigated and waited for the following URL: "+ urlToWaitFor);
			return urlToWaitFor;
		} catch (Exception e) {
			System.out.println("Exception! waiting for the URL: " + urlToWaitFor + ",  Exception: " + e.getMessage());
			return e.getMessage();
		}
	}
	
	/**********************************************************************************/
	/**********************************************************************************/

	
	/**********************************************************************************
	 ** ALERT & POPUPS METHODS
	 **********************************************************************************/
	public void closePopups(By locator) {
		try {
			List<WebElement> elements = this.wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
			for (WebElement element : elements) {
				if (element.isDisplayed()) {
					element.click();
					this.wait.until(ExpectedConditions.invisibilityOfAllElements(elements));
					System.out.println("The popup has been closed Successfully!");
				}
			}
		} catch (Exception e) {
			System.out.println("Exception! - could not close the popup!, Exception: " + e.toString());
			throw (e);
		}
	}

	public boolean checkPopupIsVisible() {
		try {
			@SuppressWarnings("unused")
			Alert alert = wait.until(ExpectedConditions.alertIsPresent());
			System.out.println("A popup has been found!");
			return true;
		} catch (Exception e) {
			System.err.println("Error came while waiting for the alert popup to appear. " + e.getMessage());
		}
		return false;
	}

	public boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void closeAlertPopupBox() {
		try {
			Alert alert = this.wait.until(ExpectedConditions.alertIsPresent());
			alert.accept();
		} catch (UnhandledAlertException f) {
			Alert alert = driver.switchTo().alert();
			alert.accept();
		} catch (Exception e) {
			System.out.println("Unable to close the popup");
			Assert.fail("Unable to close the popup, Exception: " + e.getMessage());
		}
	}
	/**********************************************************************************/
	/**********************************************************************************/
	/****TAKES SCREENSHOTS*************************************************************/
	
	// Takes screenshots of failed tests that takes a part of name String
	// from the listener when test fails. result will = name of failed test.
	public void getScreenshot(String result) {
		
		try {
			File src = ((TakesScreenshot)this.driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(src, new File("src/test/java/cucumberFrameworkGianni/screenshots/" + result + "screenshot.png"));
		} catch (IOException E) {
			System.out.println("Could not take screenshot: " + E.getMessage());
		}
	}
	
	/**********************************************************************************/
	/*****EXTENT REPORTS***************************************************************/

	// Returns a string containing formatted date stamp and supplied file extension
	private String returnDateStamp(String fileExtension) {
		
		Date d = new Date();
		String date = d.toString().replace(":", "_").replace(" ", "_") + fileExtension;
		
		return date;
	}
	
	// Takes screenshot, saves it to a folder and attaches it to the Extent Report
	public void captureScreenshot() {
		
		try {
			File src = ((TakesScreenshot)this.driver).getScreenshotAs(OutputType.FILE);
			
			screenshotName = returnDateStamp(".jpg");
			
			FileUtils.copyFile(src, new File("Extent/imgs/" + screenshotName));
			
			// This is to attach the screenshot files to the Extent Reports
			Reporter.addStepLog("Taking a screenshot.");
			Reporter.addStepLog("<br>");
			Reporter.addStepLog("<a target=\"_blank\", href=" + returnScreenshotName() + 
					"><img src=" + returnScreenshotName() + " height=200 width=300></img></a>");
			
		} catch (IOException E) {
			System.out.println("Could not take screenshot: " + E.getMessage());
		}
	}

	// Returns the actual name of the screenshot file
	private String returnScreenshotName() {
		
		// Note that there is not need to add 'Extent/' before 'imgs/' in the screenshot path
		return ("imgs/" + screenshotName).toString();
	}
	


	
//	private static void copyFileUsingIOStreams(File source, File dest) throws IOException {
//		
//		InputStream is = null;
//		OutputStream os = null;
//		
//		try {
//			is = new FileInputStream(source);
//			os = new FileOutputStream(dest);
//			
//			byte[] buffer = new byte[1024];
//			int length;
//			
//			while ((length = is.read(buffer)) > 0) {
//			os.write(buffer, 0, length);
//			}		
//		} finally {
//			is.close();
//			os.close();
//		}
//	}
	
	// The following two methods make each generated report.html files unique so that 
	// new Extent Reports won't overwrite older ones. 
	
	// Short version of code to copy files using FileUtils:
	private static void copyFileUsingApacheCommonsIOFileUtils(File source, File dest) throws IOException {
	    FileUtils.copyFile(source, dest);
	}

	// Copies the latest extent.html to a new file with a name containing dat and time stamps
	public static void copyLatestExtentReport() throws IOException {
		
		Date d = new Date();
		String date = d.toString().replace(":", "_").replace(" ", "_");
		
		File source = new File("Extent/report.html");
		File dest = new File("Extent/report_" + date + ".html");
		
		copyFileUsingApacheCommonsIOFileUtils(source, dest);
	}
	
}
