package concepts.element;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

public class GetElementCSSPropertyTest {

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
	public void testGetElementCSSProperty() {
		// Expected CSS property values
		String expectedElementColor = "rgba(0, 0, 0, 1)";
		String expectedElementBGColor = "rgba(240, 240, 240, 1)";

		// Navigate to the inputs page
		driver.get("https://www.selenium.dev/selenium/web/inputs.html");

		// Retrieve actual CSS property values from the input element
		String actualElementColor = driver.findElement(By.cssSelector("input[name='color_input']")).getCssValue("color");
		String actualElementBGColor = driver.findElement(By.cssSelector("input[name='color_input']"))
				.getCssValue("background-color");

		// Assert that actual CSS property values match the expected values
		Assert.assertEquals(actualElementColor, expectedElementColor, "Color property value mismatch");
		Assert.assertEquals(actualElementBGColor, expectedElementBGColor, "Background color property value mismatch");
	}

}
