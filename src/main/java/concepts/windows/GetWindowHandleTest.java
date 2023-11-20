package concepts.windows;

import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

public class GetWindowHandleTest {

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
    public void testGetWindowHandle() {
		String expectedValue = "Google";
        driver.get("https://www.google.com/");
        System.out.println(driver.getWindowHandle());
		String actualValue = driver.getTitle();
		Assert.assertEquals(actualValue, expectedValue);
    }

}
