package concepts.keyboard;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

public class KeyChordTest {

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
	public void testKeyChord() {
		// Define a variable to hold the dynamic key value (e.g., "CONTROL")
		String dynamicKey = "CONTROL";

		// Navigate to the specified URL
		driver.get("https://admin-demo.nopcommerce.com/login");

		// Find the WebElement representing the email input field by its ID
		WebElement emailId = driver.findElement(By.id("Email"));

		// Convert the dynamicKey string to the corresponding Keys enum
		Keys modifierKey = Keys.valueOf(dynamicKey.toUpperCase());

		// Use Keys.chord with the modifier key and "a"
		emailId.sendKeys(Keys.chord(modifierKey, "a"));

		// Use Keys.DELETE to delete the selected text (clear the input field)
		emailId.sendKeys(Keys.DELETE);
	}

}
