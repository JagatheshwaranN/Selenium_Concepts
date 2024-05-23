package concepts.screenshot;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

import java.io.File;
import java.io.IOException;

public class FirefoxFullPageScreenShotTest {

    // Declare a WebDriver instance to interact with the web browser.
    private WebDriver driver;

    @BeforeMethod
    public void setUp() {
        // Set up the WebDriver instance by calling a method named 'fireFoxBrowserSetup' from the 'DriverConfiguration' class
        driver = DriverConfiguration.fireFoxBrowserSetup();
    }

    @AfterMethod
    public void tearDown() {
        // Check if the 'driver' variable is not null, indicating that a WebDriver instance exists.
        if (driver != null) {
            // If a WebDriver instance exists, quit/close the browser session.
            driver.quit();
        }
    }

    @Test
    public void takeFullPageScreenShot() throws IOException {

        // Navigate to the specified URL using the WebDriver instance
        driver.get("https://www.selenium.dev/");

        // Capture a screenshot of the full page and store it as a File
        File screenshot = ((FirefoxDriver) driver).getFullPageScreenshotAs(OutputType.FILE);

        // Copy the screenshot file to a destination file (in this case, "./FullPageScreenshot.png")
        // Ensure that the destination path is appropriate for your project structure
        FileUtils.copyFile(screenshot, new File("./FullPageScreenshot.png"));
    }

}
