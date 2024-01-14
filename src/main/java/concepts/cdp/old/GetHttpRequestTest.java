package concepts.cdp.old;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v119.network.Network;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @resource <a href="https://applitools.com/blog/selenium-chrome-devtools-protocol-cdp-how-does-it-work/#:~:text=We%20can%20simulate%20things%20like,done%20from%20our%20automated%20tests">...</a>!
 */
public class GetHttpRequestTest {

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
	public void testGetHttpRequest() {
		// Define the expected number of requests
		int expectedRequestCount = 20;

		// Get the DevTools instance associated with the driver
		DevTools devTools = driver.getDevTools();

		// Create a new DevTools session if one doesn't exist
		devTools.createSessionIfThereIsNotOne();

		// Enable network conditions monitoring
		devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));

		// Create a counter to track the number of requests
		AtomicInteger requestCounter = new AtomicInteger(0); // Counter to track requests

		// Add a listener to capture requests
		devTools.addListener(Network.requestWillBeSent(), request -> {
			requestCounter.incrementAndGet(); // Increment the counter for each request
			System.out.println("Request URI     : " + request.getRequest().getUrl());
			System.out.println("Request Method  : " + request.getRequest().getMethod());
			System.out.println("Request Redirect: " + request.getRedirectResponse().isPresent());
		});

		// Navigate to the target URL
		driver.get("https://automationbookstore.dev/");

		// Disable network conditions monitoring after the page loads
		devTools.send(Network.disable());

		// Get the actual count of requests captured
		int actualRequestCount = requestCounter.get();

		// Assert that the actual request count matches the expected count
		Assert.assertEquals(actualRequestCount, expectedRequestCount, "Unexpected number of requests");
	}


}