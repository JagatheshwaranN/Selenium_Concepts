package concepts.alerts;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

import java.time.Duration;

public class ConfirmAlertTest {

	// Declare a WebDriver instance to interact with the web browser
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
	public void testConfirmAlert() {
		// Define the expected result
		String expectedAlertResult = "You clicked: Ok";

		// Declare alertContent variable
		String alertContent;

		// Navigate to the JavaScript alerts page
		driver.get("https://the-internet.herokuapp.com/javascript_alerts");

		// Click the button to trigger the JS Confirm alert
		driver.findElement(By.xpath("//button[text()='Click for JS Confirm']")).click();

		// Wait for the alert to be present
		WebDriverWait wait = new WebDriverWait(driver, WAIT_TIMEOUT);
		wait.until(ExpectedConditions.alertIsPresent());

		// Handle the alert
		Alert alert = driver.switchTo().alert();
		alertContent = alert.getText();
		System.out.println("First Alert Content: " + alertContent);

		// Dismiss (Cancel) the alert
		alert.dismiss();

		// Click the button again to trigger the JS Confirm alert
		driver.findElement(By.xpath("//button[text()='Click for JS Confirm']")).click();

		// Wait for the alert to be present
		wait.until(ExpectedConditions.alertIsPresent());

		// Handle the alert again
		alert = driver.switchTo().alert();
		alertContent = alert.getText();
		System.out.println("Second Alert Content: " + alertContent);

		// Accept (Ok) the alert
		alert.accept();

		// Wait for the result element to be updated
		wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id("result"), expectedAlertResult));

		// Get the actual result text
		String actualAlertResult = driver.findElement(By.id("result")).getText();

		// Assert that the actual result matches the expected result
		Assert.assertEquals(actualAlertResult, expectedAlertResult);
	}

}
