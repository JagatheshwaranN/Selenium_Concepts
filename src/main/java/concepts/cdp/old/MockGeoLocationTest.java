package concepts.cdp.old;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v125.emulation.Emulation;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

import java.util.Optional;

/**
 * @resource <a href="https://applitools.com/blog/selenium-chrome-devtools-protocol-cdp-how-does-it-work/#:~:text=We%20can%20simulate%20things%20like,done%20from%20our%20automated%20tests">...</a>!
 */
public class MockGeoLocationTest {

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
	public void testMockGeoLocation() {
		// Store the expected location for comparison
		String expectedLocation = "Sanger, CA 93657, United States";

		// Get the DevTools instance associated with the driver
		DevTools devTools = driver.getDevTools();

		// Create a new DevTools session
		devTools.createSession();

		// Set the mocked geolocation coordinates (latitude, longitude, accuracy)
		devTools.send(
				Emulation.setGeolocationOverride(
						Optional.of(36.778259), // Latitude
						Optional.of(-119.417931), // Longitude
						Optional.of(1) // Accuracy
				)
		);

		// Set the mocked timezone override to US/Central
		devTools.send(Emulation.setTimezoneOverride("US/Central"));

		// Set the mocked locale override to en_us
		devTools.send(Emulation.setLocaleOverride(Optional.of("en_us")));

		// Navigate to a website that retrieves geolocation information
		driver.get("https://my-location.org/");

		// Retrieve the actual location displayed on the website
		String actualLocation = driver.findElement(By.id("address")).getText();

		// Verify that the actual location matches the expected location
		Assert.assertEquals(actualLocation, expectedLocation);
	}

}