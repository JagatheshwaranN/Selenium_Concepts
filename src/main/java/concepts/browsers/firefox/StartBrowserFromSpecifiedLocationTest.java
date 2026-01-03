package concepts.browsers.firefox;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.GeckoDriverService;
import org.openqa.selenium.remote.service.DriverFinder;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.nio.file.Path;

public class StartBrowserFromSpecifiedLocationTest {

    // Declare a WebDriver instance to interact with the web browser.
    private WebDriver driver;

    // Declare a FirefoxOptions object to store browser configuration settings
    FirefoxOptions firefoxOptions;

    @Test(priority = 1)
    public void testStartBrowserFromSpecifiedLocation() {
        // Instantiate FirefoxOptions to configure the FirefoxDriver
        firefoxOptions = new FirefoxOptions();

        // Set the path to the Firefox browser binary
        firefoxOptions.setBinary(getFirefoxLocation());

        // Initialize the FirefoxDriver with the configured options
        driver = new FirefoxDriver(firefoxOptions);

        // Maximize the browser window for better visibility
        driver.manage().window().maximize();

        // Navigate to the specified URL
        driver.get("https://www.google.com/");

        // Compare the actual title with the expected title and assert their equality
        Assert.assertEquals(driver.getTitle(), "Google");
    }

    @AfterMethod
    public void tearDown() {
        // Check if the 'driver' variable is not null, indicating that a WebDriver instance exists.
        if (driver != null) {
            // If a WebDriver instance exists, quit/close the browser session.
            driver.quit();
        }
    }

    // Method to get a firefox binary path for FirefoxOptions
    private Path getFirefoxLocation(){
        // Create a FirefoxOptions instance
        firefoxOptions = new FirefoxOptions();

        // Set a browser version (optional)
        firefoxOptions.setBrowserVersion("stable");

        // Get the browser path using DriverFinder utility method
        DriverFinder driverFinder = new DriverFinder(GeckoDriverService.createDefaultService(), firefoxOptions);

        // Extract and return the browser path from the result
        return Path.of(driverFinder.getBrowserPath());
    }

}
