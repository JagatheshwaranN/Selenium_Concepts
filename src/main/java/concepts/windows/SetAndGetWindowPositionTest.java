package concepts.windows;

import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

public class SetAndGetWindowPositionTest {

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
	public void testSetAndGetWindowPosition() {
		// Navigate to the target URL
		driver.get("https://the-internet.herokuapp.com/windows");

		// Approach 1
		// Get the X coordinate of the window's position
		int x = driver.manage().window().getPosition().getX();

		// Get the Y coordinate of the window's position
		int y = driver.manage().window().getPosition().getY();

		// Print the X and Y coordinates of the window's position
		System.out.println("Window's X Axis ==> " + x);
		System.out.println("Window's Y Axis ==> " + y);

		// Approach 2
		// Set the window's position to co-ordinates (5, 5)
		driver.manage().window().setPosition(new Point(5, 5));

		// Get the window's position after setting it
		Point position = driver.manage().window().getPosition();

		// Print the updated X and Y coordinates of the window's position
		System.out.println("Window's X Axis ==> " + position.getX());
		System.out.println("Window's Y Axis ==> " + position.getY());
	}

}
