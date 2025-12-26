package concepts.driver.chrome.options;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class BrowserDetailsTest {

    // Declare a WebDriver instance to interact with the web browser.
    private WebDriver driver;

    // Declare a ChromeOptions instance to interact with the web browser.
    private ChromeOptions chromeOptions;

    @BeforeMethod
    public void setUp() {
        // Instantiate ChromeOptions to configure the ChromeDriver
        chromeOptions = new ChromeOptions();

        // Initialize the ChromeDriver with the configured options
        driver = new ChromeDriver(chromeOptions);

        // Maximize the browser window using WebDriver's manage() method
        driver.manage().window().maximize();
    }

    @Test(priority = 1)
    public void browserDetails() {
        // Navigate to the Google Home page.
        driver.get("https://www.google.com/");

        // Prints the browser name to the console.
        System.out.println("Browser Name    :   " + chromeOptions.getBrowserName());

        // Note: The chromeOptions.getBrowserVersion() method does not work as expected.
        // It returns an empty string instead of the browser version.

        // WorkAround - Instead, we use RemoteWebDriver's capabilities to get the browser
        // version.
        // Get the browser version using the Capabilities object.
        Capabilities capabilities = ((RemoteWebDriver) driver).getCapabilities();
        System.out.println("Browser Version :   " + capabilities.getBrowserVersion());

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
