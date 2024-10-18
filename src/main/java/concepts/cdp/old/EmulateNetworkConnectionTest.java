package concepts.cdp.old;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v129.network.Network;
import org.openqa.selenium.devtools.v129.network.model.ConnectionType;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

import java.util.Optional;

/**
 * @resource <a href="https://applitools.com/blog/selenium-chrome-devtools-protocol-cdp-how-does-it-work/#:~:text=We%20can%20simulate%20things%20like,done%20from%20our%20automated%20tests">...</a>!
 */
public class EmulateNetworkConnectionTest {

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
	public void testEmulateNetworkConnection() {
		// Define the expected page load time in milliseconds
		long expectedPageLoadTimeInMilliseconds = 5000;

		// Get the DevTools instance associated with the driver
		DevTools devTools = driver.getDevTools();

		// Create a new DevTools session if one doesn't exist
		devTools.createSessionIfThereIsNotOne();

		// Enable network conditions emulation
		devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));

		// Emulate network conditions (Offline: false, Latency: 100 ms, Download: 6144 Kbps, Upload: 4096 Kbps, Connection Type: 4G)
		devTools.send(Network.emulateNetworkConditions(false, 100, 6144, 4096, Optional.of(ConnectionType.CELLULAR4G), Optional.empty(),Optional.empty(), Optional.empty()));

		// Record the start time before navigating to the website
		long startTime = System.currentTimeMillis();

		// Navigate to the target URL
		driver.get("https://automationbookstore.dev/");

		// Record the end time after the page loads
		long endTime = System.currentTimeMillis();

		// Find an element on the page to verify successful loading
		WebElement testAutomationBook = driver.findElement(By.xpath("//li[@id='pid1']"));

		// Assert that the element is displayed, indicating successful loading
		Assert.assertTrue(testAutomationBook.isDisplayed());

		// Disable network conditions
		devTools.send(Network.disable());

		// Calculate the actual page load time in milliseconds
		long actualPageLoadTimeInMilliseconds = endTime - startTime;

		// Print the actual page load time (for debugging or observation purposes)
		System.out.println(actualPageLoadTimeInMilliseconds);

		// Assert that the actual page load time is greater than the expected page load time
		Assert.assertTrue(actualPageLoadTimeInMilliseconds > expectedPageLoadTimeInMilliseconds);
	}

}