package concepts.driver.edge;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class EdgeHeadlessModeTest {

    // Declare a WebDriver instance to interact with the web browser.
    private WebDriver driver;

    @BeforeMethod
    public void setUp() {
        // Set the system property for the WebDriver to use the JDK HTTP client
        System.setProperty("webdriver.http.factory", "jdk-http-client");

        // Instantiate EdgeOptions to configure the EdgeDriver
        EdgeOptions edgeOptions = new EdgeOptions();

        // Set the Edge browser to run in headless mode with the specified argument
        edgeOptions.addArguments("--headless=new");

        // Initialize the EdgeDriver with the configured options
        driver = new EdgeDriver(edgeOptions);

        // Maximize the browser window using WebDriver's manage() method
        driver.manage().window().maximize();
    }

    @Test(priority = 1)
    public void headlessEdgeBrowserLaunch() {
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

