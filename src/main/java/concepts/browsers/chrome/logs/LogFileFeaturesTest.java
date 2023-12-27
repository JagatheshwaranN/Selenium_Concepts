package concepts.browsers.chrome.logs;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chromium.ChromiumDriverLogLevel;
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

    // Declare a ChromeDriverService object to manage the ChromeDriver process
    ChromeDriverService chromeDriverService;

    File logLocation;

    @BeforeMethod
    public void setUp() {
        // Set the system property for the WebDriver to use the JDK HTTP client
        System.setProperty("webdriver.http.factory", "jdk-http-client");

        // Create a temporary log file using FileUtil and assigning it to logLocation
        logLocation = FileUtil.getTempFile("logFileFeatures", ".log");

        // Set the system property to specify the ChromeDriver log file location
        // This directs ChromeDriver to write its logs to the designated file.
        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, logLocation.getAbsolutePath());

        // Set the system property to configure the ChromeDriver log level to DEBUG
        // This enables detailed logging, capturing most events and messages.
        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_LEVEL_PROPERTY, ChromiumDriverLogLevel.DEBUG.toString());

        // Create a ChromeDriverService instance with specific logging features
        chromeDriverService = new ChromeDriverService.Builder()
                .withAppendLog(true)  // Append logs to the file instead of overwriting
                .withReadableTimestamp(true)  // Add human-readable timestamps to log entries
                .build();

        // Initialize the ChromeDriver with the configured options
        driver = new ChromeDriver(chromeDriverService);

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

        // Create a regular expression pattern to match timestamps in the format "[DD-MM-YYYY]"
        // Matches two digits for day, two digits for month, and four digits for year, case-insensitive
        Pattern pattern = Pattern.compile("\\[\\d\\d-\\d\\d-\\d\\d\\d\\d", Pattern.CASE_INSENSITIVE);

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
            chromeDriverService.stop();
        }
    }

}
