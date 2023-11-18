package concepts.waits.common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

import java.time.Duration;

public class PageLoadWaitTest {

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
	public void testPageLoadWait() {
		// Set the page load timeout
		driver.manage().timeouts().pageLoadTimeout(WAIT_TIMEOUT);

		// Navigate to the bookstore website
		driver.get("https://automationbookstore.dev/");

		// Find the bookstore title element
		WebElement bookStoreTitle = driver.findElement(By.cssSelector("#page-title"));

		// Assert if the bookstore title is displayed
		Assert.assertTrue(bookStoreTitle.isDisplayed());
	}

}
