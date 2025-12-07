package concepts.element;

import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

public class GetElementSizeTest {

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
	public void testGetElementSize() {
		// Navigate to the inputs page
		driver.get("https://www.selenium.dev/selenium/web/inputs.html");

		// Find the input element
		WebElement inputElement = driver.findElement(By.cssSelector("input[name='no_type']"));

		// Retrieve element size using getSize()
		Dimension elementSize = inputElement.getSize();
		int elementHeight = elementSize.getHeight();
		int elementWidth = elementSize.getWidth();

		// Retrieve element size using getRect()
		Rectangle rectangle = inputElement.getRect();

		// Retrieve element location
		Point point = inputElement.getLocation();

		// Assert the obtained values
		Assert.assertEquals(elementHeight, 21);
		Assert.assertEquals(elementWidth, 170);
		Assert.assertEquals(rectangle.getHeight(), 21);
		Assert.assertEquals(rectangle.getWidth(), 170);
		Assert.assertEquals(rectangle.getX(), 8);
		Assert.assertEquals(rectangle.getY(), 67);
		Assert.assertEquals(point.getX(), 8);
		Assert.assertEquals(point.getY(), 67);
	}

}
