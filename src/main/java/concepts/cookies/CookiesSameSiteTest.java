package concepts.cookies;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

import java.util.Set;

public class CookiesSameSiteTest {

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
	public void testCookieSameSite() {
		// Set to store allCookies
		Set<Cookie> allCookies;

		// Navigate to the example website
		driver.get("http://www.example.com/");

		// Create two cookies with different SameSite attributes
		Cookie cookie1 = new Cookie.Builder("Test", "12345").sameSite("Strict").build();
		Cookie cookie2 = new Cookie.Builder("Auto", "98765").sameSite("Lax").build();

		// Add the cookies to the browser session
		driver.manage().addCookie(cookie1);
		driver.manage().addCookie(cookie2);

		// Print the SameSite attribute for each cookie
		System.out.println("SameSite attribute for 'Test' cookie: " + cookie1.getSameSite());
		System.out.println("SameSite attribute for 'Auto' cookie: " + cookie2.getSameSite());

		// Retrieve and print all allCookies before deletion
		allCookies = driver.manage().getCookies();

		// Assert that the number of cookies is 2, indicating that both cookies were added
		Assert.assertEquals(allCookies.size(), 2, "Expected 2 cookies, but found: " + allCookies.size());

		// Assert that the cookie named "Test" with the value "12345" is present in the set of cookies
		Assert.assertTrue(allCookies.contains(new Cookie("Test", "12345")), "Cookie 'Test' not found.");

		// Assert that the cookie named "Auto" with the value "98765" is present in the set of cookies
		Assert.assertTrue(allCookies.contains(new Cookie("Auto", "98765")), "Cookie 'Auto' not found.");
	}

}
