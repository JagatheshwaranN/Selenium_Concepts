package concepts.jsexecutor;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

public class RefreshPageType1Test {

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
	public void testRefreshPageType1() {
		// Set the expected value (empty string) for input field after refresh
		String expectedValue = "";

		// Open the specified URL
		driver.get("https://www.selenium.dev/selenium/web/single_text_input.html");

		// Locate the WebElement with ID 'textInput'
		WebElement input = driver.findElement(By.id("textInput"));

		// Simulate typing "Automation!" into the input field
		new Actions(driver).sendKeys(input, "Automation!").perform();

		// Create the JavascriptExecutor instance
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

		// Execute JavaScript to reload the current page
		jsExecutor.executeScript("document.location.reload()");

		// Re-locate the input field after the page refresh
		input = driver.findElement(By.id("textInput"));

		// Get the actual value of the input field
		String actualValue = input.getAttribute("value");

		// Verify that the actual value of the input field is empty
		Assert.assertEquals(actualValue, expectedValue);
	}

}
