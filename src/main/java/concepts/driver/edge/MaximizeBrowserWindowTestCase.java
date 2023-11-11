package concepts.driver.edge;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class MaximizeBrowserWindowTestCase {

    // Declare a WebDriver instance to interact with the web browser.
    private WebDriver driver;

    @Test(priority = 1)
    public void openMaximizedBrowser() {
        // Set the system property for the webdriver factory
        System.setProperty("webdriver.http.factory", "jdk-http-client");

        // Instantiate EdgeOptions to configure the EdgeDriver
        EdgeOptions edgeOptions = new EdgeOptions();

        // Add the argument to launch the Edge browser in maximized mode
        edgeOptions.addArguments("start-maximized");

        // Initialize the EdgeDriver with the specified EdgeOptions
        driver = new EdgeDriver(edgeOptions);

        // Open the Google homepage
        driver.get("https://www.google.com/");

        // Assert that the title of the page is "Google"
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
