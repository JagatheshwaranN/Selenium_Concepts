package concepts.cdp.cdp_api;

import com.google.common.collect.ImmutableMap;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v142.network.Network;
import org.openqa.selenium.devtools.v142.network.model.Headers;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

import java.util.Base64;
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

	@Test
	public void testBasicAuthentication() {
		// Define the expected success message for authentication
		String expectedMessage = "Congratulations! You must have the proper credentials.";

		// Get the DevTools instance associated with the driver
		DevTools devTools = driver.getDevTools();

		// Create a new DevTools session if one doesn't exist
		devTools.createSessionIfThereIsNotOne();

		// Enable network monitoring to modify HTTP headers
		devTools.send(Network.enable(Optional.of(10000), Optional.of(10000), Optional.of(10000), Optional.empty(), Optional.empty()));

		// Encode the username and password for Basic Authentication
		String encodedAuthentication = Base64.getEncoder().encodeToString("admin:admin".getBytes());

		// Create headers with Basic Authentication information
		Map<String, Object> headers = ImmutableMap.of("Authorization", "Basic " + encodedAuthentication);

		// Set extra HTTP headers including Basic Authentication headers
		devTools.send(Network.setExtraHTTPHeaders(new Headers(headers)));

		// Navigate to a URL that requires Basic Authentication
		driver.get("https://the-internet.herokuapp.com/basic_auth");

		// Retrieve the actual message displayed on the page
		String actualMessage = driver.findElement(By.tagName("p")).getText();

		// Assert that the actual message matches the expected message
		Assert.assertEquals(actualMessage, expectedMessage);

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