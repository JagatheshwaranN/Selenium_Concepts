package concepts.jsexecutor;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

import java.time.Duration;


public class WaitForElementVisibleTest {

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
	public void testWaitForElementVisible() {
		// Navigate to Google homepage
		driver.get("https://admin-demo.nopcommerce.com/login");

		// Create a JavaScriptExecutor instance to execute JavaScript code
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

		// Create a WebDriverWait instance with a timeout of 10 seconds
		WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(10));

		/*
			Another Approach to Wait using JS Executor
			==========================================
			This approach waits until the 'Email' element is displayed using JavaScriptExecutor.
			// webDriverWait.until((ExpectedCondition<Boolean>) input -> (Boolean) jsExecutor.executeScript("return document.getElementById('Email').style.display !== 'none';"));
		*/

		// Wait until the 'Email' element is displayed using WebDriverWait and JavaScriptExecutor
		webDriverWait.until((ExpectedCondition<Boolean>) driver -> {
			// Assertion to ensure the driver is not null
			assert driver != null;

			// Executing JavaScript code to check if the 'Email' element's display style is not 'none'
			return (Boolean) jsExecutor.executeScript("return arguments[0].style.display !== 'none';", driver.findElement(By.id("Email")));
		});

		// Clear the content of the 'Email' input field
		driver.findElement(By.id("Email")).clear();
	}

}
