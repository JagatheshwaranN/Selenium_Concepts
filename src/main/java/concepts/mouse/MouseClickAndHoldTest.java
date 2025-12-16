package concepts.mouse;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

import java.time.Duration;

public class MouseClickAndHoldTest {

	// Declare a WebDriver instance to interact with the web browser.
	private WebDriver driver;

	// Define a constant duration for the maximum wait time, set to 5 seconds
	private static final Duration WAIT_TIMEOUT = Duration.ofSeconds(5);


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
	public void testMouseClickAndHold() {
		// Define the expected message after the click and hold action
		String expectedMessage = "Button has been long pressed";

		// Load the webpage that contains the button for click and hold action
		driver.get("https://letcode.in/button");

		// Perform a click and hold action on the specific button using Actions class
		new Actions(driver)
				.clickAndHold(driver.findElement(By.xpath("//div[@class='control']//button[@class='button is-primary' and @id='isDisabled']")))
				.perform();

		// Set up WebDriverWait for waiting for the message element to be present
		WebDriverWait wait = new WebDriverWait(driver, WAIT_TIMEOUT);

		// Wait until the message element with the expected text is present
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h2[contains(text(),'Button has been long pressed')]")));

		// Retrieve the text of the element to verify the resulting message
		String actualMessage = driver.findElement(By.xpath("//h2[contains(text(),'Button has been long pressed')]")).getText();

		// Verify if the actual message matches the expected message
		Assert.assertEquals(actualMessage, expectedMessage);
	}

}
