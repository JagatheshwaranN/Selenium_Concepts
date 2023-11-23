package concepts.keyboard;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

public class SendKeysToActiveElementTest {

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
	public void testSendKeysToActiveElement() {
		// Define the expected value to be entered into the input field
		String expectedValue = "Selenium";

		// Navigate to the specified URL
		driver.get("https://www.selenium.dev/selenium/web/single_text_input.html");

		// Simulate typing "Selenium" without any modifier keys
		new Actions(driver).sendKeys("Selenium").perform();

		// Find the input field element by its ID
		WebElement input = driver.findElement(By.id("textInput"));

		// Get the actual value present in the input field after typing "Chrome"
		String actualValue = input.getAttribute("value");

		// Assert that the actual value matches the expected value
		Assert.assertEquals(actualValue, expectedValue);
	}

}
