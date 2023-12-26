package concepts.browsers.edge;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeDriverService;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.manager.SeleniumManagerOutput;
import org.openqa.selenium.remote.service.DriverFinder;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.io.File;

public class StartBrowserFromSpecifiedLocationTest {

    // Declare a WebDriver instance to interact with the web browser.
    private WebDriver driver;

    // Declare an EdgeOptions object to store browser configuration settings
    EdgeOptions edgeOptions;

    @Test(priority = 1)
    public void testStartBrowserFromSpecifiedLocation() {
        // Set the system property for the WebDriver to use the JDK HTTP client
        System.setProperty("webdriver.http.factory", "jdk-http-client");

        // Instantiate EdgeOptions to configure the EdgeDriver
        edgeOptions = new EdgeOptions();

        // Set the path to the Edge browser binary
        edgeOptions.setBinary(getEdgeLocation());

        // Initialize the EdgeDriver with the configured options
        driver = new EdgeDriver(edgeOptions);

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

    // Method to get an edge binary path for EdgeOptions
    private File getEdgeLocation(){
        // Create an EdgeOptions instance
        edgeOptions = new EdgeOptions();

        // Set a browser version (optional)
        edgeOptions.setBrowserVersion("stable");

        // Get the browser path using DriverFinder utility method
        SeleniumManagerOutput.Result location = DriverFinder.getPath(EdgeDriverService.createDefaultService(), edgeOptions);

        // Extract and return the browser path from the result
        return new File(location.getBrowserPath());
    }

}
