package concepts.driver.edge;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class BrowserDetailsTest {

    // Declare a WebDriver instance to interact with the web browser.
    private WebDriver driver;

    // Declare an EdgeOptions instance to interact with the web browser.
    private EdgeOptions edgeOptions;

    @BeforeMethod
    public void setUp() {
        // Set the system property for the WebDriver to use the JDK HTTP client
        System.setProperty("webdriver.http.factory", "jdk-http-client");

        // Instantiate EdgeOptions to configure the EdgeDriver
        edgeOptions = new EdgeOptions();

        // Initialize the EdgeDriver with the configured options
        driver = new EdgeDriver(edgeOptions);

        // Maximize the browser window using WebDriver's manage() method
        driver.manage().window().maximize();
    }

    @Test(priority = 1)
    public void browserDetails() {
        // Navigate to the Google Home page.
        driver.get("https://www.google.com/");

        // Prints the browser name to the console.
        System.out.println("Browser Name    :   " + edgeOptions.getBrowserName());

        // Note: The edgeOptions.getBrowserVersion() method does not work as expected.
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
