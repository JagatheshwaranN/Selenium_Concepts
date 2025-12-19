package concepts.wheel;

import concepts.jsexecutor.util.ViewPortUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.WheelInput;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

public class ScrollFromViewportWithOffsetByGivenAmountTest {

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
    public void testScrollFromViewportWithOffsetByGivenAmount() {
        // Initializing x-offset
        int xOffSet = 10;

        // Initializing y-offset
        int yOffSet = 10;

        // Initializing x-axis scroll amount to 0
        int xaxis = 0;

        // Initializing y-axis scroll amount to 600 pixels
        int yaxis = 600;

        // Navigate to the Selenium website
        driver.get("https://www.selenium.dev/");

        // Define the scroll origin from the viewport with an offset of (10, 10) pixels
        WheelInput.ScrollOrigin scrollOrigin = WheelInput.ScrollOrigin.fromViewport(xOffSet, yOffSet);

        // Perform a scroll action from the defined origin by (0, 600) pixels
        new Actions(driver).scrollFromOrigin(scrollOrigin, xaxis, yaxis).perform();

        // Find the 'seleniumSponsors' element using CSS selector
        WebElement seleniumSponsors = driver.findElement(By.cssSelector(".selenium.text-center"));

        // Assert if the 'seleniumSponsors' element is within the viewport after scrolling
        Assert.assertTrue(ViewPortUtil.inViewport(seleniumSponsors, driver));
    }


}
