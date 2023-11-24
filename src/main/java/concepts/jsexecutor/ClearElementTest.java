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
		// The text to be entered into the input field
		String text = "Selenium";

		// The expected value after clearing the input field
		String expectedValue = "";

		// Navigate to the HTML file containing the input field
		driver.get("file:///D:/Environment_Collection/Eclipse_Env/Workspace/Selenium_Concepts/src/main/resources/supportFiles/DisabledElement.html");

		// Find the input field by its ID and enter text into it
		WebElement input = driver.findElement(By.id("myText"));
		input.sendKeys(text);

		// Create a JavaScriptExecutor instance to execute JavaScript code
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

		// Execute JavaScript code to clear the input field value
		jsExecutor.executeScript("arguments[0].value='';", input);

		// Get the actual value from the input field after clearing it
		String actualValue = input.getAttribute("value");

		// Assert whether the actual value matches the expected empty value
		Assert.assertEquals(actualValue, expectedValue);
	}

}
