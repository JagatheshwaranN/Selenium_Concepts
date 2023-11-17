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

import static org.openqa.selenium.interactions.PointerInput.*;

public class MouseBackClickTest {

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
	public void testMouseBackClick() {
		// Define variables to hold expected and actual titles
		String actualTitle;
		String expectedTitle;

		// Load the webpage to perform interactions
		driver.get("https://www.selenium.dev/selenium/web/mouse_interaction.html");

		// Perform a click action on the element with ID "click"
		driver.findElement(By.id("click")).click();

		// Verify the initial expected title after the click action
		expectedTitle = "We Arrive Here";
		actualTitle = driver.getTitle();
		Assert.assertEquals(actualTitle, expectedTitle);

		// Create a PointerInput instance for emulating mouse actions
		PointerInput mouse = new PointerInput(Kind.MOUSE, "Default Mouse");

		// Create a sequence of actions: simulate a back-click action
		Sequence sequence = new Sequence(mouse, 0)
				.addAction(mouse.createPointerDown(MouseButton.BACK.asArg()))
				.addAction(mouse.createPointerUp(MouseButton.BACK.asArg()));

		// Perform the back-click action
		((RemoteWebDriver) driver).perform(Collections.singletonList(sequence));

		// Update the expected title after the back-click action
		expectedTitle = "BasicMouseInterfaceTest";
		actualTitle = driver.getTitle();

		// Verify the title after the back-click action
		Assert.assertEquals(actualTitle, expectedTitle);
	}

}
