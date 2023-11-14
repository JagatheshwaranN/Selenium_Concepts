package concepts.mouse;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

import java.time.Duration;
import java.util.Collections;

public class MouseMoveByViewportTest {

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
	public void testMouseMoveByViewport() {
		// Open the web page with the mouse interaction demo
		driver.get("https://www.selenium.dev/selenium/web/mouse_interaction.html");

		// Create a PointerInput object representing the mouse input device
		PointerInput mouse = new PointerInput(PointerInput.Kind.MOUSE, "Default Mouse");

		// Create a PointerInput object representing the mouse input device
		Sequence sequence = new Sequence(mouse, 0)
				.addAction(mouse.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(),
						8, 12));

		// Perform the sequence of mouse actions
		((RemoteWebDriver) driver).perform(Collections.singletonList(sequence));

		// Retrieve the text from the element with the id "absolute-location" and split it into an array
		String[] result = driver.findElement(By.id("absolute-location")).getText().split(",");

		// Convert the first value (x-coordinate) to an integer and compare it to the expected value (8) with a tolerance of 2
		Assert.assertTrue(Math.abs(Integer.parseInt(result[0].strip()) - 8) < 2);

		// Convert the second value (y-coordinate) to an integer and compare it to the expected value (12) with a tolerance of 2
		Assert.assertTrue(Math.abs(Integer.parseInt(result[1].strip()) - 12) < 2);
	}

}
