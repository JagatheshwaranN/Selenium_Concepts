package concepts.waits.explicit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Sleeper;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

import java.time.Clock;
import java.time.Duration;

public class ExplicitWaitType3Test {

	// Declare a WebDriver instance to interact with the web browser.
	private WebDriver driver;

	// Define a constant duration for the maximum wait time, set to 5 seconds
	private static final Duration WAIT_TIMEOUT = Duration.ofSeconds(5);

	// Define a constant duration for the maximum sleep time, set to 1.5 seconds
	private static final Duration WAIT_SLEEP = Duration.ofMillis(1500);


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
    public void testExplicitWaitType3() {
        // Define the expected success message after a successful login
        String expectedValue = "Logged In Successfully";

        // Navigate to the login page
        driver.get("https://practicetestautomation.com/practice-test-login/");

        // Enter the username into the username input field
        driver.findElement(By.id("username")).sendKeys("student");

        // Enter the password into the password input field
        driver.findElement(By.id("password")).sendKeys("Password123");

        // Create an explicit wait and wait until the login button
        // is present in the DOM (not necessarily visible or clickable)
        WebElement loginButton = new WebDriverWait(
                driver,                 // WebDriver instance
                WAIT_TIMEOUT,            // Maximum time to wait
                WAIT_SLEEP,              // Polling interval
                Clock.systemDefaultZone(), // System clock
                Sleeper.SYSTEM_SLEEPER   // Thread sleep mechanism
        ).until(ExpectedConditions.presenceOfElementLocated(By.id("submit")));

        // Click on the login button once it is present
        loginButton.click();

        // Locate the page header that appears after login
        // and retrieve its text content
        String actualValue = driver.findElement(By.className("post-title")).getText();

        // Verify that the actual header text matches the expected value
        Assert.assertEquals(actualValue, expectedValue);
    }

}
