package concepts.waits.common;

import org.openqa.selenium.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

import java.time.Duration;

public class ScriptWaitTest {

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
	public void testScriptWait() {
		// Set the script timeout duration
		driver.manage().timeouts().scriptTimeout(WAIT_TIMEOUT);

		// Execute a JavaScript alert
		((JavascriptExecutor) driver).executeScript("alert('hello world');");

		// Switch to alert and accept
		driver.switchTo().alert().accept();

		// Execute an asynchronous JavaScript function with a timeout
		((JavascriptExecutor) driver).executeAsyncScript("window.setTimeout(arguments[arguments.length - 1], 500);");
	}

}
