package concepts.cdp.cdp_endpoint;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chromium.HasCdp;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

import java.util.HashMap;

public class SetCookiesTest {

    // Declare a WebDriver instance to interact with the web browser.
    private ChromeDriver driver;

    @BeforeMethod
    public void setUp() {
        // Set up the WebDriver instance by calling a method named 'cdpBrowserSetup' from the 'DriverConfiguration' class
        driver = DriverConfiguration.cdpBrowserSetup();
    }

    @Test
    public void setCookies() {
        // Define the expected value for the cookie
        String expectedValue = "automation";

        // Create a HashMap to store cookie properties
        HashMap<String, Object> cookie = new HashMap<>();

        // Set cookie attributes:
        cookie.put("name", "test");      // Name of the cookie
        cookie.put("value", "automation");  // Value of the cookie
        cookie.put("domain", "www.example.com");  // Domain for which the cookie is valid
        cookie.put("secure", true);     // Indicates the cookie should only be sent over HTTPS

        // Use Chrome DevTools Protocol (CDP) to execute the 'Network.setCookie' command
        ((HasCdp) driver).executeCdpCommand("Network.setCookie", cookie);

        // Navigate to a specific URL to apply the cookie
        driver.get("http://www.example.com/");

        // Retrieve the 'test' cookie from the browser's cookie storage
        Cookie test = driver.manage().getCookieNamed("test");

        // Assert that the retrieved cookie value matches the expected value
        Assert.assertEquals(test.getValue(), expectedValue);
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
