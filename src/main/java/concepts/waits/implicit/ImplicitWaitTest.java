package concepts.waits.implicit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

import java.io.File;
import java.time.Duration;

public class ImplicitWaitTest {

	// Declare a WebDriver instance to interact with the web browser.
	private WebDriver driver;

	// Define a constant duration for the maximum wait time, set to 5 seconds
	private static final Duration WAIT_TIMEOUT = Duration.ofSeconds(5);

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
	public void testImplicitWait() {
		// Define the expected value
		String expectedValue = "Selenium";

		// Set the implicit wait timeout for the WebDriver
		driver.manage().timeouts().implicitlyWait(WAIT_TIMEOUT);

        // URL of the HTML file
        String filePath = "src/main/resources/supportFiles/DisabledElement.html";

        // Open the webpage
        driver.get(new File(filePath).toURI().toString());

        // Find the input element using a CSS Selector
		WebElement input = driver.findElement(By.cssSelector("input[id='myText']"));

		// Enter text 'admin' into the input field
		input.sendKeys(expectedValue);

		// Retrieve the entered value from the input field
		String actualValue = input.getAttribute("value");

		// Assert whether the actual value matches the expected value
		Assert.assertEquals(actualValue, expectedValue);
	}

}
