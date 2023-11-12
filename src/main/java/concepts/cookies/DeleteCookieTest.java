package concepts.cookies;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

import java.util.Set;

public class DeleteCookieTest {

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
	public void deleteCookie() {
		// Set to store allCookies
		Set<Cookie> allCookies;

		// Navigate to the example website
		driver.get("http://www.example.com/");

		// Add a new cookie named "Test" with the value "12345" to the browser session
		driver.manage().addCookie(new Cookie("Test", "12345"));

		// Create a new Cookie object named 'autoCookie' with the name "Auto" and value "98765"
		Cookie autoCookie = new Cookie("Auto", "98765");
		driver.manage().addCookie(autoCookie);

		// Retrieve and print all allCookies before deletion
		allCookies = driver.manage().getCookies();
		System.out.println("Cookies before deletion: " + allCookies);

		// Assert that the number of cookies is 2, indicating that both cookies were added
		Assert.assertEquals(allCookies.size(), 2, "Expected 2 cookies, but found: " + allCookies.size());

		// Assert that the cookie named "Test" with the value "12345" is present in the set of cookies
		Assert.assertTrue(allCookies.contains(new Cookie("Test", "12345")), "Cookie 'Test' not found.");

		// Assert that the cookie named "Auto" with the value "98765" is present in the set of cookies
		Assert.assertTrue(allCookies.contains(new Cookie("Auto", "98765")), "Cookie 'Auto' not found.");

		// Delete the cookie named "Test" by name
		driver.manage().deleteCookieNamed("Test");

		// Retrieve and print all allCookies after the first deletion
		allCookies = driver.manage().getCookies();
		System.out.println("Cookies after deleting 'Test': " + allCookies);

		// Assert that the number of cookies is 1, indicating that one cookie were added
		Assert.assertEquals(allCookies.size(), 1, "Expected 1 cookies, but found: " + allCookies.size());

		// Assert that the cookie named "Auto" with the value "98765" is present in the set of cookies
		Assert.assertTrue(allCookies.contains(new Cookie("Auto", "98765")), "Cookie 'Auto' not found.");

		// Delete the 'Auto' cookie by providing the cookie object
		driver.manage().deleteCookie(autoCookie);

		// Assert that the number of cookies is 0, indicating that no cookies were added
		Assert.assertEquals(driver.manage().getCookies().size(), 0, "Expected 0 cookies, but found: " + allCookies.size());
	}

}
