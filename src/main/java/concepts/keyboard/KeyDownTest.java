package concepts.keyboard;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

public class KeyDownTest {

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
	public void testKeyDown() {
		// Define the expected value to be entered into the input field
		String expectedValue = "APP";

		// Navigate to the provided URL
		driver.get("https://www.selenium.dev/selenium/web/single_text_input.html");

		// Simulate a key press action by holding down the Shift key and sending "app" keys
		new Actions(driver).keyDown(Keys.SHIFT).sendKeys("app").perform();

		// Find the input field element by its ID
		WebElement input = driver.findElement(By.id("textInput"));

		// Get the actual value present in the input field
		String actualValue = input.getAttribute("value");

		// Assert that the actual value matches the expected value
		Assert.assertEquals(actualValue, expectedValue);
	}

}
