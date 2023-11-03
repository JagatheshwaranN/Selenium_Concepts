package scenarios;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v114.network.Network;
import org.openqa.selenium.devtools.v114.network.model.Headers;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class HandleProxyTest {

    // Declare a WebDriver instance to interact with the web browser.
    public WebDriver driver;

    // Define a map to store HTTP headers.
    public Map<String, Object> header;

    // Store basic authentication credentials as a string.
    public String basicAuthentication;

    // Create a DevTools instance for Chrome DevTools interactions.
    public DevTools devTools;

    // Define username and password for basic authentication.
    public String username = "admin";
    public String password = "admin";

    @BeforeMethod
    public void setUp() {
        // Set up the WebDriver instance by calling a method named 'browserSetup' from the 'DriverConfiguration' class
        driver = DriverConfiguration.browserSetup();
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
    public void testProxyAuthentication() {
        // Navigate to a URL that includes basic authentication credentials
        driver.get("http://admin:admin@the-internet.herokuapp.com/basic_auth");

        // Locate an <h3> element with the text "Basic Auth" using XPath and retrieve its text content
        String result = driver.findElement(By.xpath("//h3[text()='Basic Auth']")).getText();

        // Assert that the retrieved text matches the expected value ("Basic Auth").
        Assert.assertEquals(result, "Basic Auth");
    }

    @Test(priority = 2)
    public void testProxyUsingChromeDevTool() {
        // Get the DevTools object associated with the 'driver' instance (assuming 'driver' is a ChromeDriver)
        devTools = ((ChromeDriver) driver).getDevTools();

        // Create a DevTools session for the 'devTools' object
        devTools.createSession();

        // Send a command to enable the Network domain in the DevTools session
        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));

        // Create a HashMap named 'header' to store HTTP headers
        header = new HashMap<>();

        // Create basic authentication credentials by encoding the username and password in Base64
        basicAuthentication = "Basic " + new String(Base64.getEncoder()
                .encode(String.format("%s:%s", username, password).getBytes()));

        // Add the "Authorization" header to the 'header' HashMap with the basic authentication credentials
        header.put("Authorization", basicAuthentication);

        // Set extra HTTP headers with Basic Authentication.
        devTools.send(Network.setExtraHTTPHeaders(new Headers(header)));

        // Navigate to a URL that includes basic authentication credentials
        driver.get("http://admin:admin@the-internet.herokuapp.com/basic_auth");

        // Locate an <h3> element with the text "Basic Auth" using XPath and retrieve its text content
        String result = driver.findElement(By.xpath("//h3[text()='Basic Auth']")).getText();

        // Assert that the retrieved text matches the expected value ("Basic Auth").
        Assert.assertEquals(result, "Basic Auth");
    }

}
