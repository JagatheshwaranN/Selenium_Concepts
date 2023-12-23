package concepts.driver.chrome.options;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class MaximizeBrowserWindowTest {

    // Declare a WebDriver instance to interact with the web browser.
    private WebDriver driver;

    @Test(priority = 1)
    public void openMaximizedBrowser() {
        // Set the system property for the webdriver factory
        System.setProperty("webdriver.http.factory", "jdk-http-client");

        // Initialize ChromeOptions and add arguments
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("start-maximized");

        // Initialize the ChromeDriver with the specified ChromeOptions
        driver = new ChromeDriver(chromeOptions);

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
