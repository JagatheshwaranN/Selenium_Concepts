package concepts.cookies;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;


public class GetCookieTest {

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
    public void getCookie() {
		// Navigate to the example website
        driver.get("http://www.example.com/");

        // Add a cookie named "Test" with the value "12345" to the browser session
        driver.manage().addCookie(new Cookie("Test", "12345"));

        // Retrieve the cookie named "Test" from the browser session
        Cookie cookie = driver.manage().getCookieNamed("Test");

        // Print the cookie information to the console
        System.out.println(cookie);

		// Assert that the retrieved cookie is not null, indicating successful retrieval
		Assert.assertNotNull(cookie, "Cookie 'Test' is not retrieved successfully.");

		// Assert that the value of the retrieved cookie is as expected ("12345")
		Assert.assertEquals(cookie.getValue(), "12345", "Cookie value is not as expected.");
	}

}
