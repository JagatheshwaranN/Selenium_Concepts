package concepts.wheel;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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
        Assert.assertTrue(inViewport(seleniumSponsors));
    }

    public boolean inViewport(WebElement element) {
        // Define a JavaScript script to check if the element is within the viewport
        String script = """
        // Calculate the cumulative offset positions of the element and its ancestors
        for (var e = arguments[0], f = e.offsetTop, t = e.offsetLeft, o = e.offsetWidth, n = e.offsetHeight;
            e.offsetParent;) {
            f += (e = e.offsetParent).offsetTop;
            t += e.offsetLeft;
        }

        // Check if the element's top and left positions are within the viewport's boundaries
        return f < window.pageYOffset + window.innerHeight &&
            t < window.pageXOffset + window.innerWidth &&
            f + n > window.pageYOffset &&
            t + o > window.pageXOffset;
    """;

        // Execute the JavaScript script and return the result (whether the element is in viewport)
        return (boolean) ((JavascriptExecutor) driver).executeScript(script, element);
    }

}
