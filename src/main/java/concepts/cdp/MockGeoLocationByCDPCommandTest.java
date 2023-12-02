package concepts.cdp;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

import java.util.HashMap;

/**
 * @resource <a href="https://applitools.com/blog/selenium-chrome-devtools-protocol-cdp-how-does-it-work/#:~:text=We%20can%20simulate%20things%20like,done%20from%20our%20automated%20tests">...</a>!
 */
public class MockGeoLocationByCDPCommandTest {

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
	public void testMockGeoLocationByCDPCommand() {
		// Store the expected location for comparison
		String expectedLocation = "Sanger, CA 93657, United States";

		/*
			Another way of having Map Object to store geolocation information
			=================================================================
			Map<String, Object> coordinates = Map.of("latitude", 30.3079823, "longitude",
			-97.893803, "accuracy", 1);
		*/

		// Create a HashMap to hold geolocation information
		HashMap<String, Object> location = new HashMap<>();

		// Set latitude, longitude, and accuracy in the HashMap
		location.put("latitude", 36.778259); // Latitude value
		location.put("longitude", -119.417931); // Longitude value
		location.put("accuracy", 1); // Accuracy value

		// Execute the Chrome DevTools Protocol (CDP) command to override the geolocation with the specified values
		driver.executeCdpCommand("Emulation.setGeolocationOverride", location);

		// Navigate to a website that retrieves geolocation information
		driver.get("https://my-location.org/");

		// Retrieve the actual location displayed on the website
		String actualLocation = driver.findElement(By.id("address")).getText();

		// Verify that the actual location matches the expected location
		Assert.assertEquals(actualLocation, expectedLocation);
	}

}