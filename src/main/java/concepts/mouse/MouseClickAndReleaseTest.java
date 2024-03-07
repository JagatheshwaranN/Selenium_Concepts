package concepts.mouse;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

public class MouseClickAndReleaseTest {

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
	public void testMouseClickAndRelease() {
		// Define the expected message after the click action
		String expectedMessage = "focused";

		// Load the webpage that demonstrates mouse interactions
		driver.get("https://www.selenium.dev/selenium/web/mouse_interaction.html");

		// Perform a click action on the input element using Actions class
		new Actions(driver)
				.click(driver.findElement(By.xpath("//input[@id='clickable']")))
				.release(driver.findElement(By.xpath("//input[@id='clickable']")))
				.perform();

		// Retrieve the text of the element to verify the resulting message
		String actualMessage = driver.findElement(By.xpath("//strong[@id='click-status']")).getText();

		// Verify if the actual message matches the expected message
		Assert.assertEquals(actualMessage, expectedMessage);
	}

}
