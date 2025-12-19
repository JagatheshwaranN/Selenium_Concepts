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
		// Define the expected title of the webpage
		String expectedTitle = "Google";

		// Open the Google homepage
		driver.get("https://www.google.com/");

		// Get the window handle (unique identifier) of the current window
		String windowHandle = driver.getWindowHandle();
		System.out.println("Window handle: " + windowHandle);

		// Get the actual title of the current page
		String actualTitle = driver.getTitle();

		// Verify that the actual title matches the expected title
		Assert.assertEquals(actualTitle, expectedTitle);
	}

}
