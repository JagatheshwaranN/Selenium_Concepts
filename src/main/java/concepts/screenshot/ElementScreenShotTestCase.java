package concepts.screenshot;

import com.google.common.io.Files;
import org.openqa.selenium.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

import java.io.File;
import java.io.IOException;

public class ElementScreenShotTestCase {

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
    public void takeElementScreenShot() {
		try {
			// Navigate to the specified URL using the WebDriver instance
			driver.get("http://www.example.com");

			// Find the WebElement representing the <h1> header on the page
			WebElement header = driver.findElement(By.tagName("h1"));

			// Capture a screenshot of the identified WebElement and store it as a File
			File elementScreenshotFile = header.getScreenshotAs(OutputType.FILE);

			// Copy the element's screenshot file to a destination file (in this case, "./element_screenshot.png")
			// Ensure that the destination path is appropriate for your project structure
			Files.copy(elementScreenshotFile, new File("./element_screenshot.png"));

		} catch (IOException e) {
			// Handle the exception appropriately, log it, or throw a custom exception.
			e.printStackTrace();
		}
    }

}
