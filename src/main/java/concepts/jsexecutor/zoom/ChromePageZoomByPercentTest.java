package concepts.jsexecutor.zoom;

import concepts.jsexecutor.util.ViewPortUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

public class ChromePageZoomByPercentTest {

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
    public void testChromePageZoomByPercent() {
        // Define the zoom percentage
        String percent = "50%";

        // Load the Selenium website
        driver.get("https://www.selenium.dev/");

        // Initialize JavascriptExecutor
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

        // Set the page zoom to the specified percentage
        jsExecutor.executeScript("document.body.style.zoom='" + percent + "'");

        // Find the element with the text 'Getting Started'
        WebElement result = driver.findElement(By.xpath("//h2[text()='Getting Started']"));

        // Assert if the element is in the viewport
        Assert.assertTrue(ViewPortUtil.inViewport(result, driver));
    }

}
