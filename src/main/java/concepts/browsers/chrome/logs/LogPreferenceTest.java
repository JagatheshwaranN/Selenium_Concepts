package concepts.browsers.chrome.logs;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
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

    // Declare a ChromeOptions object to store browser configuration settings
    ChromeOptions chromeOptions;

    @BeforeMethod
    public void setUp() {
        // Set the system property for the WebDriver to use the JDK HTTP client
        System.setProperty("webdriver.http.factory", "jdk-http-client");

        // Create a ChromeOptions instance
        chromeOptions = new ChromeOptions();

        // Create an instance of LoggingPreferences to configure ChromeDriver logging
        LoggingPreferences loggingPreferences = new LoggingPreferences();

        // Enable logging for the PERFORMANCE category, capturing all levels of messages
        // This includes detailed performance-related information like network events,
        // page loading times, etc.
        loggingPreferences.enable(LogType.PERFORMANCE, Level.ALL);

        // Set the logging preferences as a capability for the ChromeOptions object.
        // This ensures the logging configuration is applied when starting ChromeDriver.
        chromeOptions.setCapability(ChromeOptions.LOGGING_PREFS, loggingPreferences);

        // Initialize the ChromeDriver with the configured options
        driver = new ChromeDriver(chromeOptions);

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
