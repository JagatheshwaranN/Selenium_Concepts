package concepts.navigation;

import junit.framework.Assert;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

public class MovePageForwardTestCase {

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
	public void movePageForward() {
		// Navigates the driver to the GitHub website
		driver.get("https://github.com/");

		// Navigates to the Selenium website
		driver.navigate().to("https://www.selenium.dev/");

		// Navigates back to the previous page
		driver.navigate().back();

		// Navigates forward to the next page
		driver.navigate().forward();

		// Asserts that the title of the current page is equal to "Selenium"
		Assert.assertEquals(driver.getTitle(), "Selenium");
	}

}
