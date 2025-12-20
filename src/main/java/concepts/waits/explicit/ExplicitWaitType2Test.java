package concepts.waits.explicit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

import java.time.Duration;

public class ExplicitWaitType2Test {

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
    public void testExplicitWaitType2() {
        // Define the expected success message displayed after a successful login
        String expectedValue = "Logged In Successfully";

        // Navigate to the practice test login page
        driver.get("https://practicetestautomation.com/practice-test-login/");

        // Enter the valid username into the username field
        driver.findElement(By.id("username")).sendKeys("student");

        // Enter the valid password into the password field
        driver.findElement(By.id("password")).sendKeys("Password123");

        // Create an explicit wait and wait until the login button
        // is visible and clickable (enabled + displayed)
        WebElement loginButton = new WebDriverWait(driver, WAIT_TIMEOUT, WAIT_SLEEP)
                .until(ExpectedConditions.elementToBeClickable(By.id("submit")));

        // Ensure the login button is not null after waiting
        Assert.assertNotNull(loginButton);

        // Click the login button once it becomes clickable
        loginButton.click();

        // Locate the page title element displayed after login
        // and retrieve its visible text
        String actualValue = driver.findElement(By.className("post-title")).getText();

        // Validate that the actual page title matches the expected value
        Assert.assertEquals(actualValue, expectedValue);
    }

}
