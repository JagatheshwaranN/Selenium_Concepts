package concepts.jsexecutor;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

public class GetElementAndScrollTest {

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

    @Test(priority = 1)
    public void testGetElementAndScroll() {
        // Navigate to the target website
        driver.get("http://www.automationpractice.pl/index.php");

        // Create the JavascriptExecutor instance
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

        // Execute JavaScript to scroll to the bottom of the page
        jsExecutor.executeScript("window.scrollTo(0, document.body.scrollHeight)");

        // Execute JavaScript to scroll the element with ID 'home-page-tabs' into view
        jsExecutor.executeScript("document.getElementById('home-page-tabs').scrollIntoView(true);");

        // Locate the element with ID 'home-page-tabs'
        WebElement element = driver.findElement(By.id("home-page-tabs"));

        // Verify that the element is displayed
        Assert.assertTrue(element.isDisplayed());
    }

}
