package concepts.browsers.firefox.logs;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriverService;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.GeckoDriverService;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.regex.Pattern;

public class LogFileFeaturesTest {

    // Declare a WebDriver instance to interact with the web browser
    private WebDriver driver;

    // Declare a FirefoxDriverService object to manage the FirefoxDriver process
    FirefoxDriverService firefoxDriverService;

    // Declare a variable to store the file path for logging purposes
    File logLocation;

    @BeforeMethod
    public void setUp() {
        // Set the system property for the WebDriver to use the JDK HTTP client
        System.setProperty("webdriver.http.factory", "jdk-http-client");

        // Create a temporary log file using FileUtil and assigning it to logLocation
        logLocation = FileUtil.getTempFile("logFileFeatures", ".log");

        // Set the system property to specify the GeckoDriver log file location
        // This directs FirefoxDriver to write its logs to the designated file.
        System.setProperty(GeckoDriverService.GECKO_DRIVER_LOG_PROPERTY, logLocation.getAbsolutePath());

        // Set the system property to configure the GeckoDriver log level to DEBUG
        // This enables detailed logging, capturing most events and messages.
        System.setProperty(GeckoDriverService.GECKO_DRIVER_LOG_LEVEL_PROPERTY, FirefoxDriverLogLevel.DEBUG.toString());

        // Create a GeckoDriverService builder to configure Firefox driver settings
        firefoxDriverService = new GeckoDriverService.Builder()
                // Disable log truncation to capture all available browser logs
                .withTruncatedLogs(false)  // Enable full logging
                .build();  // Create the GeckoDriverService

        // Initialize the FirefoxDriver with the configured options
        driver = new FirefoxDriver(firefoxDriverService);

        // Maximize the browser window using WebDriver's manage() method
        driver.manage().window().maximize();
    }

    @Test(priority = 1)
    public void testLogFileFeatures() {
        // Navigate to the Google Home page.
        driver.get("https://www.google.com/");

        // Assert that the page title is "Google".
        Assert.assertEquals(driver.getTitle(), "Google");

        // Declare a variable to store the log file content
        String fileContent;

        // Read the entire contents of the log file into the variable
        try {
            // Use Files.readAllBytes() to read all bytes from the log file's path
            fileContent = new String(Files.readAllBytes(logLocation.toPath()));
        } catch (IOException e) {
            // Handle potential file reading errors
            throw new RuntimeException(e);
        }

        // Print the log file content to the console for viewing (optional)
        System.out.println(fileContent);

        // Create a regular expression pattern to match timestamps in the format "[DD-MM-YYYY]"
        // Matches two digits for day, two digits for month, and four digits for year, case-insensitive
        Pattern pattern = Pattern.compile("\\d{13}", Pattern.CASE_INSENSITIVE);

        // Assert that the log file content contains at least one timestamp matching the pattern
        Assert.assertTrue(pattern.matcher(fileContent).find());
    }

    @AfterMethod
    public void tearDown() {
        // Check if the 'driver' variable is not null, indicating that a WebDriver instance exists.
        if (driver != null) {
            // If a WebDriver instance exists, quit/close the browser session.
            driver.quit();

            // Close the service after WebDriver usage
            firefoxDriverService.stop();
        }
    }

}
