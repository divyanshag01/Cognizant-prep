/**
 * Exercise 1: Implementing the Singleton Pattern
 * 
 * Scenario: Ensure that a logging utility class has only one instance 
 * throughout the application lifecycle to ensure consistent logging.
 */

// Singleton Logger Class
class Logger {
    private static Logger instance;
    private String logFile;
    
    // Private constructor to prevent instantiation
    private Logger() {
        this.logFile = "app.log";
    }
    
    // Method to get the single instance
    public static synchronized Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }
    
    // Log info messages
    public void logInfo(String message) {
        System.out.println("[INFO] " + message);
    }
    
    // Log error messages
    public void logError(String message) {
        System.out.println("[ERROR] " + message);
    }
    
    // Log warning messages
    public void logWarning(String message) {
        System.out.println("[WARNING] " + message);
    }
    
    // Get the log file name
    public String getLogFile() {
        return logFile;
    }
}

// Test the Singleton Implementation
public class Exercise1_Singleton {
    public static void main(String[] args) {
        // Get the singleton instance
        Logger logger1 = Logger.getInstance();
        Logger logger2 = Logger.getInstance();
        
        // Test that both references point to the same instance
        System.out.println("Logger1 and Logger2 are the same instance: " + (logger1 == logger2));
        
        // Test logging functionality
        logger1.logInfo("Application started");
        logger2.logWarning("This is a warning message");
        logger1.logError("An error occurred");
        
        System.out.println("Log file: " + logger1.getLogFile());
    }
}
