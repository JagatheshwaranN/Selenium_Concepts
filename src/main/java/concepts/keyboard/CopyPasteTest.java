package concepts.keyboard;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

public class CopyPasteTest {

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
	public void testCopyPaste() {
		// Define the expected value to be entered into the input field
		String expectedValue = "AutomationAutomation!";

		// Navigate to the specified URL
		driver.get("https://www.selenium.dev/selenium/web/single_text_input.html");

		// Find the input field element by its ID
		WebElement input = driver.findElement(By.id("textInput"));

		// Determine the command key (CMD for macOS, CTRL for other platforms)
		Keys cmdCtrl = Platform.getCurrent().is(Platform.MAC) ? Keys.COMMAND : Keys.CONTROL;

		// Use Actions class to perform a series of keyboard actions on the 'input' element
		new Actions(driver)
				// Type "Automation!" into the 'input' element
				.sendKeys(input, "Automation!")
				// Move the cursor one position to the left (to the exclamation mark)
				.sendKeys(Keys.ARROW_LEFT)
				// Hold down the Shift key
				.keyDown(Keys.SHIFT)
				// Move the cursor one line up while holding Shift (to capitalize the exclamation mark)
				.sendKeys(Keys.ARROW_UP)
				// Release the Shift key
				.keyUp(Keys.SHIFT)
				// Hold down the command (CMD) or control (CTRL) key
				.keyDown(cmdCtrl)
				// Type "xvv"
				.sendKeys("xvv")
				// Release the command (CMD) or control (CTRL) key
				.keyUp(cmdCtrl)
				// Perform the sequence of actions
				.perform();

		// Get the actual value present in the 'input' field after performing actions
		String actualValue = input.getAttribute("value");

		// Assert that the actual value matches the expected value
		Assert.assertEquals(actualValue, expectedValue);
	}

}
