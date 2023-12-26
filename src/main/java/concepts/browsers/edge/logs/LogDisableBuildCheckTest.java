package concepts.browsers.edge.logs;

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

public class LogDisableBuildCheckTest {

    // Declare a WebDriver instance to interact with the web browser
    private WebDriver driver;

    // Declare a ChromeDriverService object to manage the ChromeDriver process
    ChromeDriverService chromeDriverService;

    File logLocation;

    @BeforeMethod
    public void setUp() {
        // Set the system property for the WebDriver to use the JDK HTTP client
        System.setProperty("webdriver.http.factory", "jdk-http-client");

        logLocation = FileUtil.getTempFile("logFileFeatures", ".log");

        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, logLocation.getAbsolutePath());

        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_LEVEL_PROPERTY, ChromiumDriverLogLevel.WARNING.toString());

        chromeDriverService = new ChromeDriverService.Builder().withBuildCheckDisabled(true).build();

        // Initialize the ChromeDriver with the configured options
        driver = new ChromeDriver(chromeDriverService);

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

        String fileContent;

        try {
            fileContent = new String(Files.readAllBytes(logLocation.toPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Assert.assertTrue(fileContent.contains(expectedLog));

        clearLogProperties();
    }

    private void clearLogProperties() {
        System.clearProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY);

        System.clearProperty(ChromeDriverService.CHROME_DRIVER_LOG_LEVEL_PROPERTY);
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
