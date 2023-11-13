package concepts.element;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

public class GetElementAttributeTest {

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
	public void testGetElementAttribute() {
		// Define the expected element values
		String expectedElementValue = "input with no type";
		String expectedElementName = "no_type";

		// Navigate to the inputs page
		driver.get("https://www.selenium.dev/selenium/web/inputs.html");

		// Find the input element using a CSS selector
		WebElement inputElement = driver.findElement(By.cssSelector("input[name='no_type']"));

		// Retrieve attribute values
		String actualElementValue = inputElement.getAttribute("value");
		String actualElementName = inputElement.getAttribute("name");

		// Assert that actual attribute values match the expected attribute values
		Assert.assertEquals(actualElementValue, expectedElementValue);
		Assert.assertEquals(actualElementName, expectedElementName);
	}

}
