package concepts.windows;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

import java.time.Duration;

public class CreateNewWindowTest {

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
	public void testCreateNewWindow() {
		// Access the specified URL
		driver.get("file:///D:/Environment_Collection/Intellij_Env/Selenium_Concepts/src/main/resources/supportFiles/Login.html");

		// Store the current window handle for later reference
		String parentWindow = driver.getWindowHandle();
		System.out.println("Parent window handle: " + parentWindow);

		// Create a new window using the 'newWindow' method and specify 'WindowType.WINDOW'
		driver.switchTo().newWindow(WindowType.WINDOW);

		// Create a WebDriverWait object with a timeout value
		WebDriverWait wait = new WebDriverWait(driver, WAIT_TIMEOUT);

		// Wait for the number of windows to be 2
		wait.until(ExpectedConditions.numberOfWindowsToBe(2));

		// Navigate the new tab to the specified URL
		driver.navigate().to("file:///D:/Environment_Collection/Intellij_Env/Selenium_Concepts/src/main/resources/supportFiles/Sign.html");

		// Get the handle of the newly opened tab
		String childWindow = driver.getWindowHandle();
		System.out.println("Child window handle: " + childWindow);

		// Wait until the title of the new tab becomes "SignIn"
		wait.until(ExpectedConditions.titleIs("SignIn"));

		// Close the newly opened tab
		driver.close();

		// Switch the focus back to the original tab using the stored handle
		driver.switchTo().window(parentWindow);
	}

}
