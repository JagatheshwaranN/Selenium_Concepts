package concepts.mouse;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

public class MouseActionResetTest {

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
	public void testMouseActionReset() {
		// Load the webpage for mouse interactions
		driver.get("https://www.selenium.dev/selenium/web/mouse_interaction.html");

		// Initialize Actions class instance
		Actions actions = new Actions(driver);

		// Find the element to interact with
		WebElement clickable = driver.findElement(By.id("clickable"));

		// Perform a series of actions: click and hold, press SHIFT, and send 'a'
		actions.clickAndHold(clickable).keyDown(Keys.SHIFT).sendKeys("a").perform();

		// Verify the state after the initial actions
		Assert.assertEquals("A", String.valueOf(clickable.getAttribute("value").charAt(0)));
		Assert.assertEquals("a", String.valueOf(clickable.getAttribute("value").charAt(1)));

		// Reset the input state (Simulating a reset - Note: WebDriver might not provide a direct reset method)
		((RemoteWebDriver) driver).resetInputState(); // Resetting input state (May not work as expected in all scenarios)

		// Perform another action after the "reset" to simulate further input
		actions.sendKeys("a").perform();

		// Verify the state after the "reset" and additional action
		Assert.assertEquals("A", String.valueOf(clickable.getAttribute("value").charAt(0)));
		Assert.assertEquals("a", String.valueOf(clickable.getAttribute("value").charAt(1)));
	}

}
