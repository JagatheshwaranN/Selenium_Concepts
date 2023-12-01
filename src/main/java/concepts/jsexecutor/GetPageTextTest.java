package concepts.jsexecutor;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

public class GetPageTextTest {

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
	public void testGetPageText() {
		// Navigate to the Google homepage
		driver.get("https://www.google.com/");

		// Find the first element with the specified XPath
		WebElement input = driver.findElement(By.xpath("(//a[@class='gb_E'])[1]"));

		// Get the expected value by retrieving the text of the found element
		String expectedValue = input.getText();

		// Create a JavaScript Executor instance
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

		// Use JavascriptExecutor to get the entire text content of the page
		String actualValue = jsExecutor.executeScript("return document.documentElement.innerText;").toString();

		// Perform assertion to check if the actual page text contains the expected value
		Assert.assertTrue(actualValue.contains(expectedValue));
	}

}
