package cucumberFrameworkGianni.stepDefinitions;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import utils.BaseClass;
import cucumber.api.Scenario;

// I am not using this class. It should be put together with the stepDefinitions class. 
// It cannot be used since I am initializing WebDriver using page object classes in the stepDefinitions classes.
// The idea is that @Before and @After Cucumber annotations will be called to initialize and quit driver
// after execution of each stepDefinitions class. 


public class MasterHooks extends BaseClass {

	
//	@Before
//	public void initializeDriver() {
//		
//		driver = getDriver();
//	}
//	
//	@After
//	public void tearDownAndScreenshotOnFailure(Scenario scenario) {
//		
//		if(scenario.isFailed()) {
//			try {
//				scenario.embed(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES), "image/png");
//	            scenario.write("URL at failure: " + driver.getCurrentUrl());
//	        } catch (WebDriverException wde) {
//	            scenario.write("Embed Failed " + wde.getMessage());
//	        } catch (ClassCastException cce) {
//	            cce.printStackTrace();
//	        }
//		}
//		driver.manage().deleteAllCookies();
//		driver.quit();	
//	}
	
//	@ After
//	public void teardown() {
//		if (driver != null) {
//			driver.manage().deleteAllCookies();
//			driver.quit();
//			driver = null;
//		}
//	}
//	
//	@After
//	public void tearDownAndScreenshotOnFailure(Scenario scenario) {
//		try {
//			if(driver != null && scenario.isFailed()) {
//				try {
//					scenario.embed(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES), "image/png");
//		            scenario.write("URL at failure: " + driver.getCurrentUrl());
//					driver.manage().deleteAllCookies();
//					driver.quit();
//					driver = null;
//		        } catch (WebDriverException wde) {
//		            scenario.write("Embed Failed " + wde.getMessage());
//		        } catch (ClassCastException cce) {
//		            cce.printStackTrace();
//		        }
//			}
//			if(driver != null) {
//				driver.manage().deleteAllCookies();
//				driver.quit();
//				driver = null;
//			}
//		} catch (Exception e) {
//			System.out.println("Methods failed: tearDownAndScreenshotOnFailure, Exception: " + e.getMessage());
//		}
//	}
	
}
