package concepts.driver.chrome;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class PageLoadStrategyTest {

    // Declare a WebDriver instance to interact with the web browser.
    private WebDriver driver;

    @Test(priority = 1)
    public void pageLoadStrategy() {
        // Define the expected title for comparison
        String expectedTitle = "People | Unsplash";

        // Set the system property for the WebDriver to use the JDK HTTP client
        System.setProperty("webdriver.http.factory", "jdk-http-client");

        // Instantiate ChromeOptions to configure the ChromeDriver
        ChromeOptions chromeOptions = new ChromeOptions();

        /*
            The other PageLoadStrategy options are as below,
            EAGER("eager"),
            NORMAL("normal");
         */

        // Set the page load strategy to NONE, which instructs the WebDriver to wait
        // for the DOMContentLoaded event
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.NONE);

        // Initialize the ChromeDriver with the configured options
        driver = new ChromeDriver(chromeOptions);

        // Maximize the browser window for better visibility
        driver.manage().window().maximize();

        // Navigate to the unsplash website
        driver.get("https://unsplash.com/t/people");

        // Compare the expected title with the actual title and assert their equality
        Assert.assertEquals(expectedTitle, driver.getTitle(), "Actual title does not match expected title.");
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
