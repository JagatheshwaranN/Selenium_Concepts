package concepts.cookies;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

import java.util.Set;

public class DeleteCookiesTest {

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
	public void deleteAllCookies() {
		// Navigate to the example website
		driver.get("http://www.example.com/");

		// Add a new cookie named "Test" with the value "12345" to the browser session
		driver.manage().addCookie(new Cookie("Test", "12345"));

		// Create a new Cookie object named 'autoCookie' with the name "Auto" and value "98765"
		Cookie autoCookie = new Cookie("Auto", "98765");
		driver.manage().addCookie(autoCookie);

		// Delete all cookies in the browser session
		driver.manage().deleteAllCookies();

		// Retrieve and print all cookies after deletion
		Set<Cookie> cookies = driver.manage().getCookies();
		System.out.println("Cookies after deleting all: " + cookies);

		// Assert that the set of cookies is empty after deleting all cookies
		Assert.assertTrue(cookies.isEmpty(), "Cookies are not deleted successfully.");
	}

}
