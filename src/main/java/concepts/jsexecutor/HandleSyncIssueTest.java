package concepts.jsexecutor;

import org.apache.poi.util.NotImplemented;
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


public class HandleSyncIssueTest {

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
	public void testWaitForElementVisible() throws InterruptedException {
		// Navigate to Google homepage
		driver.get("https://admin-demo.nopcommerce.com/login");

		// Create a JavaScriptExecutor instance to execute JavaScript code
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

		// Create a custom Element Wait method with Javascript Executor
		waitForDynamicElement(driver, "Email");

		// Clear the content of the 'Email' input field
		driver.findElement(By.id("Email")).clear();
	}

	@NotImplemented
	private static void waitForDynamicElement(WebDriver driver, String elementId) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

		// Function to wait for the element using JavaScript
		jsExecutor.executeScript(
				"var maxAttempts = 10;" +
						"var interval = 1000; // 1 second interval" +
						"var attempts = 0;" +

						"function waitForElement() {" +
						"    if (attempts >= maxAttempts) {" +
						"        throw new Error('Timeout: Element not found');" +
						"    }" +

						"    var element = document.getElementById('" + elementId + "');" +
						"    if (element && element.style.display !== 'none') {" +
						"        console.log('Element found');" +
						"        return;" +
						"    }" +

						"    attempts++;" +
						"    setTimeout(waitForElement, interval);" +
						"}" +

						"waitForElement();"
		);
	}

}
