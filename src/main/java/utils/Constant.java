package utils;

public class Constant {
	
	// Config properties file with locations of the config file and 3 browser drivers
	
	public static final String CONFIG_PROPERTIES_DIRECTORY = "src/main/java/properties/config.properties";
	
	public static final  String GECKO_DRIVER_DIRECTORY = "src/test/java/cucumberFrameworkGianni/resources/geckodriver.exe";
	
	// For the Chrome driver location will use relative path Method from Gianni
	public static final  String CHROME_DRIVER_DIRECTORY = System.getProperty("user.dir") + "\\src\\test\\java\\cucumberFrameworkGianni\\resources\\chromedriver.exe";
			
	public static final  String IE_DRIVER_DIRECTORY = "src/test/java/cucumberFrameworkGianni/resources/IEDriverServer.exe";
}
