package concepts.browsers.firefox.logs;

import concepts.browsers.FileUtil;
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

public class LogWithLevelTest {

    // Declare a WebDriver instance to interact with the web browser
    private WebDriver driver;

    // Declare a FirefoxDriverService object to manage the FirefoxDriver process
    FirefoxDriverService firefoxDriverService;

    // Declare a variable to store the file path for logging purposes
    File logLocation;

    @BeforeMethod
    public void setUp() {
        // Create a temporary log file using FileUtil and assigning it to logLocation
        logLocation = FileUtil.getTempFile("logsWithLevelToFile", ".log");

        // Set the system property to specify the FirefoxDriver log file location
        // This directs FirefoxDriver to write its logs to the designated file.
        System.setProperty(GeckoDriverService.GECKO_DRIVER_LOG_PROPERTY, logLocation.getAbsolutePath());

        // Create a GeckoDriverService builder for configuring Firefox driver settings
        firefoxDriverService = new GeckoDriverService.Builder()
                // Set the logging level to DEBUG for detailed insights
                .withLogLevel(FirefoxDriverLogLevel.DEBUG)  // Enable DEBUG logging
                .build();  // Create the GeckoDriverService

        // Initialize the FirefoxDriver with the configured options
        driver = new FirefoxDriver(firefoxDriverService);

        // Maximize the browser window using WebDriver's manage() method
        driver.manage().window().maximize();
    }

    @Test(priority = 1)
    public void testLogWithLevel() {
        // Declare a string variable to hold the expected warning message
        String expectedLog = "marionette\tDEBUG";

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

        // Assert that the log file content contains the expected log message
        Assert.assertTrue(fileContent.contains(expectedLog));
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
