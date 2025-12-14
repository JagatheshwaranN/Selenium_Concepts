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

public class FirefoxPageZoomByPercentTest {

    // Declare a WebDriver instance to interact with the web browser.
    private WebDriver driver;

    @BeforeMethod
    public void setUp() {
        // Set up the WebDriver instance by calling a method named 'browserSetup' from the 'DriverConfiguration' class
        driver = DriverConfiguration.fireFoxBrowserSetup();
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
    public void testFirefoxPageZoomByPercent() {
        // Define the zoom percentage (in Firefox, it's represented as a decimal)
        String percent = "0.50";

        // Load the Selenium website
        driver.get("https://www.selenium.dev/");

        // Initialize JavascriptExecutor
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

        // Set the page zoom to the specified percentage for Firefox
        jsExecutor.executeScript("document.body.style.MozTransform='scale(" + percent + ")'");

        // Find the element with the text 'Getting Started'
        WebElement result = driver.findElement(By.xpath("//h2[text()='Getting Started']"));

        // Assert if the element is in the viewport
        Assert.assertTrue(ViewPortUtil.inViewport(result, driver));
    }

}
