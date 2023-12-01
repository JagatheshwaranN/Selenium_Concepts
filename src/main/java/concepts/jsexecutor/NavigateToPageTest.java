package concepts.jsexecutor;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

public class NavigateToPageTest {

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
	public void testNavigateToPage() {
		// Define the expected page title
		String expectedTitle = "Selenium";

		// Initially navigate to Google (redundant step)
		driver.get("https://www.google.com/");

		// Create the JavascriptExecutor instance
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

		// Execute JavaScript to navigate to the Selenium website
		jsExecutor.executeScript("window.location = 'https://www.selenium.dev/'");

		// Get the actual page title
		String actualTitle = driver.getTitle();

		// Verify that the actual title matches the expected title
		Assert.assertEquals(actualTitle, expectedTitle);
	}

}
