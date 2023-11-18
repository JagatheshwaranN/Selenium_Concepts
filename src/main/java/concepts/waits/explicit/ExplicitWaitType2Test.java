package concepts.waits.explicit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

import java.time.Duration;

public class ExplicitWaitType2Test {

	// Declare a WebDriver instance to interact with the web browser.
	private WebDriver driver;

	// Define a constant duration for the maximum wait time, set to 5 seconds
	private static final Duration WAIT_TIMEOUT = Duration.ofSeconds(5);

	// Define a constant duration for the maximum sleep time, set to 1.5 seconds
	private static final Duration WAIT_SLEEP = Duration.ofMillis(1500);


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
	public void testExplicitWaitType2() {
		// Define the expected value
		String expectedValue = "Dashboard";

		// Load the login page
		driver.get("https://admin-demo.nopcommerce.com/login");

		// Wait for the login button to be clickable
		WebElement loginButton = new WebDriverWait(driver, WAIT_TIMEOUT, WAIT_SLEEP)
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".button-1.login-button")));

		// Click on the login button
		loginButton.click();

		// Retrieve the text of the content header after clicking the login button
		String actualValue = driver.findElement(By.xpath("//div[@class='content-header']//h1")).getText();

		// Assert whether the actual value matches the expected value
		Assert.assertEquals(actualValue, expectedValue);
	}

}
