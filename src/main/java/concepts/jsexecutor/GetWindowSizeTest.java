package concepts.jsexecutor;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;


public class GetWindowSizeTest {

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
	public void testGetWindowSize() {
		// Define expected window height and width
		int expectedHeight = 607;
		int expectedWidth = 1366;

		// Navigate to Google homepage
		driver.get("https://www.google.com/");

		// Create a JavaScriptExecutor instance to execute JavaScript code
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

		// Execute JavaScript to retrieve the window's inner height
		long actualHeight = (long) jsExecutor.executeScript("return window.innerHeight;");

		// Execute JavaScript to retrieve the window's inner width
		long actualWidth = (long) jsExecutor.executeScript("return window.innerWidth;");

		// Verify that actual height matches the expected height
		Assert.assertEquals(actualHeight, expectedHeight);

		// Verify that actual width matches the expected width
		Assert.assertEquals(actualWidth, expectedWidth);
	}

}
