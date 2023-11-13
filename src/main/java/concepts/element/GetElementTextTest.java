package concepts.element;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

public class GetElementTextTest {

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
	public void testGetElementText() {
		// Expected element text
		String expectedElementText = "Testing Inputs";

		// Navigate to the inputs page
		driver.get("https://www.selenium.dev/selenium/web/inputs.html");

		// Find the h1 element using the tagName selector
		WebElement h1Element = driver.findElement(By.tagName("h1"));

		// Retrieve the text of the h1 element
		String actualElementText = h1Element.getText();

		// Assert that the actual text matches the expected text
		Assert.assertEquals(actualElementText, expectedElementText);
	}

}
