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

public class MouseDoubleClickTest {

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
	public void testMouseDoubleClick() {
		// Define the expected alert message
		String expectedValue = "You double clicked me.. Thank You..";

		// Load the webpage containing the button for double-click action
		driver.get("https://demo.guru99.com/test/simple_context_menu.html");

		// Find the button element that triggers the alert on double-click
		WebElement dblClkBtn = driver.findElement(By.xpath("//button[contains(text(),'Double-Click Me To See Alert')]"));

		// Perform a double click action on the button
		new Actions(driver).doubleClick(dblClkBtn).perform();

		// Set up a WebDriverWait for waiting for the alert to be present
		WebDriverWait wait = new WebDriverWait(driver, WAIT_TIMEOUT);

		// Wait for the alert to be present
		wait.until(ExpectedConditions.alertIsPresent());

		// Switch to the alert
		Alert alert = driver.switchTo().alert();

		// Get the text of the alert
		String actualValue = alert.getText();

		// Verify if the actual alert text matches the expected text
		Assert.assertEquals(actualValue, expectedValue);
	}

}
