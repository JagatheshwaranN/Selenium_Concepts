package concepts.windows;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

public class FullScreenWindowTest {

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

    // As of now, no option is available to verify the browser window is full screened
    @Test(priority = 1)
    public void testFullScreenWindow() {
        // Navigate to the URL of the webpage
        driver.get("https://the-internet.herokuapp.com/windows");

        // Maximize the browser window to full screen
        driver.manage().window().fullscreen();
    }

}
