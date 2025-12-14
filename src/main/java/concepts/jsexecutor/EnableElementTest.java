package concepts.jsexecutor;

import junit.framework.Assert;
import org.openqa.selenium.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

import java.io.File;

public class EnableElementTest {

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
	public void testEnableElement() {
		// Initialize variables for test input and expected values
		String value = "Selenium";
		String expectedValue1 = ""; // Empty string for disabled state
		String expectedValue2 = "Selenium"; // Value for enabled state

        // URL of the HTML file
        String filePath = "src/main/resources/supportFiles/DisabledElement.html";

        // Open the webpage
        driver.get(new File(filePath).toURI().toString());

		// Locate the input element with ID "myText"
		WebElement input = driver.findElement(By.id("myText"));

		// Enter the text "Selenium" into the input field
		input.sendKeys(value);

		// Click the button using XPath selector to trigger an action
		driver.findElement(By.xpath("//button")).click();

		// Retrieve the text from the input field and store it in 'actualValue1'
		String actualValue1 = input.getText();

		// Verify that the element remains disabled after the button clicks
		Assert.assertEquals(expectedValue1, actualValue1);

		// Create a JavaScriptExecutor object to execute JavaScript scripts
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

		// Execute a JavaScript script to remove the 'disabled' attribute from the input element, enabling it
		jsExecutor.executeScript("arguments[0].removeAttribute('disabled');", input);

		// Retrieve the value of the input field and store it in 'actualValue2'
		String actualValue2 = input.getAttribute("value");

		// Verify that the element is now enabled and contains the expected value "Selenium"
		Assert.assertEquals(expectedValue2, actualValue2);
	}

}
