package concepts.waits.fluent;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

import java.io.File;
import java.time.Duration;
import java.util.Arrays;

public class FluentWaitType1Test {

    // Declare a WebDriver instance to interact with the web browser.
    private WebDriver driver;

    // Define a constant duration for the maximum wait time, set to 10 seconds
    private static final Duration WAIT_TIMEOUT = Duration.ofSeconds(10);

	// Define a constant duration for the maximum sleep time, set to 0.5 seconds
	private static final Duration WAIT_SLEEP = Duration.ofMillis(500);

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
	public void testFluentWaitType1() {
		// Define the expected value
		String expectedValue = "Selenium";

        // URL of the HTML file
        String filePath = "src/main/resources/supportFiles/DisabledElement.html";

        // Open the webpage
        driver.get(new File(filePath).toURI().toString());

		try {
			// Set up FluentWait with specific timeout, polling interval, and exceptions to ignore
			Wait<WebDriver> wait = new FluentWait<>(driver)
					.withTimeout(WAIT_TIMEOUT) // Adjust timeout as needed
					.pollingEvery(WAIT_SLEEP) // Adjust a polling interval as needed
					.ignoreAll(Arrays.asList(NoSuchElementException.class, NotFoundException.class));

			// Wait until the condition is met or timeout occurs
			WebElement username = wait.until(driver -> {
				try {
					// Find the username input element
					WebElement element = driver.findElement(By.cssSelector("input[id='myText']"));

					// Check if the element is displayed before interacting with it
					return element.isDisplayed() ? element : null;
				} catch (StaleElementReferenceException e) {
					// If the element reference is stale, return null
					return null;
				}
			});

			// If the username element is found
			if (username != null) {
				// Input the expected value
				username.sendKeys(expectedValue);

				// Retrieve the actual value entered
				String actualValue = username.getAttribute("value");

				// Assert that the actual value matches the expected value
				Assert.assertEquals(actualValue, expectedValue);
			} else {
				// Handle a case where the username field is not found or intractable after waiting
				Assert.fail("Username field not found or intractable");
			}
		} catch (TimeoutException e) {
			// Handle timeout exception
			Assert.fail("Timeout while waiting for username field");
		}
	}

}
