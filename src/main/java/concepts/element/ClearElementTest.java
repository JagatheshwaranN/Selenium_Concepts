package concepts.element;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

public class ClearElementTest {

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
	public void testClearElement() {
		// Define the expected error message
		String expectedMessage = "Please enter your email";

		// Navigate to the login page
		driver.get("https://admin-demo.nopcommerce.com/login");

		// Find the email input field using a CSS selector
		WebElement email = driver.findElement(By.cssSelector("input[name='Email']"));

		// Clear the email input field (if needed)
		email.clear();

		// Click on the email input field
		email.click();

		// Simulate pressing the Enter key using the Actions class
		new Actions(driver).keyDown(Keys.ENTER).perform();

		// Find the error message element using XPath
		String actualMessage = driver.findElement(By.xpath("//span[@id='Email-error']")).getText();

		// Assert that the actual error message matches the expected error message
		Assert.assertEquals(actualMessage, expectedMessage);
	}

}
