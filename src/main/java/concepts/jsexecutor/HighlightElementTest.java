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

public class HighlightElementTest {

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
	public void testHighlightElement() {
		// Define the expected background value
		String expectedValue = "rgb(255, 255, 0) none repeat scroll 0% 0% / auto padding-box border-box";

		// Load the web page
		driver.get("file:///D:/Environment_Collection/Eclipse_Env/Workspace/Selenium_Concepts/src/main/resources/supportFiles/DisabledElement.html");

		// Find the element to highlight
		WebElement input = driver.findElement(By.id("myText"));

		// Create the JavascriptExecutor instance
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

		// Use JavaScript to modify the element's style
		jsExecutor.executeScript(
				"arguments[0].setAttribute('style', 'background: yellow; border: 2px solid green;')", input);

		// Get the actual background value of the element
		String actualValue = input.getCssValue("background");

		// Perform assertion to compare expected and actual values
		Assert.assertEquals(actualValue, expectedValue);
	}

}
