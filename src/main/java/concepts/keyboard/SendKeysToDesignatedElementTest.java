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

public class SendKeysToDesignatedElementTest {

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
	public void testSendKeysToDesignatedElement() {
		// Define the expected value to be entered into the input field
		String expectedValue = "Selenium";

		// Navigate to the specified URL
		driver.get("https://www.selenium.dev/selenium/web/single_text_input.html");

		// Find the input field element by its ID
		WebElement input = driver.findElement(By.id("textInput"));

		// Simulate typing "Selenium" into the specified 'input' element using Actions class
		new Actions(driver)
				// Perform 'sendKeys' action by targeting the 'input' WebElement and typing "Selenium"
				.sendKeys(input, "Selenium")
				// Perform the action sequence
				.perform();

		// Get the actual value present in the input field after typing "Chrome"
		String actualValue = input.getAttribute("value");

		// Assert that the actual value matches the expected value
		Assert.assertEquals(actualValue, expectedValue);
	}

}
