package concepts.windows;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

public class SetAndGetWindowSizeTest {

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
	public void testSetAndGetWindowSize() {
		// Navigate to the target URL
		driver.get("https://the-internet.herokuapp.com/windows");

		//Approach 1

		// Get the height of the current window
		int height = driver.manage().window().getSize().getHeight();

		// Get the width of the current window
		int width = driver.manage().window().getSize().getWidth();

		// Print the height and width of the current window
		System.out.println("Window's Height ==> " + height);
		System.out.println("Window's Width ==> " + width);

		//Approach 2

		// Set the window size to 1024x768
		driver.manage().window().setSize(new Dimension(1024, 768));

		// Get the window size after resizing
		Dimension dimension = driver.manage().window().getSize();

		// Print the height and width of the window after resizing
		System.out.println("Window's Height ==> " + dimension.getHeight());
		System.out.println("Window's Width ==> " + dimension.getWidth());
	}

}
