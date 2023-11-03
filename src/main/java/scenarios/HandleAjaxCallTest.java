package scenarios;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class HandleAjaxCallTest {

	// Declare a WebDriver instance to interact with the web browser.
	private WebDriver driver;

	// Define a constant duration for the maximum wait time, set to 10 seconds.
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
	public void testHandleAjaxCall() {
		// Navigate to the specified URL "https://omayo.blogspot.com/" using the WebDriver instance
		driver.get("https://omayo.blogspot.com/");

		// Wait for the page to load completely.
		untilPageLoadComplete();

		// Use JavaScriptExecutor to execute a JavaScript snippet to Scroll to the bottom of the page.
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");

		// Locate and identify a WebElement representing a dropdown button using a CSS selector
		WebElement dropDown = driver.findElement(By.cssSelector("button[class='dropbtn']"));

		// Perform a click action on the 'dropDown' WebElement to activate the dropdown menu
		dropDown.click();

		// Wait for Ajax calls to complete.
		untilJqueryIsDone();

		// Locate the 'Flipkart' link within an element with the id 'myDropdown' using XPath
		WebElement flipkart = driver.findElement(By.xpath("//div[@id='myDropdown']//a[text()='Flipkart']"));

		// Create a WebDriverWait instance with a specified timeout (WAIT_TIMEOUT)
		// and wait for the 'flipkart' element to be clickable within the given timeout
		new WebDriverWait(driver, WAIT_TIMEOUT).until(ExpectedConditions.elementToBeClickable(flipkart)).click();

		// Get the current URL of the web page and store it in the 'result' variable
		String result = driver.getCurrentUrl();

		// Use the 'Assert.assertEquals' method to compare the value in the 'result' variable
		// with the expected URL "https://www.flipkart.com/"
		Assert.assertEquals(result, "https://www.flipkart.com/");
	}

	private void untilJqueryIsDone() {
		// Use a custom Expected Condition to wait until a specific condition is met
		// This condition checks if there are no active jQuery AJAX requests in the web page
		until(driver -> {
			// Assert that the 'driver' is not null before proceeding
			assert driver != null;

			// Execute a JavaScript snippet to check if jQuery.active is equal to 0
			// This indicates that there are no active jQuery AJAX requests
			String jsResult = ((JavascriptExecutor) driver).executeScript("return jQuery.active==0").toString();

			// Return true if the result of the JavaScript execution is "true", indicating no active requests
			return jsResult.equals("true");
		});
	}

	private void untilPageLoadComplete() {
		// Use a custom Expected Condition to wait until the web page's document.readyState becomes "complete"
		until(driver -> {
			// Assert that the 'driver' is not null before proceeding
			assert driver != null;

			// Execute a JavaScript snippet to retrieve the value of document.readyState
			// This represents the loading state of the web page
			String pageReadyState = ((JavascriptExecutor) driver).executeScript("return document.readyState").toString();

			// Return true if the pageReadyState is "complete," indicating that the page has fully loaded
			return pageReadyState.equals("complete");
		});
	}

	private void until(ExpectedCondition<Boolean> waitCondition) {
		// Create a WebDriverWait instance 'wait' with a specified timeout using the 'driver' object and a constant 'WAIT_TIMEOUT'
		WebDriverWait wait = new WebDriverWait(driver, HandleAjaxCallTest.WAIT_TIMEOUT);

		// Set the timeout for the 'wait' instance to 'WAIT_TIMEOUT' (this line is redundant as the timeout was already set during creation)
		wait.withTimeout(HandleAjaxCallTest.WAIT_TIMEOUT);

		try {
			// Use the 'wait' instance to wait until a specific condition ('waitCondition') is met
			wait.until(waitCondition);
		} catch (Exception e) {
			// If an exception is thrown during the waiting period, print the error message to the console
			System.out.println(e.getMessage());
		}
	}

}
