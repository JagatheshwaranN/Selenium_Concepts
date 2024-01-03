package concepts.browsers.edge.logs;

import concepts.browsers.FileUtil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeDriverService;
import org.openqa.selenium.chromium.ChromiumDriverLogLevel;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class LogDisableBuildCheckTest {

    // Declare a WebDriver instance to interact with the web browser
    private WebDriver driver;

    // Declare an EdgeDriverService object to manage the EdgeDriver process
    EdgeDriverService edgeDriverService;

    // Declare a variable to store the file path for logging purposes
    File logLocation;

    @BeforeMethod
    public void setUp() {
        // Set the system property for the WebDriver to use the JDK HTTP client
        System.setProperty("webdriver.http.factory", "jdk-http-client");

        // Create a temporary log file using FileUtil and assigning it to logLocation
        logLocation = FileUtil.getTempFile("logFileFeatures", ".log");

        // Set the system property to specify the EdgeDriver log file location
        // This directs EdgeDriver to write its logs to the designated file.
        System.setProperty(EdgeDriverService.EDGE_DRIVER_LOG_PROPERTY, logLocation.getAbsolutePath());

        // Set the system property to configure the EdgeDriver log level.
        // This limits the logs to WARNING and above, filtering out less severe messages.
        System.setProperty(EdgeDriverService.EDGE_DRIVER_LOG_LEVEL_PROPERTY, ChromiumDriverLogLevel.WARNING.toString());

        // Create an EdgeDriverService instance, disabling the build check
        edgeDriverService = new EdgeDriverService.Builder().
                withBuildCheckDisabled(true) // Skips the check for matching ChromeDriver and browser versions
                .build();

        // Initialize the EdgeDriver with the configured options
        driver = new EdgeDriver(edgeDriverService);

        // Maximize the browser window using WebDriver's manage() method
        driver.manage().window().maximize();
    }

    @Test(priority = 1)
    public void testLogDisableBuildCheck() {
        String expectedLog = "[WARNING]: You are using an unsupported command-line switch: --disable-build-check";
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

        // Assert that the log file content contains the expected log message
        Assert.assertTrue(fileContent.contains(expectedLog));

        // Clear the previously set log properties (EdgeDriver log file and level)
        clearLogProperties(); // Likely a custom method to reset those properties
    }

    // Method to clear EdgeDriver-related log properties
    private void clearLogProperties() {
        // Unset the system property for the EdgeDriver log file location
        System.clearProperty(EdgeDriverService.EDGE_DRIVER_LOG_PROPERTY);

        // Unset the system property for the EdgeDriver log level
        System.clearProperty(EdgeDriverService.EDGE_DRIVER_LOG_LEVEL_PROPERTY);
    }

    @AfterMethod
    public void tearDown() {
        // Check if the 'driver' variable is not null, indicating that a WebDriver instance exists.
        if (driver != null) {
            // If a WebDriver instance exists, quit/close the browser session.
            driver.quit();

            // Close the service after WebDriver usage
            edgeDriverService.stop();
        }
    }

}
