package concepts.cdp.bidi_api;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.logging.HasLogEvents;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

import java.net.URI;
import java.time.Duration;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static org.openqa.selenium.devtools.events.CdpEventTypes.domMutation;

public class ElementMutationTest {

	// Declare a WebDriver instance to interact with the web browser.
	private ChromeDriver driver;

	@BeforeMethod
	public void setUp() {
		// Set up the WebDriver instance by calling a method named 'cdpBrowserSetup' from the 'DriverConfiguration' class
		driver = DriverConfiguration.cdpBrowserSetup();
	}

	@Test
	public void testElementMutation() {
		// Navigate to the test page
		driver.get("https://www.selenium.dev/selenium/web/dynamic.html");

		// Create a thread-safe list to store mutated elements
		CopyOnWriteArrayList<WebElement> elementMutation = new CopyOnWriteArrayList<>();

		// Register a listener for DOM mutation events
		((HasLogEvents) driver).onLogEvent(domMutation(element -> elementMutation.add(element.getElement())));

		// Trigger a DOM mutation by clicking an element
		driver.findElement(By.id("reveal")).click();

		// Wait for a mutation to be detected
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(driverObject -> !elementMutation.isEmpty());

		// Get the mutated element
		WebElement mutatedElement = driver.findElement(By.id("revealed"));

		// Assert that the mutated element is the expected one
		Assert.assertEquals(elementMutation.get(0), mutatedElement);
	}

	@AfterMethod
	public void tearDown() {
		// Check if the 'driver' variable is not null, indicating that a WebDriver instance exists.
		if (driver != null) {
			// If a WebDriver instance exists, quit/close the browser session.
			driver.quit();
		}
	}

}