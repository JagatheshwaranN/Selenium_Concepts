package concepts.mouse;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

public class MouseRightClickTest {


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
	public void testMouseRightClick() {
		// Define the expected value for the context menu option
		String expectedValue = "Copy";

		// Open the web page with the context menu
		driver.get("https://demo.guru99.com/test/simple_context_menu.html");

		// Create an Actions object for performing actions on the page
		Actions actions = new Actions(driver);

		// Perform a right-click action on the specified element
		actions.contextClick(driver.findElement(By.xpath("//span[contains(@class,'context-menu-one')]"))).perform();

		// Retrieve the text from the context menu option to compare with the expected value
		String actualValue = driver.findElement(By.cssSelector(".context-menu-item.context-menu-icon.context-menu-icon-copy")).getText();

		// Assertion to verify that the actual value matches the expected value
		Assert.assertEquals(actualValue, expectedValue);
	}

}
