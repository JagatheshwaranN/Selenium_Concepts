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

public class ScrollFromElementWithOffsetByGivenAmountTest {

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
    public void testScrollFromElementWithOffsetByGivenAmount() {
        // Initializing x-offset
        int xOffSet = 0;

        // Initializing y-offset
        int yOffSet = -50;

        // Initializing x-axis scroll amount to 0
        int xaxis = 0;

        // Initializing y-axis scroll amount to 200 pixels
        int yaxis = 200;

        // Navigate to the Selenium website
        driver.get("https://www.selenium.dev/");

        // Find the 'seleniumDonation' element using XPath
        WebElement seleniumDonation = driver.findElement(By.xpath("//input[@type='image']"));

        // Defining the scroll origin based on the seleniumDonation element
        // Scroll origin is defined with an offset of (0, -50) pixels from the element
        WheelInput.ScrollOrigin scrollOrigin = WheelInput.ScrollOrigin.fromElement(seleniumDonation, xOffSet, yOffSet);

        // Scroll the page from the Selenium Donation button by 200 pixels vertically
        new Actions(driver).scrollFromOrigin(scrollOrigin, xaxis, yaxis).perform();

        // Find the 'copyRightContent' element to check if it's in the viewport
        WebElement copyRightContent = driver.findElement(By.xpath("//small[@class='text-white']"));

        // Assert that the copyright content element is now visible within the viewport
        Assert.assertTrue(inViewport(copyRightContent));
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
