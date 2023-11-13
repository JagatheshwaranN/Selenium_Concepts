package concepts.element;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

import java.time.Duration;

public class TypeIntoElementTest {

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
	public void testTypeIntoElement() {
		String expectedValue = "google";

		// Navigate to the Google Accounts page
		driver.get("https://accounts.google.com/");

		// Use WebDriverWait to wait up to 10 seconds until the expected condition
		// (visibility of the element) is met.
		// This is useful to ensure that the element is present in the DOM and visible
		// on the web page.
		// ExpectedConditions.visibilityOfElementLocated(By) checks if the given element
		// is present in the DOM and visible on the web page, returns the located WebElement
		// once it is visible
		WebElement identifierInput = new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.presenceOfElementLocated(By.name("identifier")));

		// Type "google" into the input field
		identifierInput.sendKeys("google");

		// Retrieve the attribute "data-initial-value" from the same element
		String actualValue = identifierInput.getAttribute("data-initial-value");

		// Assert that the actual value matches the expected value
		Assert.assertEquals(actualValue, expectedValue);
	}

}
