package concepts.frames;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

public class SwitchToFrameByIdTest {

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
	public void testSwitchToFrameById() {
		// Store the expected value in a variable.
		String expectedValue = "John";

		// Navigate to a website with a frame.
		driver.get("https://demo.automationtesting.in/Frames.html");

		// Check if the frame with the ID "singleframe" exists before switching to it.
		if (!driver.findElements(By.id("singleframe")).isEmpty()) {

			// Switch to the frame by ID.
			driver.switchTo().frame("singleframe");

			// Send text to an input element inside the frame.
			driver.findElement(By.tagName("input")).sendKeys("John");

			// Get the value of the input element.
			String valueFromInput = driver.findElement(By.tagName("input")).getAttribute("value");

			// Assert that the text was sent to the input element successfully.
			Assert.assertEquals(valueFromInput, expectedValue);
		} else {
			// Print a message to the console indicating that the frame with the ID
			// "singleframe" does not exist.
			System.out.println("The frame with the ID \"singleframe\" does not exist.");
		}
	}

}
