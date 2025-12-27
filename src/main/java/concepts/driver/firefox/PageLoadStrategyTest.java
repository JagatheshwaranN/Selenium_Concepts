package concepts.driver.firefox;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
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

        // Instantiate FirefoxOptions to configure the FirefoxDriver
        FirefoxOptions firefoxOptions = new FirefoxOptions();

        /*
            The other PageLoadStrategy options are as below,
            NONE("none");
         */

        // Set the page load strategy to NORMAL, which instructs the WebDriver to wait
        // for the DOMContentLoaded event
        firefoxOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);

        // Initialize the FirefoxDriver with the configured options
        driver = new FirefoxDriver(firefoxOptions);

        // Navigate to the unsplash website
        driver.get("https://unsplash.com/t/people");

        // Compare the expected title with the actual title and assert their equality
        Assert.assertEquals(driver.getTitle(), expectedTitle,  "Actual title does not match expected title.");
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
