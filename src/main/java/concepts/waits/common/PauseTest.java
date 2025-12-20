package concepts.waits.common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

import java.io.File;

public class PauseTest {

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

	@SuppressWarnings("SynchronizeOnNonFinalField")
    @Test(priority = 1)
	public void testImplicitWait() {
		// Define the expected value
		String expectedValue = "Selenium";

        // URL of the HTML file
        String filePath = "src/main/resources/supportFiles/DisabledElement.html";

        // Open the webpage
        driver.get(new File(filePath).toURI().toString());

        // Find the input element using a CSS Selector
		WebElement input = driver.findElement(By.cssSelector("input[id='myText']"));

		// Acquires the lock on the "driver" object
		synchronized (driver) {
			try {
				// The Current thread waits for 5000 milliseconds (5 seconds)
				driver.wait(5000);
			} catch (InterruptedException e) {
				// Exception handling if interrupted while waiting
				throw new RuntimeException(e);
			}
			// Release the lock on the "driver" object after exiting synchronized block
		}

		// Enter text 'admin' into the input field
		input.sendKeys(expectedValue);

		// Retrieve the entered value from the input field
		String actualValue = input.getAttribute("value");

		// Assert whether the actual value matches the expected value
		Assert.assertEquals(actualValue, expectedValue);
	}

}
