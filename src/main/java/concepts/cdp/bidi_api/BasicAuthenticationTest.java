package concepts.cdp.bidi_api;

import org.openqa.selenium.By;
import org.openqa.selenium.Credentials;
import org.openqa.selenium.UsernameAndPassword;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

import java.net.URI;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class BasicAuthenticationTest {

	// Declare a WebDriver instance to interact with the web browser.
	private ChromeDriver driver;

	@BeforeMethod
	public void setUp() {
		// Set up the WebDriver instance by calling a method named 'cdpBrowserSetup' from the 'DriverConfiguration' class
		driver = DriverConfiguration.cdpBrowserSetup();
	}

	@Test
	public void testBasicAuthentication() {
		// Define the expected success message for authentication
		String expectedMessage = "Congratulations! You must have the proper credentials.";

		// Create a predicate to identify URIs that require authentication
		Predicate<URI> uriPredicate = uri -> uri.toString().contains("herokuapp.com");

		// Create a supplier to provide credentials for authentication
		Supplier<Credentials> authSupplier = UsernameAndPassword.of("admin", "admin");

		// Register the predicate and supplier with the driver for authentication
		driver.register(uriPredicate, authSupplier);

		// Navigate to a URL that requires Basic Authentication
		driver.get("https://the-internet.herokuapp.com/basic_auth");

		// Retrieve the actual message displayed on the page
		String actualMessage = driver.findElement(By.tagName("p")).getText();

		// Assert that the actual message matches the expected message
		Assert.assertEquals(actualMessage, expectedMessage);
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