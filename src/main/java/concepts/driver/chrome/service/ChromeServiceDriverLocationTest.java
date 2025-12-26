package concepts.driver.chrome.service;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.service.DriverFinder;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;

public class ChromeServiceDriverLocationTest {

    // Declare a WebDriver instance to interact with the web browser
    private WebDriver driver;

    // Declare a ChromeOptions object to store browser configuration settings
    ChromeOptions chromeOptions;

    // Declare a ChromeDriverService object to manage the ChromeDriver process
    ChromeDriverService chromeDriverService;

    // Declare File objects to hold paths to the Chrome browser and ChromeDriver executables
    File driverPath, browserPath;

    @BeforeMethod
    public void setUp() {
        // Obtain paths to Chrome browser and ChromeDriver executable
        setBinaryPath();

        // Create a ChromeOptions instance
        chromeOptions = new ChromeOptions();

        // Set the path to the Chrome browser binary
        chromeOptions.setBinary(browserPath);  // Specify the browser location

        // Create a ChromeDriverService instance using the identified driver path
        chromeDriverService = new ChromeDriverService.Builder()
                .usingDriverExecutable(driverPath)  // Set the path to the ChromeDriver executable
                .build();  // Construct the service with the specified configurations

        // Initialize the ChromeDriver with the configured options
        driver = new ChromeDriver(chromeDriverService, chromeOptions);

        // Maximize the browser window using WebDriver's manage() method
        driver.manage().window().maximize();
    }

    @Test(priority = 1)
    public void testChromeServiceDriverLocation() {
        // Navigate to the Google Home page.
        driver.get("https://www.google.com/");

        // Assert that the page title is "Google".
        Assert.assertEquals(driver.getTitle(), "Google");
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

    // Method to set a binary path for ChromeOptions
    private void setBinaryPath() {
        // Create a ChromeOptions instance
        chromeOptions = new ChromeOptions();

        // Set a browser version (optional)
        chromeOptions.setBrowserVersion("stable");

        // Get the driver and browser paths using DriverFinder utility method
        DriverFinder driverFinder = new DriverFinder(ChromeDriverService.createDefaultService(), chromeOptions);

        // Extract the driver and browser paths from the result
        driverPath = new File(driverFinder.getDriverPath()); // Assuming driverPath is a File object
        browserPath = new File(driverFinder.getBrowserPath()); // Assuming browserPath is a File object
    }

}
