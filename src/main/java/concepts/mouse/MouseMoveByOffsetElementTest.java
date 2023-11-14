package concepts.mouse;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

public class MouseMoveByOffsetElementTest {

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
	public void testMouseMoveByOffsetElement() {
		// Define the expected value for the text of the 'absolute-location' element
		String expectedValue = "139, 587";

		// Open the web page with the mouse interaction demo
		driver.get("https://www.selenium.dev/selenium/web/mouse_interaction.html");

		// Create an Actions object for performing actions on the page
		Actions actions = new Actions(driver);

		// Perform a mouse move action with an offset of 20 pixels horizontally
		actions.moveToElement(driver.findElement(By.id("mouse-tracker")), 20, 0).perform();

		// Retrieve the text from the element with the xpath "//span[@id='absolute-location']"
		String actualValue = driver.findElement(By.xpath("//span[@id='absolute-location']")).getText();

		// Assert that the actual value matches the expected value
		Assert.assertEquals(actualValue, expectedValue);
	}

}
