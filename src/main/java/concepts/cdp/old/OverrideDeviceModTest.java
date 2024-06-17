package concepts.cdp.old;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v125.emulation.Emulation;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

import java.util.Optional;

/**
 * @resource <a href="https://applitools.com/blog/selenium-chrome-devtools-protocol-cdp-how-does-it-work/#:~:text=We%20can%20simulate%20things%20like,done%20from%20our%20automated%20tests">...</a>!
 */
public class OverrideDeviceModTest {

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
	public void testOverrideDeviceMod() {
		// Get the DevTools instance associated with the driver
		DevTools devTools = driver.getDevTools();

		// Create a new DevTools session
		devTools.createSession();

		// Send a command to set the device metrics override
		devTools.send(Emulation.setDeviceMetricsOverride(
				400, 900, // Width and height of the device viewport
				70, // Device scale factor (can be used for high-DPI devices)
				true, // Whether to emulate a mobile device (true/false)
				Optional.empty(), Optional.empty(), Optional.empty(), // Other parameters like mobile user agent
				Optional.empty(), Optional.empty(), Optional.empty(), // More optional parameters
				Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty()// Additional optional parameters
		));

		// Load the Google homepage after setting the device metrics override
		driver.get("https://google.com/");
	}

}