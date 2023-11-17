package concepts.mouse;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

import java.time.Duration;
import java.time.Instant;

public class MouseActionPauseTest {

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
	public void testMouseActionPause() {
		// Load the webpage for mouse interactions
		driver.get("https://www.selenium.dev/selenium/web/mouse_interaction.html");

		// Store the current time before performing actions
		Instant startTime = Instant.now();

		// Perform a sequence of actions: move to an element, pause, click and hold, pause, and send keys
		new Actions(driver)
				.moveToElement(driver.findElement(By.id("clickable")))
				.pause(Duration.ofSeconds(1))
				.clickAndHold()
				.pause(Duration.ofSeconds(1))
				.sendKeys("action pause")
				.perform();

		// Calculate the time taken for the action sequence
		Instant endTime = Instant.now();
		Duration duration = Duration.between(startTime, endTime);

		// Verify if the duration falls within the expected range (between 2000ms and 3000ms)
		Assert.assertTrue(duration.toMillis() > 2000 && duration.toMillis() < 3000,
				"The action duration is within the expected range.");
	}

}
