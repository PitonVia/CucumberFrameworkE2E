package cucumberFrameworkGianni.log4j.filePkg;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggingToFileDemo {

	private static final Logger log = LogManager.getLogger(LoggingToFileDemo.class);

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	public static void main(String[] args) {
		
		log.trace("Trace message logged");
        log.debug("Debug message logged");
        log.info("Info message logged");
        log.warn("Warning message logged");
        log.error("Error message logged");
        log.fatal("Fatal message logged");
        
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        
        System.out.println("Using timestamp with default format: " + timestamp);
        
        System.out.println("logging completed at " + sdf.format(timestamp) + " - check log file");
	}
}
