package concepts.driver.firefox;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class FirefoxHeadlessModeTest {

    // Declare a WebDriver instance to interact with the web browser.
    private WebDriver driver;

    @BeforeMethod
    public void setUp() {
        // Set the system property for the WebDriver to use the JDK HTTP client
        System.setProperty("webdriver.http.factory", "jdk-http-client");

        // Create FirefoxOptions instance
        FirefoxOptions firefoxOptions = new FirefoxOptions();

        // Set the headless mode capability using the addArguments method
        firefoxOptions.addArguments("--headless");

        // Initialize FirefoxDriver with FirefoxOptions
        driver = new FirefoxDriver(firefoxOptions);
    }

    @Test(priority = 1)
    public void headlessFirefoxBrowserLaunch() {
        // Navigate to the Google Home page.
        driver.get("https://www.google.com/");

        // Assert that the page title is "Google".
        Assert.assertEquals(driver.getTitle(), "Google");
    }

    @AfterMethod
    public void tearDown() {
        // Check if the 'driver' variable is not null, indicating that a WebDriver instance exists.
        if (driver != null) {
            // If a WebDriver instance exists, quit/close the browser session.
            driver.quit();
        }
    }

}

