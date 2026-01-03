package concepts.browsers.edge.logs;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.logging.Level;

public class LogPreferenceTest {

    // Declare a WebDriver instance to interact with the web browser
    private WebDriver driver;

    // Declare an EdgeOptions object to store browser configuration settings
    EdgeOptions edgeOptions;

    @BeforeMethod
    public void setUp() {
        // Create an EdgeOptions instance
        edgeOptions = new EdgeOptions();

        // Create an instance of LoggingPreferences to configure EdgeDriver logging
        LoggingPreferences loggingPreferences = new LoggingPreferences();

        // Enable logging for the PERFORMANCE category, capturing all levels of messages
        // This includes detailed performance-related information like network events,
        // page loading times, etc.
        loggingPreferences.enable(LogType.PERFORMANCE, Level.ALL);

        // Set the logging preferences as a capability for the EdgeOptions object.
        // This ensures the logging configuration is applied when starting EdgeDriver.
        edgeOptions.setCapability(EdgeOptions.LOGGING_PREFS, loggingPreferences);

        // Initialize the EdgeDriver with the configured options
        driver = new EdgeDriver(edgeOptions);

        // Maximize the browser window using WebDriver's manage() method
        driver.manage().window().maximize();
    }

    @Test(priority = 1)
    public void testLogPreference() {
        // Navigate to the Google Home page.
        driver.get("https://www.google.com/");

        // Assert that the page title is "Google".
        Assert.assertEquals(driver.getTitle(), "Google");

        // Retrieve the performance logs from the WebDriver instance
        LogEntries logEntries = driver.manage().logs().get(LogType.PERFORMANCE);

        // Assert that the retrieved log entries are not empty
        Assert.assertFalse(logEntries.getAll().isEmpty());
    }

    @AfterMethod
    public void tearDown() {
        // Check if the 'driver' variable is not null, indicating that a WebDriver instance exists.
        if (driver != null) {
            // If a WebDriver instance exists, quit/close the browser session.
            driver.quit();
        }
    }

}
