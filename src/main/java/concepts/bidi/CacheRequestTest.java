package concepts.bidi;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v119.network.Network;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

import java.util.Optional;

public class CacheRequestTest {

    // Declare a WebDriver instance to interact with the web browser.
    private ChromeDriver driver;

    @BeforeMethod
    public void setUp() {
        // Set up the WebDriver instance by calling a method named 'cdpBrowserSetup' from the 'DriverConfiguration' class
        driver = DriverConfiguration.cdpBrowserSetup();
    }

    @AfterMethod
    public void tearDown() {
        // Check if the 'driver' variable is not null, indicating that a WebDriver instance exists.
        if (driver != null) {
            // If a WebDriver instance exists, quit/close the browser session.
            driver.quit();
        }
    }

    @Test(priority = 1)
    public void testCacheRequest() {
        // Define the expected title
        String expectedTitle = "Selenium";

        // Get the DevTools instance from the WebDriver
        DevTools devTools = driver.getDevTools();

        // Create a new DevTools session if one doesn't exist
        devTools.createSessionIfThereIsNotOne();

        // Enable the Network domain to handle network-related functionalities
        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));

        // Disable browser cache for subsequent requests
        devTools.send(Network.setCacheDisabled(true));

        // Clear the browser's cache
        devTools.send(Network.clearBrowserCache());

        // Clear browser cookies
        devTools.send(Network.clearBrowserCookies());

        // Add a listener to capture requests served from the cache and print them
        devTools.addListener(Network.requestServedFromCache(), System.out::println);

        // Navigate to the selenium website
        driver.get("https://www.selenium.dev/");

        try {
            // Wait for some time to allow the browser to potentially cache resources
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            // If an InterruptedException occurs during the sleep, throw a RuntimeException
            throw new RuntimeException(e);
        }

        // Navigate again to the selenium website
        driver.get("https://www.selenium.dev/");

        // Disable the network domain
        devTools.send(Network.disable());

        // Get the current title of the web page using the WebDriver
        String actualTitle = driver.getTitle();

        // Assert that the actual title matches the expected title
        Assert.assertEquals(actualTitle, expectedTitle);
    }

}