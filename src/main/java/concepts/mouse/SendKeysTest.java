package concepts.mouse;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

public class SendKeysTest {

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
	public void testSendKeys() {
		// Define the expected value for the username
		String expectedValue = "google";

		// Open the Google login page
		driver.get("https://accounts.google.com/");

		// Using Actions class to send keys to the identifier field
		new Actions(driver)
				.sendKeys(driver.findElement(By.name("identifier")), "google")
				.perform();

		// Get the actual value of the username field
		String actualValue = driver.findElement(By.name("identifier")).getAttribute("data-initial-value");

		// Assert that the actual value matches the expected value
		Assert.assertEquals(actualValue, expectedValue);
	}
	
}
