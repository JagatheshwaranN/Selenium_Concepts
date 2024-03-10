package concepts.navigation;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class NavigateToTest {

	// Declare a WebDriver instance to interact with the web browser.
	private WebDriver driver;

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
	public void testNavigateTo() {
		// Declare a variable to hold the URL
		URL url;

		try {
			// Convert a String URI to a URL object
			url = URI.create("https://github.com/").toURL();
		} catch (MalformedURLException e) {
			// Handle the exception by throwing a RuntimeException
			throw new RuntimeException(e);
		}

		// Use WebDriver's navigate().to() method to navigate to the specified URL
		driver.navigate().to(url);

		// Asserts that the title of the current page is equal to the specified title
		Assert.assertEquals(driver.getTitle(), "GitHub: Let’s build from here · GitHub");
	}

}
