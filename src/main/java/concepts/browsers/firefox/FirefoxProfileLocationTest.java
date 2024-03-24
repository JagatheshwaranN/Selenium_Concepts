package concepts.browsers.firefox;

import concepts.browsers.FileUtil;
import junit.framework.Assert;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriverService;
import org.openqa.selenium.firefox.GeckoDriverService;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;

public class FirefoxProfileLocationTest {

    // Declare a WebDriver instance to interact with the web browser
    private WebDriver driver;

    // Declare a FirefoxDriverService object to manage the FirefoxDriver process
    FirefoxDriverService firefoxDriverService;

    // Declare a variable to store the file path
    File profileDirectory;

    @BeforeMethod
    public void setUp() {
        // Set the system property for the WebDriver to use the JDK HTTP client
        System.setProperty("webdriver.http.factory", "jdk-http-client");

        // Create a temporary directory to store the Firefox profile
        File profileDirectory = FileUtil.getTempDirectory("profile_");

        // Build a GeckoDriverService (the driver that controls Firefox)
        // Specify the profile directory to be used for this instance
        firefoxDriverService = new GeckoDriverService.Builder()
                .withProfileRoot(profileDirectory)  // Set the profile directory
                .build();
    }

    @Test(priority = 1)
    public void testFirefoxProfileLocation() {
        // Create a FirefoxDriver instance using the configured GeckoDriverService
        driver = new FirefoxDriver(firefoxDriverService);

        // Cast the driver to a RemoteWebDriver to access capability information
        RemoteWebDriver remoteDriver = (RemoteWebDriver) driver;

        // Get the capabilities of the running Firefox browser
        Capabilities capabilities = remoteDriver.getCapabilities();

        // Extract the actual path of the Firefox profile being used
        String location = (String) capabilities.getCapability("moz:profile");

        // Print the expected profile directory path (for verification)
        System.out.println(profileDirectory.getAbsolutePath());

        // Assert that the actual profile path matches the expected one
        Assert.assertTrue(location.contains(profileDirectory.getAbsolutePath()));
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
