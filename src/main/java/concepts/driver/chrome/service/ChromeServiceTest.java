package concepts.driver.chrome.service;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ChromeServiceTest {

    // Declare a WebDriver instance to interact with the web browser.
    private WebDriver driver;

    // Declare a ChromeDriverService object to manage the ChromeDriver process
    ChromeDriverService chromeDriverService;

    @BeforeMethod
    public void setUp() {
        // Set the system property for the WebDriver to use the JDK HTTP client
        System.setProperty("webdriver.http.factory", "jdk-http-client");

        // Create a new ChromeDriverService instance using a builder pattern
        chromeDriverService = new ChromeDriverService.Builder()
                .build();  // Construct the service with default settings

        // Initialize the ChromeDriver with the configured options
        driver = new ChromeDriver(chromeDriverService);

        // Maximize the browser window using WebDriver's manage() method
        driver.manage().window().maximize();
    }

    @Test(priority = 1)
    public void testChromeService() {
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

            // Close the service after WebDriver usage
            chromeDriverService.stop();
        }
    }

}
