package concepts.screenshot;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.google.common.io.Files;
import scenarios.DriverConfiguration;

public class PageScreenShotTest {

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
    public void testPageScreenShot() throws IOException {
		/*
			Another way of taking screenshot
			================================
			byte[] screenshotBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
			Files.write(Paths.get("./screenshot.png"), screenshotBytes);
		*/
        // Navigate to the specified URL using the WebDriver instance
        driver.get("https://www.selenium.dev/");

        // Capture a screenshot of the current page and store it as a File
        File pageScreenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        // Copy the screenshot file to a destination file (in this case, "./screenshot.png")
        // Ensure that the destination path is appropriate for your project structure
        Files.copy(pageScreenshotFile, new File("./screenshot.png"));
    }

}
