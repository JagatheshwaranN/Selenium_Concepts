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

public class LogWithLevelTest {

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
        logLocation = FileUtil.getTempFile("logsWithLevelToFile", ".log");

        // Set the system property to specify the EdgeDriver log file location
        // This directs EdgeDriver to write its logs to the designated file.
        System.setProperty(EdgeDriverService.EDGE_DRIVER_LOG_PROPERTY, logLocation.getAbsolutePath());

        // Create an EdgeDriverService instance, configuring the log level to DEBUG
        edgeDriverService = new EdgeDriverService.Builder()
                .withLoglevel(ChromiumDriverLogLevel.DEBUG) // Enable detailed logging for debugging purposes
                .build();

        // Initialize the EdgeDriver with the configured options
        driver = new EdgeDriver(edgeDriverService);

        // Maximize the browser window using WebDriver's manage() method
        driver.manage().window().maximize();
    }

    @Test(priority = 1)
    public void testLogWithLevel() {
        // Declare a string variable to hold the expected warning message
        String expectedLog = "[DEBUG]:";

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
            edgeDriverService.stop();
        }
    }

}
