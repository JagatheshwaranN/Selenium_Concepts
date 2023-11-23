package concepts.windows;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

import java.time.Duration;

public class SwitchToTabOrWindowTest {

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
	public void testSwitchToTabOrWindow() {
		// Define the expected title
		String expectedTitle = "New Window";

		// Navigate to the target URL
		driver.get("https://the-internet.herokuapp.com/windows");

		// Get the handle of the current window
		String parentWindow = driver.getWindowHandle();
		System.out.println("Parent window handle: " + parentWindow);

		// Verify that there is only one window open
		assert driver.getWindowHandles().size() == 1 : "Expected only one window open";

		// Click on the "Click Here" link to open a new window
		driver.findElement(By.xpath("//a[text()='Click Here']")).click();

		// Create a WebDriverWait object with a timeout value
		WebDriverWait wait = new WebDriverWait(driver, WAIT_TIMEOUT);

		// Wait for the number of windows to be 2
		wait.until(ExpectedConditions.numberOfWindowsToBe(2));

		// Iterate through the set of window handles to find the new window
		for (String handle : driver.getWindowHandles()) {
			if (!parentWindow.contentEquals(handle)) {
				driver.switchTo().window(handle);
				System.out.println("Switched to new window handle: " + handle);
				break;
			}
		}

		// Wait for the title of the new window to be "New Window"
		wait.until(ExpectedConditions.titleIs(expectedTitle));

		// Close the new window
		driver.close();

		// Switch back to the parent window
		driver.switchTo().window(parentWindow);
	}

}
