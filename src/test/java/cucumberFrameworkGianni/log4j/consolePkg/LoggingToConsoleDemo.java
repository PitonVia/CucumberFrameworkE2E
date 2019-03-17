package cucumberFrameworkGianni.log4j.consolePkg;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class LoggingToConsoleDemo {

	private static final Logger log = LogManager.getLogger(LoggingToConsoleDemo.class);
	
	public static void main(String[] args) {
		
		log.trace("Trace message logged");
        log.debug("Debug message logged");
        log.info("Info message logged");
        log.warn("Warning message logged");
        log.error("Error message logged");
        log.fatal("Fatal message logged");
	}
}
