package concepts.mouse;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

import java.time.Duration;

public class MouseMoveToElementTest {

	// Declare a WebDriver instance to interact with the web browser.
	private WebDriver driver;

	// Define a constant duration for the maximum wait time, set to 10 seconds
	private static final Duration WAIT_TIMEOUT = Duration.ofSeconds(10);

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
	public void testMoveToElement() {
		// Define the expected value for the 'move-status' element
		String expectedValue = "hovered";

		// Open the web page with the mouse interaction demo
		driver.get("https://www.selenium.dev/selenium/web/mouse_interaction.html");

		// Create a WebDriverWait object for waiting for element visibility
		WebDriverWait wait = new WebDriverWait(driver, WAIT_TIMEOUT);

		// Create an Actions object for performing actions on the page
		Actions actions = new Actions(driver);

		// Locate the element to be hovered over
		WebElement element = driver.findElement(By.xpath("//input[@id='hover']"));

		// Perform a mouse move action to hover over the element
		actions.moveToElement(element).perform();

		// Wait until the expected value appears in the 'move-status' element
		wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//strong[@id='move-status']"), expectedValue));

		// Fetch the actual value from the element after the hover action
		String actualValue = driver.findElement(By.xpath("//strong[@id='move-status']")).getText();

		// Assert that the actual value matches the expected value
		Assert.assertEquals(actualValue, expectedValue);
	}

}
