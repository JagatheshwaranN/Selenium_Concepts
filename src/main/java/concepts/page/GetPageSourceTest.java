package concepts.page;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import scenarios.DriverConfiguration;

public class GetPageSourceTest {

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
	public void testGetPageSource() {
		// Navigate to the example.com website
		driver.get("https://www.example.com/");

		// Get the page source as a string
		String pageSource = driver.getPageSource();

		// Assert that the page source contains the specified title tag
        Assert.assertNotNull(pageSource);
        Assert.assertTrue(pageSource.contains("<title>Example Domain</title>"));
	}

}
