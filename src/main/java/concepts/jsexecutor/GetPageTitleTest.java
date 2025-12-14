package concepts.jsexecutor;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

import java.util.Objects;

public class GetPageTitleTest {

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
	public void testGetPageTitle() {
		// Define the expected page title
		String expectedValue = "Google";

		// Navigate to the Google homepage
		driver.get("https://www.google.com/");

		// Create a JavascriptExecutor instance
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

		// Execute JavaScript to retrieve the page title
		String actualValue = Objects.requireNonNull(jsExecutor.executeScript("return document.title")).toString();

		// Verify that the actual page title matches the expected value
		Assert.assertEquals(actualValue, expectedValue);
	}

}
