package concepts.browsers.edge;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.manager.SeleniumManagerOutput;
import org.openqa.selenium.remote.service.DriverFinder;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.io.File;

public class StartBrowserFromSpecifiedLocationTest {

    // Declare a WebDriver instance to interact with the web browser.
    private WebDriver driver;

    // Declare a ChromeOptions object to store browser configuration settings
    ChromeOptions chromeOptions;

    @Test(priority = 1)
    public void testStartBrowserFromSpecifiedLocation() {
        // Set the system property for the WebDriver to use the JDK HTTP client
        System.setProperty("webdriver.http.factory", "jdk-http-client");

        // Instantiate ChromeOptions to configure the ChromeDriver
        chromeOptions = new ChromeOptions();

        // Set the path to the Chrome browser binary
        chromeOptions.setBinary(getChromeLocation());

        // Initialize the ChromeDriver with the configured options
        driver = new ChromeDriver(chromeOptions);

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

    // Method to get a chrome binary path for ChromeOptions
    private File getChromeLocation(){
        // Create a ChromeOptions instance
        chromeOptions = new ChromeOptions();

        // Set a browser version (optional)
        chromeOptions.setBrowserVersion("stable");

        // Get the browser path using DriverFinder utility method
        SeleniumManagerOutput.Result location = DriverFinder.getPath(ChromeDriverService.createDefaultService(), chromeOptions);

        // Extract and return the browser path from the result
        return new File(location.getBrowserPath());
    }

}
