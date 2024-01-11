package concepts.cdp.cdp_endpoint;

import com.google.common.collect.ImmutableMap;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chromium.HasCdp;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class BasicAuthenticationTest {

    // Declare a WebDriver instance to interact with the web browser.
    private ChromeDriver driver;

    @BeforeMethod
    public void setUp() {
        // Set up the WebDriver instance by calling a method named 'cdpBrowserSetup' from the 'DriverConfiguration' class
        driver = DriverConfiguration.cdpBrowserSetup();
    }

    @Test
    public void testBasicAuthentication() {
        // Define the expected success message
        String expectedMessage = "Congratulations! You must have the proper credentials.";

        // Enable the Network domain in Chrome DevTools Protocol (CDP)
        ((HasCdp) driver).executeCdpCommand("Network.enable", new HashMap<>());

        // Encode the username and password for Basic Authentication
        String encodedAuthentication = Base64.getEncoder().encodeToString("admin:admin".getBytes());

        // Create headers with Basic Authentication information
        Map<String, Object> headers = ImmutableMap.of("headers", ImmutableMap.of("authorization", "Basic " + encodedAuthentication));

        // Set extra HTTP headers including Basic Authentication headers
        ((HasCdp) driver).executeCdpCommand("Network.setExtraHTTPHeaders", headers);

        // Navigate to a URL that requires Basic Authentication
        driver.get("https://the-internet.herokuapp.com/basic_auth");

        // Retrieve the actual message displayed on the page
        String actualMessage = driver.findElement(By.tagName("p")).getText();

        // Assert that the actual message matches the expected message
        Assert.assertEquals(actualMessage, expectedMessage);
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
