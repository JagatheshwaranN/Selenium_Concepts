package concepts.cdp.cdp_api;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v142.network.Network;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

import java.util.Optional;

public class SetCookiesTest {

    // Declare a WebDriver instance to interact with the web browser.
    private ChromeDriver driver;

    @BeforeMethod
    public void setUp() {
        // Set up the WebDriver instance by calling a method named 'cdpBrowserSetup' from the 'DriverConfiguration' class
        driver = DriverConfiguration.cdpBrowserSetup();
    }

    @Test
    public void setCookiesTest() {
        // Define the expected value of the cookie
        String expectedValue = "automation";

        // Access the DevTools interface for interacting with browser features
        DevTools devTools = driver.getDevTools();

        // Ensure a DevTools session is active
        devTools.createSessionIfThereIsNotOne();

        // Set the cookie using DevTools protocol
        // - "test": Name of the cookie
        // - "automation": Value of the cookie
        // - Optional.of("www.selenium.dev"): Domain for which the cookie is valid
        // - Optional.of(true): Indicates that the cookie is accessible across subdomains
        devTools.send(Network.setCookie(
                "test", "automation",
                Optional.empty(),  // Optional path for the cookie
                Optional.of("www.selenium.dev"),
                Optional.empty(),  // Optional expiration time
                Optional.of(true), // Secure flag (HTTPS only)
                Optional.empty(),  // Optional HTTP-only flag
                Optional.empty(),  // Optional sameSite policy
                Optional.empty(),  // Optional priority
                Optional.empty(),  // Optional expiration time (in seconds)
                Optional.empty(),  // Optional expiration time (in milliseconds)
                Optional.empty(),  // Optional whether to create the cookie unconditionally
                Optional.empty(),  // Optional session cookie flag
                Optional.empty()   // Optional expiration time (in seconds since epoch)
        ));

        // Navigate to the website to trigger cookie usage
        driver.get("https://www.selenium.dev");

        // Retrieve the cookie named "test"
        Cookie test = driver.manage().getCookieNamed("test");

        // Assert that the value of the retrieved cookie matches the expected value
        Assert.assertEquals(test.getValue(), expectedValue);

        // Disable network monitoring after test execution
        devTools.send(Network.disable());
    }

    @AfterMethod
    public void tearDown() {
        // Check if the 'driver' variable is not null, indicating that a WebDriver instance exists.
        if (driver != null) {
            // If a WebDriver instance exists, quit/close the browser session.
            driver.quit();
        }
    }

}
