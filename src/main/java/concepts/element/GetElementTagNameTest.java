package concepts.element;

import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

public class GetElementTagNameTest {

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
	public void testGetElementTagName() {
		// Expected tag name
		String expectedElementTag = "input";

		// Navigate to the inputs page
		driver.get("https://www.selenium.dev/selenium/web/inputs.html");

		// Find the input element using a CSS selector
		WebElement inputElement = driver.findElement(By.cssSelector("input[name='number_input']"));

		// Retrieve the tag name of the input element
		String actualElementTag = inputElement.getTagName();

		// Assert that the actual tag name matches the expected tag name
		Assert.assertEquals(actualElementTag, expectedElementTag);
	}

}
