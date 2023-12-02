package concepts.cdp;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v119.network.Network;
import org.openqa.selenium.devtools.v119.network.model.Headers;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @resource <a href="https://applitools.com/blog/selenium-chrome-devtools-protocol-cdp-how-does-it-work/#:~:text=We%20can%20simulate%20things%20like,done%20from%20our%20automated%20tests">...</a>!
 */
public class BasicAuthenticationTest {

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
	public void testBasicAuthentication() {
		// Define the expected success message for authentication
		String expectedMessage = "Congratulations! You must have the proper credentials.";

		// Get the DevTools instance associated with the driver
		DevTools devTools = driver.getDevTools();

		// Create a new DevTools session if one doesn't exist
		devTools.createSessionIfThereIsNotOne();

		// Enable network monitoring to modify HTTP headers
		devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));

		// Create a map to hold custom headers (in this case, 'Authorization' header for basic authentication)
		Map<String, Object> header = new HashMap<>();
		header.put("Authorization", "Basic YWRtaW46YWRtaW4="); // 'admin:admin' credentials encoded in Base64

		// Set the extra HTTP headers for network requests to include the basic authentication header
		devTools.send(Network.setExtraHTTPHeaders(new Headers(header)));

		// Navigate to the target URL that requires basic authentication
		driver.get("https://the-internet.herokuapp.com/basic_auth");

		// Extract the page content from the element with the specified XPath selector and store it actual message
		String actualMessage = driver.findElement(By.xpath("//div[@class='example']//p")).getText();

		// Disable network monitoring after test execution
		devTools.send(Network.disable());

		// Verify that the actual message content matches the expected message
		Assert.assertEquals(actualMessage, expectedMessage);
	}

}