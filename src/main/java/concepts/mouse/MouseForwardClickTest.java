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

import java.util.Collections;

public class MouseForwardClickTest {

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
	public void mouseForwardClick() {
		// Define the expected page title after the forward click
		String expectedTitle1 = "We Arrive Here";

		// Define the expected page title after the backward click
		String expectedTitle2 = "BasicMouseInterfaceTest";

		// Open the web page with the mouse interaction demo
		driver.get("https://www.selenium.dev/selenium/web/mouse_interaction.html");

		// Click on the element with the id "click"
		driver.findElement(By.id("click")).click();

		// Get the actual page title
		String actualTitle = driver.getTitle();

		// Verify that the page title has changed to "We Arrive Here"
		Assert.assertEquals(actualTitle, expectedTitle1);

		// Create a PointerInput object representing the mouse input device
		PointerInput mouse = new PointerInput(PointerInput.Kind.MOUSE, "Default Mouse");

		// Create a Sequence object representing a sequence of mouse actions
		Sequence sequence = new Sequence(mouse, 0)
				.addAction(mouse.createPointerDown(PointerInput.MouseButton.BACK.asArg())) // Simulate pressing the back mouse button
				.addAction(mouse.createPointerUp(PointerInput.MouseButton.BACK.asArg())); // Simulate releasing the back mouse button

		// Execute the mouse action sequence to simulate a back button click
		((RemoteWebDriver) driver).perform(Collections.singletonList(sequence));

		// Get the actual page title after the backward click
		actualTitle = driver.getTitle();

		// Verify that the page title has changed back to "BasicMouseInterfaceTest"
		Assert.assertEquals(actualTitle, expectedTitle2);

		// Create a new PointerInput object
		mouse = new PointerInput(PointerInput.Kind.MOUSE, "Default Mouse");

		// Create a new Sequence object representing a sequence of mouse actions
		sequence = new Sequence(mouse, 0)
				.addAction(mouse.createPointerDown(PointerInput.MouseButton.FORWARD.asArg())) // Simulate pressing the forward mouse button
				.addAction(mouse.createPointerUp(PointerInput.MouseButton.FORWARD.asArg())); // Simulate releasing the forward mouse button

		// Execute the mouse action sequence to simulate a forward button click
		((RemoteWebDriver) driver).perform(Collections.singletonList(sequence));

		// Get the actual page title after the forward click
		actualTitle = driver.getTitle();

		// Verify that the actual page title matches the expected title
		Assert.assertEquals(actualTitle, expectedTitle1);
	}

}
